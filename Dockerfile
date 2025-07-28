# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Install curl for health check
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Copy the built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Health check (optional, requires Spring Boot Actuator)
# HEALTHCHECK --interval=30s --timeout=10s --retries=5 CMD curl --fail http://localhost:8080/actuator/health || exit 1

# Configure JVM memory settings
ENTRYPOINT ["java", "-Xmx512m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]







# FROM maven:3.9.6-eclipse-temurin-21 AS build
# WORKDIR /app
# # Copy pom.xml first to leverage Docker layer caching for dependencies
# COPY pom.xml .
# # Copy source code
# COPY src ./src
# # Build the application, skipping tests for faster image creation
# RUN mvn clean install -DskipTests

# # --- Run Stage ---
# FROM eclipse-temurin:21-jre-jammy
# WORKDIR /app

# # Build stage
# FROM maven:3.9.6-eclipse-temurin-21 AS build
# WORKDIR /app
# COPY pom.xml .
# RUN mvn dependency:go-offline
# COPY src ./src
# RUN mvn package -DskipTests

# # Runtime stage
# FROM eclipse-temurin:21-jre-jammy
# WORKDIR /app
# COPY --from=build /app/target/*.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java", "-jar", "app.jar"]

# # Install curl for the health check (consider if strictly necessary for production)
# # For Debian/Ubuntu based images like jammy:
# RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# # Copy the built JAR from the build stage
# # COPY --from=build /app/target/kifiya-cafe-backend-0.0.1-SNAPSHOT.jar app.jar
# COPY --from=build /app/target/kifiya_cafe-0.0.1-SNAPSHOT.jar app.jar

# # Expose the application port
# EXPOSE 8081

# # Configure JVM memory settings and a faster random number generator for Spring Boot
# # Adjust -Xmx (max heap size) based on your application's needs and container's allocated memory.
# # -Djava.security.egd=file:/dev/./urandom is a common optimization for Spring Boot.
# ENTRYPOINT ["java", "-Xmx512m", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]

# # Health check (requires Spring Boot Actuator enabled in your application)
# # --interval: how often to run the check
# # --timeout: how long to wait for a response
# # --retries: how many consecutive failures before marking as unhealthy
# # The CMD uses 'curl' to hit the Actuator health endpoint and checks for a successful exit (status 0).
# #HEALTHCHECK --interval=30s --timeout=10s --retries=5 CMD curl --fail http://localhost:8080/actuator/health || exit 1















# # # Build stage
# # FROM maven:3.9.6-openjdk-21 AS builder
# # WORKDIR /app
# # COPY pom.xml .
# # RUN mvn dependency:go-offline
# # COPY src ./src
# # RUN mvn package -DskipTests

# # # Runtime stage
# # FROM eclipse-temurin:21-jre-jammy
# # WORKDIR /app
# # COPY --from=builder /app/target/*.jar app.jar
# # EXPOSE 8080
# # ENTRYPOINT ["java", "-jar", "/app/app.jar"]