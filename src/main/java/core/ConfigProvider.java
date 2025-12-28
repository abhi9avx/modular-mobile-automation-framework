package core;

import java.io.InputStream;
import java.util.Properties;

public class ConfigProvider {

  private static Properties config;
  private static String loadedPlatform;

  private static void loadConfig() {
    try {
      // 1. Get the platform checking command line arguments (default to "android")
      String platform = System.getProperty("platform", "android").toLowerCase();
      System.out.println("Loading configuration for Platform: " + platform);

      // 2. Define which file to load
      String fileName = "config/" + platform + ".properties";

      // 3. Load the file from the resources folder
      InputStream fileInput = ConfigProvider.class.getClassLoader().getResourceAsStream(fileName);

      if (fileInput == null) {
        throw new RuntimeException("Config file not found: " + fileName);
      }

      // 4. Save properties so we can use them
      config = new Properties();
      config.load(fileInput);
      loadedPlatform = platform;

    } catch (Exception e) {
      throw new RuntimeException("Failed to load configuration", e);
    }
  }

  public static String get(String key) {
    String currentPlatform = System.getProperty("platform", "android").toLowerCase();
    if (config == null || !currentPlatform.equals(loadedPlatform)) {
      loadConfig();
    }
    return config.getProperty(key);
  }
}

// “It centralizes environment and platform configuration, allowing the same
// automation codebase to run across Android and iOS by switching behavior
// through runtime properties instead of code changes.”
