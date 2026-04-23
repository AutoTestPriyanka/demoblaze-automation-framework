package com.pages;

import com.utils.LogUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	WebDriver driver;
	WebDriverWait wait;

	By loginLink = By.id("login2");
	By loginModal = By.id("logInModal");
	By loginModalTitle = By.id("logInModalLabel");

	By usernameLabel = By.xpath("//*[@id='logInModal']/div/div/div[2]/form/div[1]/label");
	By passwordLabel = By.xpath("//*[@id='logInModal']/div/div/div[2]/form/div[2]/label");

	By usernameInput = By.id("loginusername");
	By passwordInput = By.id("loginpassword");

	By loginButton = By.xpath("//div[@id='logInModal']//button[text()='Log in']");
	By closeButton = By.xpath("//div[@id='logInModal']//button[text()='Close']");
	By closeIcon = By.xpath("//div[@id='logInModal']//button[@class='close']");

	By welcomeUser = By.id("nameofuser");
	By logoutLink = By.id("logout2");

	public LoginPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public boolean isLoginLinkDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(loginLink)).isDisplayed();
	}

	public void clickLoginLink() {
		wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(loginModal));
	}

	public boolean isLoginModalDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(loginModal)).isDisplayed();
	}

	public String getLoginModalHeaderText() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(loginModalTitle)).getText().trim();
	}

	public boolean isUsernameInputDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).isDisplayed();
	}

	public boolean isPasswordInputDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).isDisplayed();
	}

	public boolean isLoginButtonDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed();
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

	public void clickLoginButton() {
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
	}

	public String acceptAlertAndGetText() {
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		String text = alert.getText().trim();
		alert.accept();
		return text;
	}

	public void closeModalUsingCloseButton() {
		wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loginModal));
	}

	public void closeModalUsingXIcon() {
		wait.until(ExpectedConditions.elementToBeClickable(closeIcon)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(loginModal));
	}

	public String getUsernameFieldValue() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).getAttribute("value");
	}

	public String getPasswordFieldValue() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).getAttribute("value");
	}

	public boolean isUserLoggedIn() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser)).isDisplayed();
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getWelcomeText() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeUser)).getText().trim();
	}

	public void logoutIfLoggedIn() {
		try {
			if (driver.findElements(logoutLink).size() > 0 && driver.findElement(logoutLink).isDisplayed()) {
				wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
				LogUtil.info("Logged out successfully.");
			}
		} catch (Exception e) {
			LogUtil.info("Logout not required.");
		}
	}

	public boolean hasForgotPasswordOption() {
		return driver
				.findElements(By.xpath("//*[contains(text(),'Forgot Password') or contains(text(),'forgot password')]"))
				.size() > 0;
	}

	public boolean hasProfileViewOption() {
		return driver.findElements(By.xpath("//*[contains(text(),'Profile') or contains(text(),'profile')]"))
				.size() > 0;
	}

	public boolean hasChangePasswordOption() {
		return driver
				.findElements(By.xpath("//*[contains(text(),'Change Password') or contains(text(),'change password')]"))
				.size() > 0;
	}

	public boolean hasOrderHistoryOption() {
		return driver.findElements(By.xpath(
				"//*[contains(text(),'Order History') or contains(text(),'Purchase History') or contains(text(),'history')]"))
				.size() > 0;
	}

	public boolean hasSavedBillingOption() {
		return driver
				.findElements(By.xpath(
						"//*[contains(text(),'Billing') or contains(text(),'Payment') or contains(text(),'Card')]"))
				.size() > 0;
	}

	public boolean hasShowHidePasswordOption() {
		return driver.findElements(By
				.xpath("//input[@id='loginpassword']/following::*[contains(text(),'Show') or contains(text(),'Hide')]"))
				.size() > 0;
	}
}