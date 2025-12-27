package pages.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import pages.common.BasePage;
import pages.common.ProductsPage;

public class AndroidProductsPage extends BasePage implements ProductsPage {

    private final By productsTitle = AppiumBy.accessibilityId("test-PRODUCTS");
    private final By addToCartButton = AppiumBy.accessibilityId("test-ADD TO CART");
    private final By cartBadge = AppiumBy.accessibilityId("test-Cart badge");

    public AndroidProductsPage(AppiumDriver driver) {
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
        try {
            String badgeText = waitForVisible(cartBadge, 15).getText();
            return Integer.parseInt(badgeText);
        } catch (Exception e) {
            // Fallback: Try XPath
            By xpathBadge = By.xpath("//*[@content-desc='test-Cart']//android.widget.TextView");
            String badgeText = waitForVisible(xpathBadge, 10).getText();
            return Integer.parseInt(badgeText);
        }
    }
}
