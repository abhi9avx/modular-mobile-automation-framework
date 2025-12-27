package utils;

import core.ConfigProvider;
import io.appium.java_client.AppiumDriver;
import pages.android.AndroidLoginPage;
import pages.android.AndroidProductsPage;
import pages.common.LoginPage;
import pages.common.ProductsPage;
import pages.ios.IOSLoginPage;
import pages.ios.IOSProductsPage;

public class PageFactory {

    public static LoginPage getLoginPage(AppiumDriver driver) {
        String platform = ConfigProvider.get("platformName");

        if ("Android".equalsIgnoreCase(platform)) {
            return new AndroidLoginPage(driver);
        } else {
            return new IOSLoginPage(driver);
        }
    }

    public static ProductsPage getProductsPage(AppiumDriver driver) {
        String platform = ConfigProvider.get("platformName");

        if ("Android".equalsIgnoreCase(platform)) {
            return new AndroidProductsPage(driver);
        } else {
            return new IOSProductsPage(driver);
        }
    }
}
