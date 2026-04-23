package com.pages;

import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PlaceOrderPage {

    WebDriver driver;
    WebDriverWait wait;

    public PlaceOrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ================= LOCATORS =================

    // Home
    By products = By.cssSelector("#tbodyid .card-title a");

    // Product
    By addToCartBtn = By.xpath("//a[contains(text(),'Add to cart')]");

    // Cart
    By cartLink = By.id("cartur");
    By cartTable = By.id("tbodyid");
    By cartRows = By.cssSelector("#tbodyid tr");
    By totalLabel = By.id("totalp");

    // Place Order
    By placeOrderBtn = By.xpath("//button[contains(text(),'Place Order')]");
    By placeOrderModal = By.id("orderModal");
    By modalTitle = By.id("orderModalLabel");

    // Fields
    By name = By.id("name");
    By country = By.id("country");
    By city = By.id("city");
    By card = By.id("card");
    By month = By.id("month");
    By year = By.id("year");

    // Buttons
    By closeBtn = By.xpath("//button[text()='Close']");
    By purchaseBtn = By.xpath("//button[text()='Purchase']");
    By closeX = By.xpath("//div[@id='orderModal']//button[@class='close']");

    // Popup
    By popup = By.cssSelector(".sweet-alert.showSweetAlert.visible");
    By popupText = By.cssSelector(".sweet-alert p");
    By popupTitle = By.cssSelector(".sweet-alert h2");
    By okBtn = By.cssSelector(".sweet-alert .confirm");

    // ================= HOME =================

    public boolean addFirstProductToCartAndGoToCart() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(products));
            driver.findElements(products).get(0).click();

            wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();

            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();

            clickCartLink();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // ================= CART =================

    public void clickCartLink() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
    }

    public boolean isPlaceOrderButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderBtn)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getTotalText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(totalLabel)).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public int getCartRowCount() {
        return driver.findElements(cartRows).size();
    }

    public boolean deleteAllCartItems() {
        try {
            clickCartLink();
            while (getCartRowCount() > 0) {
                driver.findElements(By.linkText("Delete")).get(0).click();
                Thread.sleep(1000); // simple sync for beginners
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCartEmpty() {
        return getCartRowCount() == 0;
    }

    // ================= PLACE ORDER =================

    public void clickPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderModal));
    }

    public boolean isPlaceOrderModalDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderModal)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getPlaceOrderModalTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle)).getText().trim();
    }

    // ================= FIELD VISIBILITY =================

    public boolean isNameFieldDisplayed() { return isDisplayed(name); }
    public boolean isCountryFieldDisplayed() { return isDisplayed(country); }
    public boolean isCityFieldDisplayed() { return isDisplayed(city); }
    public boolean isCardFieldDisplayed() { return isDisplayed(card); }
    public boolean isMonthFieldDisplayed() { return isDisplayed(month); }
    public boolean isYearFieldDisplayed() { return isDisplayed(year); }

    public boolean isCloseButtonDisplayed() { return isDisplayed(closeBtn); }
    public boolean isPurchaseButtonDisplayed() { return isDisplayed(purchaseBtn); }

    // ================= MODAL ACTION =================

    public void clickCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeBtn)).click();
    }

    public void clickCloseXButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeX)).click();
    }

    public boolean reopenPlaceOrderModalSuccessfully() {
        clickCloseButton();
        clickPlaceOrder();
        return isPlaceOrderModalDisplayed();
    }

    // ================= FORM =================

    public void fillPlaceOrderForm(String n, String c, String ci, String ca, String m, String y) {
        type(name, n);
        type(country, c);
        type(city, ci);
        type(card, ca);
        type(month, m);
        type(year, y);
    }

    public String getNameValue() { return getValue(name); }
    public String getCountryValue() { return getValue(country); }
    public String getCityValue() { return getValue(city); }
    public String getCardValue() { return getValue(card); }
    public String getMonthValue() { return getValue(month); }
    public String getYearValue() { return getValue(year); }

    public void enterMonth(String val) { type(month, val); }
    public void enterYear(String val) { type(year, val); }

    public boolean isNumeric(String val) {
        return val.matches("\\d+");
    }

    public String generateLongText(int len) {
        return "A".repeat(len);
    }

    // ================= PURCHASE =================

    public boolean purchaseWithData(String n, String c, String ci, String ca, String m, String y) {
        try {
            fillPlaceOrderForm(n, c, ci, ca, m, y);
            wait.until(ExpectedConditions.elementToBeClickable(purchaseBtn)).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(popup));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isConfirmationPopupDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(popup)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getConfirmationText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(popupText)).getText();
    }

    public String getConfirmationTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(popupTitle)).getText();
    }

    public boolean isConfirmationOkButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(okBtn)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickConfirmationOk() {
        wait.until(ExpectedConditions.elementToBeClickable(okBtn)).click();
    }

    public boolean isConfirmationPopupClosed() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(popup));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCartClearedAfterPurchase() {
        clickConfirmationOk();
        return getCartRowCount() == 0;
    }

    // ================= HELPERS =================

    void type(By locator, String value) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        el.clear();
        el.sendKeys(value);
    }

    String getValue(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getAttribute("value");
    }

    boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}