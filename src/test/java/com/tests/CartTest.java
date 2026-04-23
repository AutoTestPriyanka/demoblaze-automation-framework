package com.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.BaseTest;
import com.pages.CartPage;
import com.utils.LogUtil;

public class CartTest extends BaseTest {

    @Test(description = "Verify user can navigate to cart page from navbar")
    public void verifyUserCanNavigateToCartPageFromNavbar() {
        CartPage page = new CartPage(driver);

        page.clickCartLink();
        boolean onCartPage = page.isOnCartPage();

        if (onCartPage) {
            LogUtil.pass("User navigated to cart page successfully.");
        } else {
            LogUtil.defect("User could not navigate to cart page.");
        }

        Assert.assertTrue(onCartPage, "User should navigate to cart page from navbar.");
    }

    @Test(description = "Verify cart table is displayed on cart page")
    public void verifyCartTableIsDisplayedOnCartPage() {
        CartPage page = new CartPage(driver);

        page.clickCartLink();
        boolean cartTableDisplayed = page.isCartTableDisplayed();

        if (cartTableDisplayed) {
            LogUtil.pass("Cart table is displayed on cart page.");
        } else {
            LogUtil.defect("Cart table is not displayed on cart page.");
        }

        Assert.assertTrue(cartTableDisplayed, "Cart table should be displayed on cart page.");
    }

    @Test(description = "Verify Place Order button is displayed on cart page")
    public void verifyPlaceOrderButtonIsDisplayedOnCartPage() {
        CartPage page = new CartPage(driver);

        page.clickCartLink();
        boolean placeOrderVisible = page.isPlaceOrderButtonDisplayed();

        if (placeOrderVisible) {
            LogUtil.pass("Place Order button is displayed on cart page.");
        } else {
            LogUtil.defect("Place Order button is not displayed on cart page.");
        }

        Assert.assertTrue(placeOrderVisible, "Place Order button should be displayed on cart page.");
    }

    @Test(description = "Verify Place Order modal opens from cart page")
    public void verifyPlaceOrderModalOpensFromCartPage() {
        CartPage page = new CartPage(driver);

        page.clickCartLink();
        page.clickPlaceOrder();
        boolean modalDisplayed = page.isPlaceOrderModalDisplayed();

        if (modalDisplayed) {
            LogUtil.pass("Place Order modal opens successfully.");
        } else {
            LogUtil.defect("Place Order modal did not open.");
        }

        Assert.assertTrue(modalDisplayed, "Place Order modal should open from cart page.");
    }

       @Test(description = "Verify product can be added to cart successfully")
    public void verifyProductCanBeAddedToCartSuccessfully() {
        CartPage page = new CartPage(driver);

        String expectedProduct = page.getFirstProductNameOnHomePage();
        page.openFirstProduct();
        String alertText = page.clickAddToCartAndGetAlertText();
        page.clickCartLink();

        boolean presentInCart = page.waitForProductInCart(expectedProduct);
        boolean successAlert = alertText != null && !alertText.isEmpty();

        if (successAlert && presentInCart) {
            LogUtil.pass("Product added to cart successfully: " + expectedProduct);
        } else {
            LogUtil.defect("Product was not added to cart successfully: " + expectedProduct);
        }

        Assert.assertTrue(successAlert, "Add to cart should show success alert.");
        Assert.assertTrue(presentInCart, "Added product should appear in cart.");
    }

    @Test(description = "Verify cart contains added product with correct title")
    public void verifyCartContainsAddedProductWithCorrectTitle() {
        CartPage page = new CartPage(driver);

        String expectedProduct = page.getFirstProductNameOnHomePage();
        page.openFirstProduct();
        page.clickAddToCartAndGetAlertText();
        page.clickCartLink();

        boolean presentInCart = page.waitForProductInCart(expectedProduct);
        String actualCartTitle = page.getFirstCartProductTitle();

        boolean matched = presentInCart &&
                (expectedProduct.equalsIgnoreCase(actualCartTitle) || page.isProductPresentInCart(expectedProduct));

        if (matched) {
            LogUtil.pass("Cart contains the added product with correct title: " + expectedProduct);
        } else {
            LogUtil.defect("Cart product title mismatch. Expected: " + expectedProduct + ", Actual: " + actualCartTitle);
        }

        Assert.assertTrue(matched, "Cart should contain the added product with correct title.");
    }

