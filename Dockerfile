# Use an official OpenJDK image as the base
FROM openjdk:17-jdk-slim

# Set environment variables
ENV ANDROID_HOME /opt/android-sdk
ENV PATH ${PATH}:${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools

# Install system dependencies
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    git \
    nodejs \
    npm \
    ffmpeg \
    && rm -rf /var/lib/apt/lists/*

# Install Appium
RUN npm install -g appium
RUN appium driver install uiautomator2

# Set the working directory inside the container
WORKDIR /app

# Copy the project files into the container
COPY . .

# Ensure gradlew is executable
RUN chmod +x gradlew

# The command to keep the container alive (will be overridden by docker-compose)
CMD ["./gradlew", "test"]
