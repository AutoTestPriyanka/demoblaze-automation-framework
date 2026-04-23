package com.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ContactPage {

	WebDriver driver;
	WebDriverWait wait;

	By contactLink = By.xpath("//a[text()='Contact']");
	By modal = By.id("exampleModal");
	By modalTitle = By.id("exampleModalLabel");

	By emailField = By.id("recipient-email");
	By nameField = By.id("recipient-name");
	By messageField = By.id("message-text");

	By sendButton = By.xpath("//div[@id='exampleModal']//button[text()='Send message']");
	By closeButton = By.xpath("//div[@id='exampleModal']//button[text()='Close']");
	By xButton = By.xpath("//div[@id='exampleModal']//button[contains(@class,'close')]");

	public ContactPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void openModal() {
		wait.until(ExpectedConditions.elementToBeClickable(contactLink)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(modal));
	}

	public boolean isModalDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(modal)).isDisplayed();
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getModalTitleText() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle)).getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isEmailFieldDisplayed() {
		return isDisplayed(emailField);
	}

	public boolean isNameFieldDisplayed() {
		return isDisplayed(nameField);
	}

	public boolean isMessageFieldDisplayed() {
		return isDisplayed(messageField);
	}

	public boolean isSendButtonDisplayed() {
		return isDisplayed(sendButton);
	}

	public boolean isCloseButtonDisplayed() {
		return isDisplayed(closeButton);
	}

	public boolean isXButtonDisplayed() {
		return isDisplayed(xButton);
	}

	public void fillContactForm(String email, String name, String message) {
		WebElement emailBox = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
		WebElement nameBox = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
		WebElement messageBox = wait.until(ExpectedConditions.visibilityOfElementLocated(messageField));

		emailBox.clear();
		nameBox.clear();
		messageBox.clear();

		if (email != null) {
			emailBox.sendKeys(email);
		}
		if (name != null) {
			nameBox.sendKeys(name);
		}
		if (message != null) {
			messageBox.sendKeys(message);
		}
	}

	public String clickSendAndGetAlertText() {
		wait.until(ExpectedConditions.elementToBeClickable(sendButton)).click();

		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			String text = alert.getText();
			alert.accept();
			return text;
		} catch (TimeoutException | NoAlertPresentException e) {
			return null;
		}
	}

	public void closeModalWithCloseButton() {
		wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));
	}

	public String getEmailFieldValue() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).getAttribute("value").trim();
	}

	public String getNameFieldValue() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).getAttribute("value").trim();
	}

	public String getMessageFieldValue() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(messageField)).getAttribute("value").trim();
	}

	boolean isDisplayed(By locator) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}