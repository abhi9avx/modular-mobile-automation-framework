package pages.common;

public interface ProductsPage {

  // Verify products page is visible
  boolean isProductsPageDisplayed();

  // Add first product to cart
  void addFirstProductToCart();

  // Get cart badge count
  int getCartItemCount();
}
