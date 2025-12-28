package base;

import core.DriverFactory;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.ios.IOSStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
  protected static final Logger logger = LogManager.getLogger(BaseTest.class);

  // This method runs BEFORE every test method
  @BeforeMethod
  public void setUp() {
    logger.info("Setting up driver for test case...");
    // Create and launch the Appium driver
    DriverFactory.getDriver();

    // Point 2: Start Video Recording
    startRecording();
  }

  // This method runs AFTER every test method
  @AfterMethod(alwaysRun = true)
  public void tearDown(ITestResult result) {

    if (ITestResult.FAILURE == result.getStatus()) {
      logger.error("Test Case FAILED: " + result.getName());
      captureScreenshot("FAILURE Screenshot");
    }

    if (ITestResult.SUCCESS == result.getStatus()) {
      logger.info("Test Case PASSED: " + result.getName());
      captureScreenshot("SUCCESS Screenshot");
    }

    // Point 2: Stop and Attach Video for ALL results (Learning/Testing)
    saveVideoRecording();

    // Point 5: Attach Log File
    attachLogFile();

    DriverFactory.quitDriver();
  }

  @Attachment(value = "Full Execution Log", type = "text/plain")
  public byte[] attachLogFile() {
    try {
      return Files.readAllBytes(Paths.get("build/logs/app.log"));
    } catch (Exception e) {
      logger.warn("Could not attach log file: " + e.getMessage());
      return new byte[0];
    }
  }

  @Step("Starting screen recording")
  private void startRecording() {
    try {
      if (DriverFactory.getDriver() instanceof CanRecordScreen) {
        String platform = System.getProperty("platform", "android").toLowerCase();

        if (platform.equals("ios")) {
          // iOS specific recording options for better browser compatibility
          ((CanRecordScreen) DriverFactory.getDriver()).startRecordingScreen(
              new IOSStartScreenRecordingOptions()
                  .withVideoType("h264") // Standard for browser playback
                  .withVideoQuality(IOSStartScreenRecordingOptions.VideoQuality.MEDIUM)
                  .withTimeLimit(java.time.Duration.ofMinutes(10)));
        } else {
          // Android specific recording options
          ((CanRecordScreen) DriverFactory.getDriver()).startRecordingScreen(
              new AndroidStartScreenRecordingOptions()
                  .withTimeLimit(java.time.Duration.ofMinutes(10)));
        }
      }
    } catch (Exception e) {
      logger.warn("Screen recording could not be started: " + e.getMessage());
    }
  }

  @Step("Stopping screen recording")
  private void stopRecording() {
    try {
      if (DriverFactory.getDriver() instanceof CanRecordScreen) {
        ((CanRecordScreen) DriverFactory.getDriver()).stopRecordingScreen();
      }
    } catch (Exception e) {
      // Ignore if recording was already stopped or not supported
    }
  }

  @Attachment(value = "Video Recording", type = "video/mp4")
  private byte[] saveVideoRecording() {
    try {
      if (DriverFactory.getDriver() instanceof CanRecordScreen) {
        String base64Video = ((CanRecordScreen) DriverFactory.getDriver()).stopRecordingScreen();
        return Base64.getDecoder().decode(base64Video);
      }
    } catch (Exception e) {
      logger.warn("Could not capture video recording: " + e.getMessage());
    }
    return new byte[0];
  }

  @Attachment(value = "{logName}", type = "text/plain")
  public static String saveTextLog(String logName, String message) {
    return message;
  }

  @Attachment(value = "{screenshotName}", type = "image/png")
  public byte[] captureScreenshot(String screenshotName) {
    return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
  }
}
