# Multi-stage Dockerfile for AuthService
# Build with project's Maven Wrapper, run with Eclipse Temurin 21 JRE

FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .
COPY src ./src

RUN ["chmod", "+x", "./mvnw"] || true
RUN ./mvnw -DskipTests package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]

