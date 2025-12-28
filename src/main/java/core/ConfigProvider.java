package core;

import java.io.InputStream;
import java.util.Properties;

public class ConfigProvider {

  private static Properties config;
  private static String loadedPlatform;

  private static void loadConfig() {
    String platform = System.getProperty("platform");

    // Default to android if platform is null or empty
    if (platform == null || platform.trim().isEmpty()) {
      platform = "android";
    }
    platform = platform.toLowerCase();

    String fileName = "config/" + platform + ".properties";
    System.out.println("DEBUG: Loading configuration for Platform: [" + platform + "] from file: [" + fileName + "]");

    try {
      InputStream fileInput = ConfigProvider.class.getClassLoader().getResourceAsStream(fileName);

      if (fileInput == null) {
        throw new RuntimeException("Config file not found in classpath: " + fileName);
      }

      config = new Properties();
      config.load(fileInput);
      loadedPlatform = platform;

    } catch (Exception e) {
      throw new RuntimeException("Failed to load configuration for file: " + fileName + ". Error: " + e.getMessage(),
          e);
    }
  }

  public static String get(String key) {
    return get(key, null);
  }

  public static String get(String key, String defaultValue) {
    String platform = System.getProperty("platform");
    if (platform == null || platform.trim().isEmpty()) {
      platform = "android";
    }
    String currentPlatform = platform.toLowerCase();

    if (config == null || !currentPlatform.equals(loadedPlatform)) {
      loadConfig();
    }
    return config.getProperty(key, defaultValue);
  }
}

// “It centralizes environment and platform configuration, allowing the same
// automation codebase to run across Android and iOS by switching behavior
// through runtime properties instead of code changes.”
