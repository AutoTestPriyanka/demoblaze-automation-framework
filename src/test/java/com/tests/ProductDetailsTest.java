package com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.base.BaseTest;
import com.pages.ProductDetailsPage;
import com.utils.LogUtil;

public class ProductDetailsTest extends BaseTest {

    @Test(description = "Verify user can open product details page by clicking a product")
    public void verifyUserCanOpenProductDetailsPage() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        String productNameBeforeClick = page.getFirstProductNameOnHomePage();
        page.openFirstProductDetails();

        boolean detailsOpened = page.isOnProductDetailsPage();

        if (detailsOpened) {
            LogUtil.pass("Product details page opened successfully for product: " + productNameBeforeClick);
        } else {
            LogUtil.defect("Product details page did not open after clicking product: " + productNameBeforeClick);
        }

        Assert.assertTrue(detailsOpened, "Product details page should open after clicking a product.");
    }

    @Test(description = "Verify product name is displayed on product details page")
    public void verifyProductNameDisplayedOnDetailsPage() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.openFirstProductDetails();
        boolean nameDisplayed = page.isProductNameDisplayed();

        if (nameDisplayed) {
            LogUtil.pass("Product name is displayed on product details page.");
        } else {
            LogUtil.defect("Product name is not displayed on product details page.");
        }

        Assert.assertTrue(nameDisplayed, "Product name should be displayed on product details page.");
    }

    @Test(description = "Verify product price is displayed on product details page")
    public void verifyProductPriceDisplayedOnDetailsPage() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.openFirstProductDetails();
        boolean priceDisplayed = page.isProductPriceDisplayed();
        String priceText = page.getProductPriceText();

        if (priceDisplayed && !priceText.isEmpty()) {
            LogUtil.pass("Product price is displayed on product details page. Price: " + priceText);
        } else {
            LogUtil.defect("Product price is not displayed on product details page.");
        }

        Assert.assertTrue(priceDisplayed, "Product price should be displayed on product details page.");
        Assert.assertFalse(priceText.isEmpty(), "Product price text should not be empty.");
    }

    @Test(description = "Verify product description is displayed on product details page")
    public void verifyProductDescriptionDisplayedOnDetailsPage() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.openFirstProductDetails();
        boolean descriptionDisplayed = page.isProductDescriptionDisplayed();
        String descriptionText = page.getProductDescriptionText();

        if (descriptionDisplayed && !descriptionText.isEmpty()) {
            LogUtil.pass("Product description is displayed on product details page.");
        } else {
            LogUtil.defect("Product description is not displayed on product details page.");
        }

        Assert.assertTrue(descriptionDisplayed, "Product description should be displayed on product details page.");
        Assert.assertFalse(descriptionText.isEmpty(), "Product description text should not be empty.");
    }

    @Test(description = "Verify product image is displayed on product details page")
    public void verifyProductImageDisplayedOnDetailsPage() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.openFirstProductDetails();
        boolean imageDisplayed = page.isProductImageDisplayed();
        String imageSrc = page.getProductImageSrc();

        if (imageDisplayed && !imageSrc.isEmpty()) {
            LogUtil.pass("Product image is displayed on product details page.");
        } else {
            LogUtil.defect("Product image is not displayed on product details page.");
        }

        Assert.assertTrue(imageDisplayed, "Product image should be displayed on product details page.");
        Assert.assertFalse(imageSrc.isEmpty(), "Product image source should not be empty.");
    }

    @Test(description = "Verify Add to cart button is displayed on product details page")
    public void verifyAddToCartButtonDisplayedOnDetailsPage() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.openFirstProductDetails();
        boolean addToCartDisplayed = page.isAddToCartButtonDisplayed();

        if (addToCartDisplayed) {
            LogUtil.pass("Add to cart button is displayed on product details page.");
        } else {
            LogUtil.defect("Add to cart button is not displayed on product details page.");
        }

        Assert.assertTrue(addToCartDisplayed, "Add to cart button should be displayed on product details page.");
    }

    @Test(description = "Verify clicked product name matches details page product name")
    public void verifyClickedProductMatchesDetailsPage() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        String expectedProductName = page.getFirstProductNameOnHomePage();
        page.openFirstProductDetails();
        String actualProductName = page.getProductNameText();

        boolean matched = !expectedProductName.isEmpty() && expectedProductName.equals(actualProductName);

        if (matched) {
            LogUtil.pass("Clicked product matches details page product name: " + actualProductName);
        } else {
            LogUtil.defect("Clicked product name does not match details page. Expected: "
                    + expectedProductName + ", Actual: " + actualProductName);
        }

        Assert.assertTrue(matched, "Clicked product name should match details page product name.");
    }

    @Test(description = "Verify product details page URL is correct after clicking product")
    public void verifyProductDetailsPageUrlIsCorrect() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.openFirstProductDetails();
        boolean correctUrl = page.doesCurrentUrlContainProductId();
        String currentUrl = page.getCurrentUrl();

        if (correctUrl) {
            LogUtil.pass("Product details page URL is correct: " + currentUrl);
        } else {
            LogUtil.defect("Product details page URL is incorrect: " + currentUrl);
        }

        Assert.assertTrue(correctUrl, "Product details page URL should contain product details pattern.");
    }

    @Test(description = "Verify Add to cart on product details page shows success alert")
    public void verifyAddToCartShowsSuccessAlert() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.openFirstProductDetails();
        String alertText = page.clickAddToCartAndGetAlertText();

        boolean successAlert = alertText != null && !alertText.isEmpty();

        if (successAlert) {
            LogUtil.pass("Add to cart success alert displayed: " + alertText);
        } else {
            LogUtil.defect("Add to cart success alert was not displayed.");
        }

        Assert.assertNotNull(alertText, "Add to cart should show an alert.");
    }

    @Test(description = "Verify Phones category product can open product details page")
    public void verifyPhonesCategoryProductDetailsOpen() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.clickPhonesCategory();
        String expectedProduct = page.getFirstProductNameOnHomePage();
        page.openFirstProductDetails();

        boolean detailsOpened = page.isOnProductDetailsPage();
        boolean productMatched = expectedProduct.equals(page.getProductNameText());

        if (detailsOpened && productMatched) {
            LogUtil.pass("Phones category product details page opened successfully.");
        } else {
            LogUtil.defect("Phones category product details page did not open correctly.");
        }

        Assert.assertTrue(detailsOpened, "Phones category product details page should open.");
        Assert.assertTrue(productMatched, "Phones category clicked product should match details page.");
    }

    @Test(description = "Verify Laptops category product can open product details page")
    public void verifyLaptopsCategoryProductDetailsOpen() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.clickLaptopsCategory();
        String expectedProduct = page.getFirstProductNameOnHomePage();
        page.openFirstProductDetails();

        boolean detailsOpened = page.isOnProductDetailsPage();
        boolean productMatched = expectedProduct.equals(page.getProductNameText());

        if (detailsOpened && productMatched) {
            LogUtil.pass("Laptops category product details page opened successfully.");
        } else {
            LogUtil.defect("Laptops category product details page did not open correctly.");
        }

        Assert.assertTrue(detailsOpened, "Laptops category product details page should open.");
        Assert.assertTrue(productMatched, "Laptops category clicked product should match details page.");
    }

    @Test(description = "Verify Monitors category product can open product details page")
    public void verifyMonitorsCategoryProductDetailsOpen() {
        ProductDetailsPage page = new ProductDetailsPage(driver);

        page.clickMonitorsCategory();
        String expectedProduct = page.getFirstProductNameOnHomePage();
        page.openFirstProductDetails();

        boolean detailsOpened = page.isOnProductDetailsPage();
        boolean productMatched = expectedProduct.equals(page.getProductNameText());

        if (detailsOpened && productMatched) {
            LogUtil.pass("Monitors category product details page opened successfully.");
        } else {
            LogUtil.defect("Monitors category product details page did not open correctly.");
        }

        Assert.assertTrue(detailsOpened, "Monitors category product details page should open.");
        Assert.assertTrue(productMatched, "Monitors category clicked product should match details page.");
    }
}