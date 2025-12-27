package tests;

import core.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class SmokeTest {

    @Test
    public void launchAppTest() {
        DriverFactory.getDriver();
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
