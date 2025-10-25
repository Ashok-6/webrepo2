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

# Use OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy target JAR file into container
COPY target/documentmanager-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8087

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
