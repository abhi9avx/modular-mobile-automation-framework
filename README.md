# Modular Mobile Automation Framework

A robust, scalable, and modular automation framework designed for testing **Android** and **iOS** mobile applications using **Appium** and **Java**.

## 🚀 Key Features

*   **Cross-Platform Support**: Write tests once and run them on both Android and iOS devices.
*   **Dynamic Configuration**: Automatically loads the correct environment configuration (`android.properties` or `ios.properties`) based on the runtime platform.
*   **Cross-Platform Page Objects**: Uses the **Factory Design Pattern** via `PageFactory` to dynamically serve Android or iOS page implementations, allowing a single test script to run on both platforms.
*   **Smart Driver Management**: Intelligent driver factory that handles driver creation and automatic switching between platforms (e.g., stopping an iOS session before starting an Android test) without manual intervention.
*   **Modular Architecture**: Separation of concerns with distinct layers for Configuration, Drivers, and Tests.
*   **Gradle Integration**: Easy-to-run tests via command-line arguments.

## 🛠️ Tech Stack

*   **Language**: Java 17
*   **Automation Core**: Appium Java Client 9.4.0
*   **Build Tool**: Gradle
*   **Test Runner**: TestNG 7.10.2
*   **Logging**: Log4j2

## 📂 Project Structure

```
modular-mobile-automation-framework/
├── src/
│   ├── main/java/core/
│   │   ├── ConfigProvider.java    # Handles dynamic config loading
│   │   ├── DriverFactory.java     # Manages Appium drivers (Android/iOS)
│   │   └── PlatformType.java      # Platform enums
│   └── test/
│       ├── java/tests/            # Test classes (e.g., SmokeTest.java)
│       └── resources/
│           ├── apps/              # Mobile app binaries (.apk, .app)
│           └── config/            # Configuration files
│               ├── android.properties
│               └── ios.properties
├── build.gradle                   # Dependencies and build configuration
└── README.md
```

## ⚙️ Prerequisites

1.  **Java JDK 17+** installed and configured (`JAVA_HOME`).
2.  **Node.js & NPM** installed.
3.  **Appium Server 2.x** installed (`npm install -g appium`).
4.  **Appium Drivers**:
    *   `appium driver install uiautomator2` (for Android)
    *   `appium driver install xcuitest` (for iOS)
5.  **Android Studio** (for Android Emulator/SDK) or **Xcode** (for iOS Simulator).

## 🏃‍♂️ How to Run Tests

You can execute tests easily using Gradle command-line arguments.

### Run on Android (Default)
```bash
./gradlew clean test
# OR explicit command
./gradlew clean test -Dplatform=android
```
*Loads configuration from `src/test/resources/config/android.properties`*

### Run on iOS
```bash
./gradlew clean test -Dplatform=iOS
```
*Loads configuration from `src/test/resources/config/ios.properties`*

## 📚 Interview Preparation

Check out the [INTERVIEW_QA.md](INTERVIEW_QA.md) file for a curated list of 20+ interview questions and answers based on this framework's architecture and implementation.

## 🔄 Recent Improvements

*   **Fixed Configuration Caching**: The framework now correctly reloads properties files when switching platforms dynamically during the same Gradle daemon session.
*   **Driver Lifecycle Management**: Implemented logic to automatically quit and recreate drivers when the requested platform changes between tests.
*   **Git Integration**: Added `.gitignore` to keep the repository clean.
