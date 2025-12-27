package tests.login;

import base.BaseTest;
import core.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common.LoginPage;
import utils.PageFactory;

public class LoginTest extends BaseTest {

    @Test
    public void shouldShowErrorForInvalidLogin() {

        // Create LoginPage factory (decides Android vs iOS internally)
        LoginPage loginPage = PageFactory.getLoginPage(DriverFactory.getDriver());
        loginPage.login("invalid_user", "invalid_pass");

        // Validate error message
        String errorText = loginPage.getErrorMessage();
        Assert.assertTrue(
                errorText.toLowerCase().contains("do not match"),
                "Expected error message was not displayed. Actual: " + errorText);
    }
}
