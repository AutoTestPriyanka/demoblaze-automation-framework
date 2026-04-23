package com.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductCategoryPage {

     WebDriver driver;
     WebDriverWait wait;

     By categorySection = By.id("cat");
     By phonesCategory = By.xpath("//a[text()='Phones']");
     By laptopsCategory = By.xpath("//a[text()='Laptops']");
     By monitorsCategory = By.xpath("//a[text()='Monitors']");

     By productContainer = By.id("tbodyid");
     By productCards = By.cssSelector("#tbodyid .card");
     By productTitles = By.cssSelector("#tbodyid .card-title a");

     By nextButton = By.id("next2");
     By previousButton = By.id("prev2");

    public ProductCategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(12));
    }

    public boolean isCategorySectionDisplayed() {
        return isElementVisible(categorySection);
    }

    public boolean isPhonesCategoryDisplayed() {
        return isElementVisible(phonesCategory);
    }

    public boolean isLaptopsCategoryDisplayed() {
        return isElementVisible(laptopsCategory);
    }

    public boolean isMonitorsCategoryDisplayed() {
        return isElementVisible(monitorsCategory);
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

    public boolean areProductsVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
            wait.until(driver -> driver.findElements(productCards).size() > 0);
            return driver.findElements(productCards).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getVisibleProductCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
            wait.until(driver -> driver.findElements(productCards).size() > 0);
            return driver.findElements(productCards).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getVisibleProductTitles() {
        List<String> titlesList = new ArrayList<>();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
            wait.until(driver -> driver.findElements(productTitles).size() > 0);

            List<WebElement> titles = driver.findElements(productTitles);
            for (WebElement title : titles) {
                String text = title.getText().trim();
                if (!text.isEmpty()) {
                    titlesList.add(text);
                }
            }
        } catch (Exception e) {
            // ignore
        }

        return titlesList;
    }

    public String getFirstProductTitle() {
        List<String> titles = getVisibleProductTitles();
        return titles.isEmpty() ? "" : titles.get(0);
    }

    public boolean isNextButtonDisplayed() {
        return isElementVisible(nextButton);
    }

    public boolean isPreviousButtonDisplayed() {
        return isElementVisible(previousButton);
    }

    public void clickNextPage() {
        String before = getFirstProductTitle();
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        waitForProductListChange(before);
    }

    public void clickPreviousPage() {
        String before = getFirstProductTitle();
        wait.until(ExpectedConditions.elementToBeClickable(previousButton)).click();
        waitForProductListChange(before);
    }

    public boolean didProductListChangeAfterNext() {
        String before = getFirstProductTitle();
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        return waitForProductListChange(before);
    }

    public boolean didProductListChangeAfterPrevious() {
        String before = getFirstProductTitle();
        wait.until(ExpectedConditions.elementToBeClickable(previousButton)).click();
        return waitForProductListChange(before);
    }

    public boolean containsAnyPhoneProducts() {
        return containsAnyKeyword("phone", "iphone", "samsung", "nokia", "sony", "htc");
    }

    public boolean containsAnyLaptopProducts() {
        return containsAnyKeyword("laptop", "macbook", "dell", "sony vaio", "asus");
    }

    public boolean containsAnyMonitorProducts() {
        return containsAnyKeyword("monitor", "apple monitor", "asus full hd");
    }

    void clickCategoryAndWait(By categoryLocator) {
        String before = getFirstProductTitle();
        wait.until(ExpectedConditions.elementToBeClickable(categoryLocator)).click();

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productContainer));
            wait.until(driver -> {
                try {
                    List<WebElement> titles = driver.findElements(productTitles);
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
            wait.until(driver -> driver.findElements(productCards).size() > 0);
        }
    }

    boolean waitForProductListChange(String beforeFirstTitle) {
        try {
            return wait.until(driver -> {
                try {
                    List<WebElement> titles = driver.findElements(productTitles);
                    if (titles.isEmpty()) {
                        return false;
                    }
                    String after = titles.get(0).getText().trim();
                    return !after.isEmpty() && !after.equals(beforeFirstTitle);
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    boolean containsAnyKeyword(String... keywords) {
        List<String> titles = getVisibleProductTitles();

        for (String title : titles) {
            String lower = title.toLowerCase();
            for (String keyword : keywords) {
                if (lower.contains(keyword.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}