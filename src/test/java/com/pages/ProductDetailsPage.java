package com.pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailsPage {

     WebDriver driver;
     WebDriverWait wait;

     By productContainer = By.id("tbodyid");
     By productCards = By.cssSelector("#tbodyid .card");
     By productLinks = By.cssSelector("#tbodyid .card-title a");

     By phonesCategory = By.xpath("//a[text()='Phones']");
     By laptopsCategory = By.xpath("//a[text()='Laptops']");
     By monitorsCategory = By.xpath("//a[text()='Monitors']");

     By productName = By.cssSelector(".name");
     By productPrice = By.cssSelector(".price-container");
     By productDescription = By.cssSelector("#more-information p");
     By productImage = By.cssSelector(".item img");
     By addToCartButton = By.xpath("//a[contains(text(),'Add to cart')]");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public boolean areProductsVisibleOnHomePage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
            wait.until(d -> d.findElements(productCards).size() > 0);
            return driver.findElements(productCards).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getVisibleProductCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
            wait.until(d -> d.findElements(productLinks).size() > 0);
            return driver.findElements(productLinks).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getFirstProductNameOnHomePage() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
            wait.until(d -> d.findElements(productLinks).size() > 0);
            List<WebElement> products = driver.findElements(productLinks);
            return products.isEmpty() ? "" : products.get(0).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void openFirstProductDetails() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
        wait.until(d -> d.findElements(productLinks).size() > 0);

        List<WebElement> products = driver.findElements(productLinks);
        if (!products.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(products.get(0))).click();
            waitForProductDetailsPage();
        }
    }

    public void openProductByName(String expectedProductName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
        wait.until(d -> d.findElements(productLinks).size() > 0);

        List<WebElement> products = driver.findElements(productLinks);

        for (WebElement product : products) {
            if (product.getText().trim().equalsIgnoreCase(expectedProductName)) {
                product.click();
                waitForProductDetailsPage();
                return;
            }
        }

        throw new RuntimeException("Product not found on page: " + expectedProductName);
    }

    public void clickPhonesCategory() {
        clickCategoryAndWait(phonesCategory);
    }

    public void clickLaptopsCategory() {
        clickCategoryAndWait(laptopsCategory);
    }

    public void clickMonitorsCategory() {
        clickCategoryAndWait(monitorsCategory);
    }

    public boolean isProductNameDisplayed() {
        return isElementVisible(productName);
    }

    public String getProductNameText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isProductPriceDisplayed() {
        return isElementVisible(productPrice);
    }

    public String getProductPriceText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isProductDescriptionDisplayed() {
        return isElementVisible(productDescription);
    }

    public String getProductDescriptionText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(productDescription)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isProductImageDisplayed() {
        return isElementVisible(productImage);
    }

    public String getProductImageSrc() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(productImage))
                    .getAttribute("src")
                    .trim();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isAddToCartButtonDisplayed() {
        return isElementVisible(addToCartButton);
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public String clickAddToCartAndGetAlertText() {
        clickAddToCart();

        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText().trim();
            alert.accept();
            return alertText;
        } catch (TimeoutException | NoAlertPresentException e) {
            return null;
        }
    }

    public boolean isOnProductDetailsPage() {
        return isProductNameDisplayed()
                && isProductPriceDisplayed()
                && isProductDescriptionDisplayed()
                && isAddToCartButtonDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean doesCurrentUrlContainProductId() {
        String url = getCurrentUrl().toLowerCase();
        return url.contains("prod.html?idp_=") || url.contains("prod.html");
    }

    void waitForProductDetailsPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton));
    }

    void clickCategoryAndWait(By categoryLocator) {
        String before = getFirstProductNameOnHomePage();
        wait.until(ExpectedConditions.elementToBeClickable(categoryLocator)).click();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
            wait.until(d -> {
                try {
                    List<WebElement> titles = d.findElements(productLinks);
                    if (titles.isEmpty()) {
                        return false;
                    }
                    String after = titles.get(0).getText().trim();
                    return !after.isEmpty() && !after.equals(before);
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });
        } catch (TimeoutException e) {
            wait.until(d -> d.findElements(productCards).size() > 0);
        }
    }

    boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}