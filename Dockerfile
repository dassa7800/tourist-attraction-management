# Use official OpenJDK image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built application jar to the container
COPY target/tourist-management-*.jar app.jar

# Expose the port that the app will run on
EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
