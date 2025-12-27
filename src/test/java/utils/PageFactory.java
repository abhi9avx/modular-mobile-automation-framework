package utils;

import core.ConfigProvider;
import io.appium.java_client.AppiumDriver;
import pages.android.AndroidLoginPage;
import pages.common.LoginPage;
import pages.ios.IOSLoginPage;

/**
 * Design Pattern: Factory Pattern
 * 
 * This class acts as a central "Factory" that manufactures the correct Page
 * Object
 * based on the current platform (Android or iOS).
 * 
 * extensive logic (if-else checks) is abstracted away here so that
 * the Test classes remain clean and platform-agnostic.
 */
public class PageFactory {

    public static LoginPage getLoginPage(AppiumDriver driver) {
        // 1. Ask ConfigProvider which platform we are currently running on
        String platform = ConfigProvider.get("platformName");

        // 2. Return the implementation matching that platform
        if ("Android".equalsIgnoreCase(platform)) {
            return new AndroidLoginPage(driver);
        } else {
            return new IOSLoginPage(driver);
        }
    }
}
