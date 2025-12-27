package pages.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

import pages.common.BasePage;
import pages.common.LoginPage;

public class AndroidLoginPage extends BasePage implements LoginPage {

    // ===== Locators (Android only) =====
    private final By usernameField = AppiumBy.accessibilityId("test-Username");
    private final By passwordField = AppiumBy.accessibilityId("test-Password");
    private final By loginButton = AppiumBy.accessibilityId("test-LOGIN");
    // Direct XPath to find the error text view by its content
    private final By errorMessage = By.xpath("//*[contains(@text, 'Username and password do not match')]");

    // ===== Constructor =====
    public AndroidLoginPage(AppiumDriver driver) {
        super(driver); // pass driver to BasePage
    }

    // ===== Business actions implementation =====

    @Override
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        tapLogin();
    }

    @Override
    public void enterUsername(String username) {
        type(usernameField, username);
    }

    @Override
    public void enterPassword(String password) {
        type(passwordField, password);
    }

    @Override
    public void tapLogin() {
        // Try to hide keyboard to ensure button is visible/clickable
        try {
            if (driver instanceof io.appium.java_client.HidesKeyboard) {
                ((io.appium.java_client.HidesKeyboard) driver).hideKeyboard();
            }
        } catch (Exception ignored) {
            // Ignore if keyboard is already hidden or not supported
        }
        click(loginButton);
    }

    @Override
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
