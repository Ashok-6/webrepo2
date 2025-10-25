## Use Java 17 base image
#FROM eclipse-temurin:17-jdk-alpine
#
## Set working directory
#WORKDIR /app
#
## Copy the JAR file
#COPY target/documentmanager-0.0.1-SNAPSHOT.jar app.jar
#
## Expose the port
#EXPOSE 8087
#
## Start the app
#CMD ["java", "-jar", "app.jar"]
#
# ---------- Build stage ----------
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /workspace

COPY mvnw . 
COPY .mvn .mvn
COPY pom.xml . 
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline

COPY src ./src
RUN ./mvnw clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /workspace/target/*.jar app.jar

EXPOSE 8087

ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar"]

