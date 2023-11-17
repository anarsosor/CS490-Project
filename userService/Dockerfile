# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/userService-0.0.1-SNAPSHOT.jar .

# Specify the command to run when the container starts
CMD ["java", "-jar", "userService-0.0.1-SNAPSHOT.jar"]