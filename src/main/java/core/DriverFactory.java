package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.net.URL;
import java.nio.file.Paths;

public class DriverFactory {

  // Single driver instance for current test thread
  // Generic AppiumDriver to hold either AndroidDriver or IOSDriver
  private static AppiumDriver driver;
  private static String currentDriverPlatform;

  // This method gives driver to tests
  public static AppiumDriver getDriver() {
    String requestedPlatform = ConfigProvider.get("platformName");

    // If driver exists but platform changed (e.g. iOS -> Android), quit old driver
    if (driver != null && !requestedPlatform.equalsIgnoreCase(currentDriverPlatform)) {
      quitDriver();
    }

    if (driver == null) {
      currentDriverPlatform = requestedPlatform;
      if (requestedPlatform.equalsIgnoreCase("Android")) {
        createAndroidDriver();
      } else if (requestedPlatform.equalsIgnoreCase("iOS")) {
        createIOSDriver();
      } else {
        throw new RuntimeException("Unsupported platform: " + requestedPlatform);
      }
    }
    return driver;
  }

  // This method is responsible for creating Android driver
  private static void createAndroidDriver() {
    try {
      // Step 1: Create options object for Android
      UiAutomator2Options options = new UiAutomator2Options();

      // Step 2: Read values from android.properties using ConfigProvider
      options.setPlatformName(ConfigProvider.get("platformName"));
      options.setDeviceName(ConfigProvider.get("deviceName"));
      options.setAutomationName(ConfigProvider.get("automationName"));

      if (ConfigProvider.get("appPackage") != null) {
        options.setAppPackage(ConfigProvider.get("appPackage"));
      }
      if (ConfigProvider.get("appActivity") != null) {
        options.setAppActivity(ConfigProvider.get("appActivity"));
      }
      if (ConfigProvider.get("appWaitActivity") != null) {
        options.setAppWaitActivity(ConfigProvider.get("appWaitActivity"));
      }
      if (ConfigProvider.get("appWaitDuration") != null) {
        options.setAppWaitDuration(
            java.time.Duration.ofMillis(Long.parseLong(ConfigProvider.get("appWaitDuration"))));
      }

      // Add specialized timeouts for CI stability
      if (ConfigProvider.get("adbExecTimeout") != null) {
        options.setAdbExecTimeout(
            java.time.Duration.ofMillis(Long.parseLong(ConfigProvider.get("adbExecTimeout"))));
      }
      if (ConfigProvider.get("androidInstallTimeout") != null) {
        options.setAndroidInstallTimeout(
            java.time.Duration.ofMillis(Long.parseLong(ConfigProvider.get("androidInstallTimeout"))));
      }
      if (ConfigProvider.get("uiautomator2ServerInstallTimeout") != null) {
        options.setUiautomator2ServerInstallTimeout(
            java.time.Duration.ofMillis(Long.parseLong(ConfigProvider.get("uiautomator2ServerInstallTimeout"))));
      }
      if (ConfigProvider.get("uiautomator2ServerLaunchTimeout") != null) {
        options.setUiautomator2ServerLaunchTimeout(
            java.time.Duration.ofMillis(Long.parseLong(ConfigProvider.get("uiautomator2ServerLaunchTimeout"))));
      }

      if (ConfigProvider.get("noReset") != null) {
        options.setNoReset(Boolean.parseBoolean(ConfigProvider.get("noReset")));
      }
      if (ConfigProvider.get("fullReset") != null) {
        options.setFullReset(Boolean.parseBoolean(ConfigProvider.get("fullReset")));
      }

      if (ConfigProvider.get("autoGrantPermissions") != null) {
        options.setAutoGrantPermissions(Boolean.parseBoolean(ConfigProvider.get("autoGrantPermissions")));
      }
      // Ensure app foregrounds even if already running
      options.setCapability("appium:forceAppLaunch", true);
      options.setCapability("appium:eventLoopIdleDelay", 1000);

      // Step 3: Convert app path to absolute path
      String appPath = Paths.get(ConfigProvider.get("app")).toAbsolutePath().toString();

      // Step 4: Set application path
      options.setApp(appPath);

      // Step 5: Create Appium server URL
      URL appiumServerUrl = new URL(ConfigProvider.get("appiumServerUrl"));

      // Step 6: Create AndroidDriver and launch the app
      driver = new AndroidDriver(appiumServerUrl, options);

    } catch (Exception e) {
      e.printStackTrace(); // Print full error to console for debugging
      // Fail immediately if driver creation fails
      throw new RuntimeException("Failed to create Android Driver", e);
    }
  }

  private static void createIOSDriver() {
    try {
      XCUITestOptions options = new XCUITestOptions();
      options.setDeviceName(ConfigProvider.get("deviceName"));
      options.setPlatformVersion(ConfigProvider.get("platformVersion"));
      options.setAutomationName("XCUITest");

      if (ConfigProvider.get("bundleId") != null) {
        options.setBundleId(ConfigProvider.get("bundleId"));
      }

      // Add specialized timeouts for iOS CI stability
      if (ConfigProvider.get("wdaLaunchTimeout") != null) {
        options.setWdaLaunchTimeout(
            java.time.Duration.ofMillis(Long.parseLong(ConfigProvider.get("wdaLaunchTimeout"))));
      }

      // Convert app path to absolute path
      String appPath = Paths.get(ConfigProvider.get("app")).toAbsolutePath().toString();
      options.setApp(appPath);

      URL appiumServerUrl = new URL(ConfigProvider.get("appiumServerUrl"));
      driver = new IOSDriver(appiumServerUrl, options);

    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to create iOS Driver", e);
    }
  }

  // This method closes the app and quits driver
  public static void quitDriver() {
    if (driver != null) {
      driver.quit(); // close app & session
      driver = null; // reset driver
    }
  }
}
