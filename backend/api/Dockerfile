# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy and install shared-dto first
COPY shared-dto /tmp/shared-dto
RUN mvn -f /tmp/shared-dto/pom.xml clean install -DskipTests

# Copy full project (so api/pom.xml is available)
COPY . .

RUN mvn -f api/pom.xml clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/api/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]