package com.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {

     WebDriver driver;
     WebDriverWait wait;

     By signUpLink = By.id("signin2");
     By signUpModal = By.id("signInModal");
     By signUpModalTitle = By.id("signInModalLabel");

     By usernameLabel = By.xpath("//label[@for='sign-username']");
     By passwordLabel = By.xpath("//label[@for='sign-password']");

     By usernameInput = By.id("sign-username");
     By passwordInput = By.id("sign-password");

     By signUpButton = By.xpath("//div[@id='signInModal']//button[text()='Sign up']");
     By closeButton = By.xpath("//div[@id='signInModal']//button[text()='Close']");
     By closeIcon = By.xpath("//div[@id='signInModal']//button[@class='close']");

    public SignupPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isSignUpLinkDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(signUpLink)).isDisplayed();
    }

    public void clickSignUpLink() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(signUpModal));
    }

    public boolean isSignUpModalDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(signUpModal)).isDisplayed();
    }

    public String getSignUpModalTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(signUpModalTitle)).getText().trim();
    }

    public boolean isUsernameInputDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).isDisplayed();
    }

    public boolean isPasswordInputDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).isDisplayed();
    }

    public boolean isSignUpButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(signUpButton)).isDisplayed();
    }

    public boolean isCloseButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(closeButton)).isDisplayed();
    }

    public boolean isCloseIconDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(closeIcon)).isDisplayed();
    }

    public String getUsernameLabelText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLabel)).getText().trim();
    }

    public String getPasswordLabelText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLabel)).getText().trim();
    }

    public void enterUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        field.clear();
        field.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        field.clear();
        field.sendKeys(password);
    }

    public void clickSignUpButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpButton)).click();
    }

    public String acceptAlertAndGetText() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText().trim();
        alert.accept();
        return text;
    }

    public String getAlertTextWithinSeconds(int seconds) {
        WebDriverWait shortWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(seconds));
        Alert alert = shortWait.until(ExpectedConditions.alertIsPresent());
        String text = alert.getText().trim();
        alert.accept();
        return text;
    }

    public void closeModalUsingCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(signUpModal));
    }

    public void closeModalUsingXIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(closeIcon)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(signUpModal));
    }

    public String getUsernameFieldValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).getAttribute("value");
    }

    public String getPasswordFieldValue() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).getAttribute("value");
    }

    public boolean hasEmailField() {
        return driver.findElements(
                By.xpath("//input[contains(@id,'email') or contains(@name,'email') or @type='email']")
        ).size() > 0;
    }

    public boolean hasConfirmPasswordField() {
        return driver.findElements(
                By.xpath("//input[contains(@id,'confirm') or contains(@name,'confirm') or contains(@placeholder,'Confirm')]")
        ).size() > 0;
    }

    public boolean hasShowHidePasswordToggle() {
        return driver.findElements(
                By.xpath("//input[@id='sign-password']/following::*[contains(text(),'Show') or contains(text(),'Hide')]")
        ).size() > 0;
    }

    public boolean isModalClosed() {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(signUpModal));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clearAndTypeUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        field.click();
        field.clear();
        field.sendKeys(username);
    }

    public void clearAndTypePassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        field.click();
        field.clear();
        field.sendKeys(password);
    }

    public boolean isEmailFieldDisplayed() {
        return hasEmailField();
    }
}