    @Test(description = "Verify cart total is displayed after product is added")
    public void verifyCartTotalIsDisplayedAfterProductIsAdded() {
        CartPage page = new CartPage(driver);

        page.openFirstProduct();
        page.clickAddToCartAndGetAlertText();
        page.clickCartLink();

        String totalText = page.getCartTotalText();
        boolean totalDisplayed = totalText != null && !totalText.isEmpty();

        if (totalDisplayed) {
            LogUtil.pass("Cart total is displayed after product is added. Total: " + totalText);
        } else {
            LogUtil.defect("Cart total is not displayed after product is added.");
        }

        Assert.assertTrue(totalDisplayed, "Cart total should be displayed after product is added.");
    }

    @Test(description = "Verify cart total after adding multiple products")
    public void verifyCartTotalAfterAddingMultipleProducts() {
        CartPage page = new CartPage(driver);

        String firstProduct = page.getFirstProductNameOnHomePage();
        Assert.assertFalse(firstProduct.isEmpty(), "First product not found.");

        page.openFirstProduct();
        int firstPrice = page.getProductPriceFromDetailsPage();
        String firstAlert = page.clickAddToCartAndGetAlertText();
        Assert.assertNotNull(firstAlert, "First product add to cart alert not shown.");

        page.navigateToHomePage();

        String secondProduct = page.getSecondProductNameOnHomePage();
        Assert.assertFalse(secondProduct.isEmpty(), "Second product not found.");

        page.openProductByName(secondProduct);
        int secondPrice = page.getProductPriceFromDetailsPage();
        String secondAlert = page.clickAddToCartAndGetAlertText();
        Assert.assertNotNull(secondAlert, "Second product add to cart alert not shown.");

        page.clickCartLink();
        page.waitForProductInCart(firstProduct);
        page.waitForProductInCart(secondProduct);

        int expectedTotal = firstPrice + secondPrice;

        String totalText = page.getCartTotalText();
        Assert.assertFalse(totalText.isEmpty(), "Cart total is empty.");

        int actualTotal = Integer.parseInt(totalText.replaceAll("[^0-9]", ""));

        LogUtil.info("First product: " + firstProduct + " | Price: " + firstPrice);
        LogUtil.info("Second product: " + secondProduct + " | Price: " + secondPrice);
        LogUtil.info("Expected total: " + expectedTotal);
        LogUtil.info("Actual total: " + actualTotal);

        if (expectedTotal == actualTotal) {
            LogUtil.pass("Cart total is correct after adding multiple products.");
        } else {
            LogUtil.defect("Cart total mismatch. Expected: " + expectedTotal + " but found: " + actualTotal);
        }

        Assert.assertEquals(actualTotal, expectedTotal,
                "Cart total should match sum of product prices.");
    }

    @Test(description = "Verify user can delete a product from cart")
    public void verifyUserCanDeleteProductFromCart() {
        CartPage page = new CartPage(driver);

        String productName = page.getFirstProductNameOnHomePage();
        boolean added = page.addProductToCartByName(productName);
        Assert.assertTrue(added, "Precondition failed: product was not added to cart.");

        page.clickCartLink();
        page.waitForProductInCart(productName);

        int beforeCount = page.getCartRowCount();
        boolean deleted = page.deleteFirstCartItem();
        int afterCount = page.getCartRowCount();

        boolean deleteWorked = deleted && afterCount < beforeCount;

        if (deleteWorked) {
            LogUtil.pass("User deleted product from cart successfully.");
        } else {
            LogUtil.defect("User could not delete product from cart.");
        }

        Assert.assertTrue(deleteWorked, "User should be able to delete a product from cart.");
    }

