package com.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

     WebDriver driver;
     WebDriverWait wait;

     By brandLogo = By.id("nava");
     By homeLink = By.xpath("//a[contains(text(),'Home')]");
     By contactLink = By.xpath("//a[text()='Contact']");
     By aboutLink = By.xpath("//a[contains(text(),'About us')]");
     By cartLink = By.id("cartur");
     By loginLink = By.id("login2");
     By signupLink = By.id("signin2");
     By logoutLink = By.id("logout2");

     By contactModal = By.id("exampleModal");
     By aboutModal = By.id("videoModal");

     By carousel = By.id("carouselExampleIndicators");
     By carouselItems = By.cssSelector("#carouselExampleIndicators .carousel-item");
     By activeCarouselItem = By.cssSelector("#carouselExampleIndicators .carousel-item.active");
     By activeCarouselImage = By.cssSelector("#carouselExampleIndicators .carousel-item.active img");
     By carouselNext = By.cssSelector("#carouselExampleIndicators .carousel-control-next");
     By carouselPrev = By.cssSelector("#carouselExampleIndicators .carousel-control-prev");

     By categoriesSection = By.id("cat");
     By phonesCategory = By.xpath("//a[text()='Phones']");
     By laptopsCategory = By.xpath("//a[text()='Laptops']");
     By monitorsCategory = By.xpath("//a[text()='Monitors']");

     By productCards = By.cssSelector("#tbodyid .card");
     By productTitles = By.cssSelector("#tbodyid .card-title a");

     By nextButton = By.id("next2");
     By prevButton = By.id("prev2");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isBrandLogoDisplayed() {
        return isElementVisible(brandLogo);
    }

    public String getBrandLogoText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(brandLogo)).getText().trim();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public boolean isHomeLinkDisplayed() {
        return isElementVisible(homeLink);
    }

    public boolean isContactLinkDisplayed() {
        return isElementVisible(contactLink);
    }

    public boolean isAboutLinkDisplayed() {
        return isElementVisible(aboutLink);
    }

    public boolean isCartLinkDisplayed() {
        return isElementVisible(cartLink);
    }

    public boolean isLoginLinkDisplayed() {
        return isElementVisible(loginLink);
    }

    public boolean isSignupLinkDisplayed() {
        return isElementVisible(signupLink);
    }

    public boolean isLogoutLinkDisplayed() {
        return isElementVisible(logoutLink);
    }

    public void clickHome() {
        wait.until(ExpectedConditions.elementToBeClickable(homeLink)).click();
    }

    public void clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public void clickContact() {
        wait.until(ExpectedConditions.elementToBeClickable(contactLink)).click();
    }

    public void clickAbout() {
        wait.until(ExpectedConditions.elementToBeClickable(aboutLink)).click();
    }

    public boolean isContactModalDisplayed() {
        return isElementVisible(contactModal);
    }

    public boolean isAboutModalDisplayed() {
        return isElementVisible(aboutModal);
    }

    public boolean isCarouselDisplayed() {
        return isElementVisible(carousel);
    }

    public int getCarouselSlideCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(carousel));
            return driver.findElements(carouselItems).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public String getActiveCarouselImageSrc() {
        try {
            WebElement image = wait.until(ExpectedConditions.visibilityOfElementLocated(activeCarouselImage));
            return image.getAttribute("src").trim();
        } catch (Exception e) {
            return "";
        }
    }

    public int getActiveCarouselIndex() {
        try {
            List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(carouselItems));
            for (int i = 0; i < items.size(); i++) {
                String classes = items.get(i).getAttribute("class");
                if (classes != null && classes.contains("active")) {
                    return i;
                }
            }
        } catch (Exception e) {
            // ignore
        }
        return -1;
    }

    public void clickCarouselNext() {
        wait.until(ExpectedConditions.elementToBeClickable(carouselNext)).click();
    }

    public void clickCarouselPrevious() {
        wait.until(ExpectedConditions.elementToBeClickable(carouselPrev)).click();
    }

    public boolean didCarouselMoveToNextSlide() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(carousel));

            int beforeIndex = getActiveCarouselIndex();
            String beforeSrc = getActiveCarouselImageSrc();

            wait.until(ExpectedConditions.elementToBeClickable(carouselNext)).click();

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));
            return shortWait.until(driver -> {
                int afterIndex = getActiveCarouselIndex();
                String afterSrc = getActiveCarouselImageSrc();

                boolean indexChanged = beforeIndex != -1 && afterIndex != -1 && beforeIndex != afterIndex;
                boolean srcChanged = !beforeSrc.isEmpty() && !afterSrc.isEmpty() && !beforeSrc.equals(afterSrc);

                return indexChanged || srcChanged;
            });
        } catch (Exception e) {
            return false;
        }
    }

    public boolean didCarouselMoveToPreviousSlide() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(carousel));

            int beforeIndex = getActiveCarouselIndex();
            String beforeSrc = getActiveCarouselImageSrc();

            wait.until(ExpectedConditions.elementToBeClickable(carouselPrev)).click();

            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));
            return shortWait.until(driver -> {
                int afterIndex = getActiveCarouselIndex();
                String afterSrc = getActiveCarouselImageSrc();

                boolean indexChanged = beforeIndex != -1 && afterIndex != -1 && beforeIndex != afterIndex;
                boolean srcChanged = !beforeSrc.isEmpty() && !afterSrc.isEmpty() && !beforeSrc.equals(afterSrc);

                return indexChanged || srcChanged;
            });
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCategoriesSectionDisplayed() {
        return isElementVisible(categoriesSection);
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
        wait.until(ExpectedConditions.elementToBeClickable(phonesCategory)).click();
        waitForProductsToLoad();
    }

    public void clickLaptopsCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(laptopsCategory)).click();
        waitForProductsToLoad();
    }

    public void clickMonitorsCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(monitorsCategory)).click();
        waitForProductsToLoad();
    }

    public boolean areProductCardsVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productCards));
            return driver.findElements(productCards).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public int getVisibleProductCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productCards));
            return driver.findElements(productCards).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isNextButtonDisplayed() {
        return isElementVisible(nextButton);
    }

    public boolean isPreviousButtonDisplayed() {
        return isElementVisible(prevButton);
    }

    public void clickNextProductPage() {
        wait.until(ExpectedConditions.elementToBeClickable(nextButton)).click();
        waitForProductsToLoad();
    }

    public void clickPreviousProductPage() {
        wait.until(ExpectedConditions.elementToBeClickable(prevButton)).click();
        waitForProductsToLoad();
    }

    public String getFirstProductTitle() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(productTitles));
            List<WebElement> titles = driver.findElements(productTitles);
            if (!titles.isEmpty()) {
                return titles.get(0).getText().trim();
            }
        } catch (Exception e) {
            // ignore
        }
        return "";
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnHomePage() {
        String url = driver.getCurrentUrl().toLowerCase();
        return url.contains("index.html") || url.equals("https://demoblaze.com/") || url.equals("https://demoblaze.com");
    }

    public boolean isOnCartPage() {
        return driver.getCurrentUrl().contains("cart.html");
    }

    boolean isElementVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    void waitForProductsToLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
            wait.until(driver -> driver.findElements(productCards).size() > 0);
        } catch (Exception e) {
            // ignore
        }
    }
}