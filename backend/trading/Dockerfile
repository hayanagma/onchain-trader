# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy and install shared-dto
COPY shared-dto /tmp/shared-dto
RUN mvn -f /tmp/shared-dto/pom.xml clean install -DskipTests

COPY . .

RUN mvn -f trading/pom.xml clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/trading/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]