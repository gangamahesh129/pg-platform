FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy full project for multi-module build
COPY . .

RUN apk add --no-cache maven && \
    mvn package -pl discovery-service -am -B -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/discovery-service/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
