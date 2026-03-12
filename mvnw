#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

set -euf
[ "${MVNW_VERBOSE-}" != debug ] || set -x

native_path() { printf %s\\n "$1"; }
case "$(uname)" in
CYGWIN* | MINGW*)
  [ -z "${JAVA_HOME-}" ] || JAVA_HOME="$(cygpath --unix "$JAVA_HOME")"
  native_path() { cygpath --path --windows "$1"; }
  ;;
esac

scriptDir="$(dirname "$0")"
scriptName="$(basename "$0")"

while IFS="=" read -r key value; do
  case "${key-}" in
  distributionUrl) distributionUrl=$(printf "%s" "${value}" | tr -d '[:space:]') ;;
  distributionSha256Sum) distributionSha256Sum=$(printf "%s" "${value}" | tr -d '[:space:]') ;;
  esac
done <"$scriptDir/.mvn/wrapper/maven-wrapper.properties"
[ -n "${distributionUrl-}" ] || { echo "cannot read distributionUrl" >&2; exit 1; }

case "${distributionUrl##*/}" in
maven-mvnd-*bin.*) MVN_CMD=mvnd.sh _MVNW_REPO_PATTERN=/maven/mvnd/ ;;
*) MVN_CMD="mvn${scriptName#mvnw}" _MVNW_REPO_PATTERN=/org/apache/maven/ ;;
esac

[ -z "${MVNW_REPOURL-}" ] || distributionUrl="$MVNW_REPOURL$_MVNW_REPO_PATTERN${distributionUrl#*"$_MVNW_REPO_PATTERN"}"
distributionUrlName="${distributionUrl##*/}"
distributionUrlNameMain="${distributionUrlName%.*}"
distributionUrlNameMain="${distributionUrlNameMain%-bin}"
MAVEN_USER_HOME="${MAVEN_USER_HOME:-${HOME}/.m2}"
hash_string() { str="${1:-}" h=0; while [ -n "$str" ]; do char="${str%"${str#?}"}"; h=$(((h * 31 + $(LC_CTYPE=C printf %d "'$char")) % 4294967296)); str="${str#?}"; done; printf %x\\n $h; }
MAVEN_HOME="${MAVEN_USER_HOME}/wrapper/dists/${distributionUrlNameMain-}/$(hash_string "$distributionUrl")"

exec_maven() { unset MVNW_VERBOSE MVNW_USERNAME MVNW_PASSWORD MVNW_REPOURL || :; exec "$MAVEN_HOME/bin/$MVN_CMD" "$@" || { echo "cannot exec" >&2; exit 1; }; }

[ -d "$MAVEN_HOME" ] && exec_maven "$@"

[ "${distributionUrl-}" = *-bin.zip ] || [ "${distributionUrl-}" = *maven-mvnd-*-*.zip ] || { echo "invalid distributionUrl" >&2; exit 1; }
TMP_DOWNLOAD_DIR="$(mktemp -d)" && [ -d "$TMP_DOWNLOAD_DIR" ] || { echo "cannot create temp dir" >&2; exit 1; }
clean() { rm -rf -- "$TMP_DOWNLOAD_DIR"; }
trap clean HUP INT TERM EXIT
mkdir -p -- "${MAVEN_HOME%/*}"
[ "${MVNW_VERBOSE-}" = true ] && echo "Downloading from: $distributionUrl"
if command -v curl >/dev/null; then
  curl -f -L -o "$TMP_DOWNLOAD_DIR/$distributionUrlName" "$distributionUrl" || { echo "curl failed" >&2; exit 1; }
elif command -v wget >/dev/null; then
  wget -q "$distributionUrl" -O "$TMP_DOWNLOAD_DIR/$distributionUrlName" || { echo "wget failed" >&2; exit 1; }
else
  { echo "curl or wget required" >&2; exit 1; }
fi
command -v unzip >/dev/null && unzip -q "$TMP_DOWNLOAD_DIR/$distributionUrlName" -d "$TMP_DOWNLOAD_DIR" || tar xzf "$TMP_DOWNLOAD_DIR/$distributionUrlName" -C "$TMP_DOWNLOAD_DIR"
actualDistributionDir=""
for dir in "$TMP_DOWNLOAD_DIR"/*; do
  [ -d "$dir" ] && [ -f "$dir/bin/$MVN_CMD" ] && { actualDistributionDir="$(basename "$dir")"; break; }
done
[ -n "$actualDistributionDir" ] || { echo "cannot find maven in archive" >&2; exit 1; }
mv -- "$TMP_DOWNLOAD_DIR/$actualDistributionDir" "$MAVEN_HOME" || [ -d "$MAVEN_HOME" ] || { echo "fail to move" >&2; exit 1; }
clean || :
exec_maven "$@"