    @Test(description = "Verify user cannot purchase without products in a cart")
    public void verifyUserCannotPurchaseWithoutProductsInCart() {
        CartPage page = new CartPage(driver);

        boolean cartCleared = page.deleteAllCartItems();
        Assert.assertTrue(cartCleared || page.isCartEmpty(), "Precondition failed: cart could not be cleared.");

        boolean cartEmpty = page.isCartEmpty();

        if (!page.isOnCartPage()) {
            page.clickCartLink();
        }

        boolean placeOrderVisible = page.isPlaceOrderButtonDisplayed();

        if (cartEmpty && placeOrderVisible) {
            page.clickPlaceOrder();
        }

        boolean placeOrderModalOpened = page.isPlaceOrderModalDisplayed();
        boolean purchaseAllowedWithEmptyCart = cartEmpty && placeOrderModalOpened;

        if (purchaseAllowedWithEmptyCart) {
            LogUtil.defect("User can still open purchase flow without products in cart.");
        } else if (cartEmpty) {
            LogUtil.pass("User cannot purchase without products in cart.");
        } else {
            LogUtil.defect("Cart was not empty before purchase validation.");
        }

        Assert.assertTrue(cartEmpty, "Cart should be empty before purchase validation.");
        Assert.assertFalse(purchaseAllowedWithEmptyCart,
                "User should not be able to proceed with purchase flow when cart is empty.");
    }

    @Test(description = "Verify duplicate product updates quantity instead of adding new row")
    public void verifyDuplicateProductUpdatesQuantityInsteadOfAddingNewRow() {
        CartPage page = new CartPage(driver);

        String productName = page.getFirstProductNameOnHomePage();
        Assert.assertFalse(productName.isEmpty(), "No product found on home page.");

        boolean firstAdded = page.addProductToCartByName(productName);
        boolean secondAdded = page.addProductToCartByName(productName);

        page.clickCartLink();
        page.waitForProductInCart(productName);

        int duplicateCount = page.getProductCountInCartByTitle(productName);

        LogUtil.info("Product: " + productName);
        LogUtil.info("Same product row count in cart: " + duplicateCount);

        boolean quantityUpdated = duplicateCount == 1;

        if (quantityUpdated) {
            LogUtil.pass("Duplicate product updates quantity instead of adding new row.");
        } else {
            LogUtil.defect("Duplicate product created multiple rows instead of updating quantity. Row count found: " + duplicateCount);
        }

        Assert.assertTrue(firstAdded, "First add-to-cart action failed.");
        Assert.assertTrue(secondAdded, "Second add-to-cart action failed.");
        Assert.assertTrue(quantityUpdated,
                "DEFECT: Duplicate product should update quantity instead of adding new row. Row count found: " + duplicateCount);
    }

    @Test(description = "Verify cart item order appear in the same order they were added")
    public void verifyCartItemOrderAppearInSameOrderTheyWereAdded() {
        CartPage page = new CartPage(driver);

        boolean cleared = page.deleteAllCartItems();
        Assert.assertTrue(cleared || page.isCartEmpty(), "Precondition failed: cart could not be cleared.");

        String firstProduct = page.getFirstProductNameOnHomePage();
        Assert.assertFalse(firstProduct.isEmpty(), "Precondition failed: first product not found.");

        boolean firstAdded = page.addProductToCartByName(firstProduct);
        Assert.assertTrue(firstAdded, "First product could not be added to cart.");

        page.navigateToHomePage();

        String secondProduct = page.getSecondProductNameOnHomePage();
        Assert.assertFalse(secondProduct.isEmpty(), "Precondition failed: second product not found.");

        boolean secondAdded = page.addProductToCartByName(secondProduct);
        Assert.assertTrue(secondAdded, "Second product could not be added to cart.");

        page.clickCartLink();
        page.waitForProductInCart(firstProduct);
        page.waitForProductInCart(secondProduct);

        List<String> cartOrder = page.getCartProductTitles();

        boolean sameOrder = cartOrder.size() >= 2
                && cartOrder.get(0).equalsIgnoreCase(firstProduct)
                && cartOrder.get(1).equalsIgnoreCase(secondProduct);

        if (sameOrder) {
            LogUtil.pass("Cart items appear in the same order they were added. First: "
                    + firstProduct + ", Second: " + secondProduct);
        } else {
            LogUtil.defect("Cart item order mismatch. Expected order: [" + firstProduct + ", "
                    + secondProduct + "], Actual order: " + cartOrder);
        }

        Assert.assertTrue(sameOrder, "Cart items should appear in the same order they were added.");
    }
}