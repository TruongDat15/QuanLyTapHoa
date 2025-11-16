FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /workspace

# Copy Maven wrapper + POM của service
COPY .mvn/ .mvn/
COPY mvnw .
COPY pom.xml .

# Copy mã common từ thư mục services/common vào build context
COPY ../common ../common

# Copy source code service
COPY src ./src

RUN chmod +x ./mvnw
RUN ./mvnw -DskipTests clean package

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/app/app.jar"]
