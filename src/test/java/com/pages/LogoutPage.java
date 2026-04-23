package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogoutPage {

     WebDriver driver;
     WebDriverWait wait;

     By loginLink = By.id("login2");
     By logoutLink = By.id("logout2");
     By welcomeUser = By.id("nameofuser");

     By loginModal = By.id("logInModal");
     By usernameInput = By.id("loginusername");
     By passwordInput = By.id("loginpassword");
     By loginButton = By.xpath("//div[@id='logInModal']//button[text()='Log in']");

    public LogoutPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isLoginLinkDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginLink)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isLogoutLinkDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutLink)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isWelcomeUserDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getWelcomeText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser)).getText().trim();
        } catch (TimeoutException e) {
            return "";
        }
    }

    public void openLoginModal() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginModal));
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

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void login(String username, String password) {
        openLoginModal();
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser));
    }

    public void clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
    }

    public boolean isUserLoggedIn() {
        return isWelcomeUserDisplayed() && isLogoutLinkDisplayed();
    }

    public boolean isUserLoggedOut() {
        boolean loginVisible = isLoginLinkDisplayed();
        boolean logoutVisible = driver.findElements(logoutLink).size() > 0 && driver.findElement(logoutLink).isDisplayed();
        boolean welcomeVisible = driver.findElements(welcomeUser).size() > 0 && driver.findElement(welcomeUser).isDisplayed();

        return loginVisible && !logoutVisible && !welcomeVisible;
    }

    public boolean waitForLogoutState() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(loginLink));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(logoutLink));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(welcomeUser));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void navigateBack() {
        driver.navigate().back();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnHomePage() {
        return getCurrentUrl().contains("demoblaze.com");
    }
}