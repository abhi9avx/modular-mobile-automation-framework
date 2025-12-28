package pages.ios;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import pages.common.BasePage;
import pages.common.ProductsPage;

public class IOSProductsPage extends BasePage implements ProductsPage {

  private final By productsTitle = AppiumBy.accessibilityId("test-PRODUCTS");
  private final By addToCartButton = AppiumBy.accessibilityId("test-ADD TO CART");
  private final By cartBadge = AppiumBy.accessibilityId("test-Cart badge");

  public IOSProductsPage(AppiumDriver driver) {
    super(driver);
  }

  @Override
  public boolean isProductsPageDisplayed() {
    return waitForVisible(productsTitle).isDisplayed();
  }

  @Override
  public void addFirstProductToCart() {
    click(addToCartButton);
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  @Override
  public int getCartItemCount() {
    // iOS may not show cart badge, return 0 if not found
    try {
      String badgeText = waitForVisible(cartBadge, 10).getText();
      return Integer.parseInt(badgeText);
    } catch (Exception e) {
      return 0;
    }
  }
}
