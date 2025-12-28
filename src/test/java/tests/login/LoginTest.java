package tests.login;

import base.BaseTest;
import core.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.common.LoginPage;
import pages.common.ProductsPage;
import utils.PageFactory;

public class LoginTest extends BaseTest {

  @Test(priority = 1)
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

  @Test(priority = 2)
  public void shouldLoginSuccessfullyWithValidCredentials() {

    LoginPage loginPage = PageFactory.getLoginPage(DriverFactory.getDriver());

    // Valid credentials for Sauce Labs demo app
    loginPage.login("standard_user", "secret_sauce");

    ProductsPage productsPage = PageFactory.getProductsPage(DriverFactory.getDriver());

    Assert.assertTrue(
        productsPage.isProductsPageDisplayed(),
        "User was not navigated to Products page after valid login");
  }
}
