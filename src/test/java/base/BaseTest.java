package base;

import core.DriverFactory;
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
    @AfterMethod
    public void tearDown() {
        // Quit the Appium driver and close the app
        DriverFactory.quitDriver();
    }
}
