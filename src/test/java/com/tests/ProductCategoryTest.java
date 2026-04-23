package com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.base.BaseTest;
import com.pages.ProductCategoryPage;
import com.utils.LogUtil;

public class ProductCategoryTest extends BaseTest {

    @Test(description = "Verify product category section is visible")
    public void verifyProductCategorySectionIsVisible() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        boolean visible = page.isCategorySectionDisplayed();

        if (visible) {
            LogUtil.pass("Product category section is visible.");
        } else {
            LogUtil.defect("Product category section is not visible.");
        }

        Assert.assertTrue(visible, "Product category section should be visible.");
    }

    @Test(description = "Verify Phones, Laptops and Monitors category links are visible")
    public void verifyAllCategoryLinksAreVisible() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        boolean phonesVisible = page.isPhonesCategoryDisplayed();
        boolean laptopsVisible = page.isLaptopsCategoryDisplayed();
        boolean monitorsVisible = page.isMonitorsCategoryDisplayed();

        boolean allVisible = phonesVisible && laptopsVisible && monitorsVisible;

        if (allVisible) {
            LogUtil.pass("Phones, Laptops and Monitors category links are visible.");
        } else {
            LogUtil.defect("One or more category links are not visible.");
        }

        Assert.assertTrue(allVisible, "All category links should be visible.");
    }

    @Test(description = "Verify product cards are visible on initial load")
    public void verifyProductCardsVisibleOnInitialLoad() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        boolean productsVisible = page.areProductsVisible();

        if (productsVisible) {
            LogUtil.pass("Product cards are visible on initial load.");
        } else {
            LogUtil.defect("Product cards are not visible on initial load.");
        }

        Assert.assertTrue(productsVisible, "Product cards should be visible on initial load.");
    }

    @Test(description = "Verify Phones category displays products")
    public void verifyPhonesCategoryDisplaysProducts() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        page.clickPhonesCategory();
        int count = page.getVisibleProductCount();

        if (count > 0) {
            LogUtil.pass("Phones category displays products. Count: " + count);
        } else {
            LogUtil.defect("Phones category does not display products.");
        }

        Assert.assertTrue(count > 0, "Phones category should display at least one product.");
    }

    @Test(description = "Verify Laptops category displays products")
    public void verifyLaptopsCategoryDisplaysProducts() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        page.clickLaptopsCategory();
        int count = page.getVisibleProductCount();

        if (count > 0) {
            LogUtil.pass("Laptops category displays products. Count: " + count);
        } else {
            LogUtil.defect("Laptops category does not display products.");
        }

        Assert.assertTrue(count > 0, "Laptops category should display at least one product.");
    }

    @Test(description = "Verify Monitors category displays products")
    public void verifyMonitorsCategoryDisplaysProducts() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        page.clickMonitorsCategory();
        int count = page.getVisibleProductCount();

        if (count > 0) {
            LogUtil.pass("Monitors category displays products. Count: " + count);
        } else {
            LogUtil.defect("Monitors category does not display products.");
        }

        Assert.assertTrue(count > 0, "Monitors category should display at least one product.");
    }

    @Test(description = "Verify Phones category shows phone-related products")
    public void verifyPhonesCategoryShowsRelevantProducts() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        page.clickPhonesCategory();
        boolean relevant = page.containsAnyPhoneProducts();

        if (relevant) {
            LogUtil.pass("Phones category shows relevant phone products.");
        } else {
            LogUtil.defect("Phones category may not be showing relevant phone products.");
        }

        Assert.assertTrue(relevant, "Phones category should show relevant phone products.");
    }

    @Test(description = "Verify Laptops category shows laptop-related products")
    public void verifyLaptopsCategoryShowsRelevantProducts() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        page.clickLaptopsCategory();
        boolean relevant = page.containsAnyLaptopProducts();

        if (relevant) {
            LogUtil.pass("Laptops category shows relevant laptop products.");
        } else {
            LogUtil.defect("Laptops category may not be showing relevant laptop products.");
        }

        Assert.assertTrue(relevant, "Laptops category should show relevant laptop products.");
    }

    @Test(description = "Verify Monitors category shows monitor-related products")
    public void verifyMonitorsCategoryShowsRelevantProducts() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        page.clickMonitorsCategory();
        boolean relevant = page.containsAnyMonitorProducts();

        if (relevant) {
            LogUtil.pass("Monitors category shows relevant monitor products.");
        } else {
            LogUtil.defect("Monitors category may not be showing relevant monitor products.");
        }

        Assert.assertTrue(relevant, "Monitors category should show relevant monitor products.");
    }

    @Test(description = "Verify switching between categories updates the product list")
    public void verifySwitchingBetweenCategoriesUpdatesProductList() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        page.clickPhonesCategory();
        String phonesFirst = page.getFirstProductTitle();

        page.clickLaptopsCategory();
        String laptopsFirst = page.getFirstProductTitle();

        page.clickMonitorsCategory();
        String monitorsFirst = page.getFirstProductTitle();

        boolean changed = !phonesFirst.isEmpty()
                && !laptopsFirst.isEmpty()
                && !monitorsFirst.isEmpty()
                && !phonesFirst.equals(laptopsFirst)
                && !laptopsFirst.equals(monitorsFirst);

        if (changed) {
            LogUtil.pass("Switching between categories updates the product list.");
        } else {
            LogUtil.defect("Switching between categories did not clearly update the product list.");
        }

        Assert.assertTrue(changed, "Product list should update when switching categories.");
    }

    @Test(description = "Verify Next button is visible for product pagination")
    public void verifyNextButtonIsVisible() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        boolean visible = page.isNextButtonDisplayed();

        if (visible) {
            LogUtil.pass("Next pagination button is visible.");
        } else {
            LogUtil.defect("Next pagination button is not visible.");
        }

        Assert.assertTrue(visible, "Next pagination button should be visible.");
    }

    @Test(description = "Verify clicking Next changes the product list")
    public void verifyClickingNextChangesProductList() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        boolean changed = page.didProductListChangeAfterNext();

        if (changed) {
            LogUtil.pass("Clicking Next changes the product list.");
        } else {
            LogUtil.defect("Clicking Next did not change the product list.");
        }

        Assert.assertTrue(changed, "Clicking Next should change the product list.");
    }

    @Test(description = "Verify Previous button works after clicking Next")
    public void verifyPreviousWorksAfterClickingNext() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        page.clickNextPage();
        boolean changedBack = page.didProductListChangeAfterPrevious();

        if (changedBack) {
            LogUtil.pass("Clicking Previous changes the product list after navigating Next.");
        } else {
            LogUtil.defect("Clicking Previous did not change the product list after navigating Next.");
        }

        Assert.assertTrue(changedBack, "Clicking Previous should change the product list.");
    }

    @Test(description = "Verify each category returns at least one product")
    public void verifyEachCategoryReturnsAtLeastOneProduct() {
        ProductCategoryPage page = new ProductCategoryPage(driver);

        page.clickPhonesCategory();
        int phonesCount = page.getVisibleProductCount();

        page.clickLaptopsCategory();
        int laptopsCount = page.getVisibleProductCount();

        page.clickMonitorsCategory();
        int monitorsCount = page.getVisibleProductCount();

        boolean allPositive = phonesCount > 0 && laptopsCount > 0 && monitorsCount > 0;

        if (allPositive) {
            LogUtil.pass("Each category returns at least one product.");
        } else {
            LogUtil.defect("One or more categories returned zero products.");
        }

        Assert.assertTrue(allPositive, "Each category should return at least one product.");
    }
}