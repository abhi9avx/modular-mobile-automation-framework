package pages.ios;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import pages.common.BasePage;
import pages.common.LoginPage;

public class IOSLoginPage extends BasePage implements LoginPage {

    // ===== Locators (iOS) =====
    // The Sauce Labs Sample App uses the same Accessibility IDs for iOS
    private final By usernameField = AppiumBy.accessibilityId("test-Username");
    private final By passwordField = AppiumBy.accessibilityId("test-Password");
    private final By loginButton = AppiumBy.accessibilityId("test-LOGIN");
    // Use iOS Predicate String to find the element containing the error text
    private final By errorMessage = AppiumBy.iOSNsPredicateString(
            "name CONTAINS 'do not match' OR label CONTAINS 'do not match' OR value CONTAINS 'do not match'");

    public IOSLoginPage(AppiumDriver driver) {
        super(driver);
    }

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
        click(loginButton);
    }

    @Override
    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
