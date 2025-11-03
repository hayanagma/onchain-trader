# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy and install shared-dto first
COPY shared-dto /tmp/shared-dto
RUN mvn -f /tmp/shared-dto/pom.xml clean install -DskipTests

COPY . .

RUN mvn -f mail/pom.xml clean package -DskipTests

# Runtime stage
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/mail/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]