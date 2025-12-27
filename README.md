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
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java       # Base test setup/teardown
│       │   ├── pages/
│       │   │   ├── common/             # Common interfaces & base page
│       │   │   │   ├── BasePage.java
│       │   │   │   ├── LoginPage.java
│       │   │   │   └── ProductsPage.java
│       │   │   ├── android/            # Android implementations
│       │   │   │   ├── AndroidLoginPage.java
│       │   │   │   └── AndroidProductsPage.java
│       │   │   └── ios/                # iOS implementations
│       │   │       ├── IOSLoginPage.java
│       │   │       └── IOSProductsPage.java
│       │   ├── tests/
│       │   │   ├── login/
│       │   │   │   └── LoginTest.java
│       │   │   └── cart/
│       │   │       └── CartTest.java
│       │   └── utils/
│       │       └── PageFactory.java    # Factory pattern for page objects
│       └── resources/
│           ├── apps/                   # Mobile app binaries (.apk, .app)
│           └── config/                 # Configuration files
│               ├── android.properties
│               └── ios.properties
├── build.gradle                        # Dependencies and build configuration
├── README.md
└── INTERVIEW_QA.md
```

## 🧪 Test Scenarios

The framework includes comprehensive test coverage for the **Sauce Labs Mobile Demo App**:

### **Login Tests** (`tests/login/LoginTest.java`)
1. **Invalid Login Test**: Validates error message display for incorrect credentials
2. **Valid Login Test**: Verifies successful login and navigation to Products page

### **Cart Tests** (`tests/cart/CartTest.java`)
1. **Add to Cart Test**: Validates adding a product to cart and verifying cart badge count

All tests are **cross-platform** and run on both Android and iOS without code changes!

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

*   **Page Object Model (POM)**: Implemented clean separation between test logic and page interactions using the Page Object pattern.
*   **Factory Design Pattern**: Created `PageFactory` utility that dynamically returns platform-specific page implementations, enabling true cross-platform testing.
*   **Cross-Platform Tests**: Login and Cart tests run seamlessly on both Android and iOS without any code duplication.
*   **Robust Element Handling**: Implemented smart wait strategies and fallback locators for reliable element interactions.
*   **Fixed Configuration Caching**: The framework correctly reloads properties files when switching platforms dynamically.
*   **Driver Lifecycle Management**: Automatic driver quit and recreation when platform changes between tests.
