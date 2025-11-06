# Multi-stage Dockerfile for APIGateWay
# Build with project's Maven Wrapper, run with Eclipse Temurin 21 JRE

# ------- build stage -------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

# copy wrapper and pom/src to leverage Docker cache
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .
COPY src ./src

# ensure wrapper is executable and build (skip tests for faster builds)
RUN chmod +x ./mvnw
RUN ./mvnw -DskipTests package

# ------- runtime stage -------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# copy artifact produced by the build stage
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]
