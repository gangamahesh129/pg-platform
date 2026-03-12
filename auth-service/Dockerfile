FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN apk add --no-cache maven && \
    mvn package -pl auth-service -am -B -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/auth-service/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
