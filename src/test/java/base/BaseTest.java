package base;

import core.DriverFactory;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

  // This method runs BEFORE every test method
  @BeforeMethod
  public void setUp() {
    // Create and launch the Appium driver
    DriverFactory.getDriver();
  }

  // This method runs AFTER every test method
  @AfterMethod(alwaysRun = true)
  public void tearDown(ITestResult result) {

    if (ITestResult.SUCCESS == result.getStatus()) {
      captureScreenshot("SUCCESS Screenshot");
    }

    if (ITestResult.FAILURE == result.getStatus()) {
      captureScreenshot("FAILURE Screenshot");
    }

    DriverFactory.quitDriver();
  }

  @Attachment(value = "{screenshotName}", type = "image/png")
  public byte[] captureScreenshot(String screenshotName) {
    return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
  }
}
