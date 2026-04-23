package com.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PurchasePage {

	WebDriver driver;
	WebDriverWait wait;

	// Navigation
	By homeLink = By.xpath("//a[contains(text(),'Home')]");
	By cartLink = By.id("cartur");

	// Home/Product
	By productCards = By.cssSelector("#tbodyid .card");
	By productTitles = By.cssSelector("#tbodyid .card-title a");
	By addToCartLink = By.xpath("//a[contains(text(),'Add to cart')]");

	// Cart
	By cartRows = By.cssSelector("#tbodyid > tr");
	By rowNames = By.xpath("//tr/td[2]");
	By deleteLinks = By.xpath("//a[text()='Delete']");
	By totalAmount = By.id("totalp");

	// Place Order
	By placeOrderButton = By.xpath("//button[contains(text(),'Place Order')]");
	By placeOrderModal = By.id("orderModal");
	By placeOrderModalTitle = By.id("orderModalLabel");

	By nameField = By.id("name");
	By countryField = By.id("country");
	By cityField = By.id("city");
	By cardField = By.id("card");
	By monthField = By.id("month");
	By yearField = By.id("year");

	By purchaseButton = By.xpath("//button[contains(text(),'Purchase')]");
	By closeButton = By.xpath("//div[@id='orderModal']//button[contains(text(),'Close')]");

	By successPopup = By.cssSelector(".sweet-alert.showSweetAlert.visible");
	By successTitle = By.cssSelector(".sweet-alert.showSweetAlert.visible h2");
	By successText = By.cssSelector(".sweet-alert.showSweetAlert.visible p");
	By successOkButton = By.cssSelector(".sweet-alert.showSweetAlert.visible .confirm");

	public PurchasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	// NAVIGATION

	public void goHome() {
		wait.until(ExpectedConditions.elementToBeClickable(homeLink)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(productCards));
	}

	public String openProductByIndex(int index) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(productCards));
		List<WebElement> products = driver.findElements(productTitles);
		String productName = products.get(index).getText().trim();
		products.get(index).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartLink));
		return productName;
	}

	public void addCurrentProductToCart() {
		wait.until(ExpectedConditions.elementToBeClickable(addToCartLink)).click();
		wait.until(ExpectedConditions.alertIsPresent());
		driver.switchTo().alert().accept();
	}

	public void addProductToCartByIndex(int index) {
		goHome();
		openProductByIndex(index);
		addCurrentProductToCart();
	}

	public void addMultipleProductsToCart(int... indexes) {
		for (int index : indexes) {
			addProductToCartByIndex(index);
		}
	}

	public void openCartPage() {
		wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderButton));
	}

	public void openPlaceOrderModal() {
		wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderModal));
	}

	public boolean isPlaceOrderModalDisplayed() {
		try {
			return driver.findElement(placeOrderModal).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getPlaceOrderModalTitle() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderModalTitle)).getText().trim();
	}

	// FORM ACTIONS

	public void fillOrderForm(String name, String country, String city, String card, String month, String year) {
		WebElement nameEl = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
		nameEl.clear();
		nameEl.sendKeys(name);

		WebElement countryEl = driver.findElement(countryField);
		countryEl.clear();
		countryEl.sendKeys(country);

		WebElement cityEl = driver.findElement(cityField);
		cityEl.clear();
		cityEl.sendKeys(city);

		WebElement cardEl = driver.findElement(cardField);
		cardEl.clear();
		cardEl.sendKeys(card);

		WebElement monthEl = driver.findElement(monthField);
		monthEl.clear();
		monthEl.sendKeys(month);

		WebElement yearEl = driver.findElement(yearField);
		yearEl.clear();
		yearEl.sendKeys(year);
	}

	public void clickPurchase() {
		wait.until(ExpectedConditions.elementToBeClickable(purchaseButton)).click();
	}

	public void clickPurchaseMultipleTimesFast(int times) {
		WebElement purchaseBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(purchaseButton));
		for (int i = 0; i < times; i++) {
			try {
				purchaseBtn.click();
			} catch (Exception ignored) {
			}
		}
	}

	public boolean isPurchaseButtonEnabled() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(purchaseButton)).isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	// SUCCESS POPUP

	public boolean isSuccessPopupDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
			return driver.findElement(successPopup).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getSuccessPopupTitle() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(successTitle)).getText().trim();
	}

	public String getSuccessPopupText() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(successText)).getText().trim();
	}

	public boolean isOkButtonVisible() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(successOkButton)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isOkButtonClickable() {
		try {
			WebElement ok = wait.until(ExpectedConditions.elementToBeClickable(successOkButton));
			return ok.isDisplayed() && ok.isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickOkButton() {
		wait.until(ExpectedConditions.elementToBeClickable(successOkButton)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(successPopup));
	}

	public boolean isSuccessPopupClosed() {
		try {
			return driver.findElements(successPopup).isEmpty() || !driver.findElement(successPopup).isDisplayed();
		} catch (Exception e) {
			return true;
		}
	}

	public void clickOutsideSuccessPopup() {
		try {
			WebElement popup = wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
			new Actions(driver).moveToElement(popup, -200, -200).click().perform();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("document.body.click();");
		}
	}

	public boolean isBackgroundInteractionBlockedWhilePopupOpen() {
		try {
			String beforeUrl = driver.getCurrentUrl();

			try {
				driver.findElement(homeLink).click();
			} catch (Exception ignored) {
			}

			String afterUrl = driver.getCurrentUrl();
			boolean popupStillVisible = isSuccessPopupDisplayed();

			return popupStillVisible && beforeUrl.equals(afterUrl);
		} catch (Exception e) {
			return false;
		}
	}

	// POPUP DATA EXTRACTION

	public String extractPurchaseId() {
		return extractValue("Id");
	}

	public String extractPurchaseAmount() {
		return extractValue("Amount");
	}

	public String extractPurchaseCardNumber() {
		return extractValue("Card Number");
	}

	public String extractPurchaseName() {
		return extractValue("Name");
	}

	public String extractPurchaseDate() {
		return extractValue("Date");
	}

	String extractValue(String key) {
		String text = getSuccessPopupText();
		String[] lines = text.split("\\r?\\n");

		for (String line : lines) {
			if (line.trim().startsWith(key)) {
				int index = line.indexOf(":");
				if (index != -1) {
					return line.substring(index + 1).trim();
				}
			}
		}
		return "";
	}

	public boolean popupContainsAllPurchaseDetails() {
		return !extractPurchaseId().isEmpty() && !extractPurchaseAmount().isEmpty()
				&& !extractPurchaseCardNumber().isEmpty() && !extractPurchaseName().isEmpty()
				&& !extractPurchaseDate().isEmpty();
	}

	public boolean purchaseDetailsMatch(String expectedName, String expectedCard) {
		return extractPurchaseName().equals(expectedName) && extractPurchaseCardNumber().equals(expectedCard);
	}

	public boolean purchaseDateMatchesToday() {
		String popupDate = extractPurchaseDate();
		if (popupDate.isEmpty()) {
			return false;
		}

		LocalDate today = LocalDate.now();
		List<DateTimeFormatter> formatters = new ArrayList<>();
		formatters.add(DateTimeFormatter.ofPattern("d/M/yyyy"));
		formatters.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		for (DateTimeFormatter formatter : formatters) {
			try {
				LocalDate parsed = LocalDate.parse(popupDate, formatter);
				return parsed.equals(today);
			} catch (Exception ignored) {
			}
		}
		return false;
	}

	public boolean isOrderHistoryVisibleAfterPurchase() {
		By orderHistory = By.xpath("//*[contains(text(),'Order history') or contains(text(),'Orders')]");
		return !driver.findElements(orderHistory).isEmpty();
	}

	// CART

	public int getCartItemCount() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderButton));
		return driver.findElements(cartRows).size();
	}

	public List<String> getCartItemNames() {
		List<String> names = new ArrayList<>();
		List<WebElement> rows = driver.findElements(rowNames);
		for (WebElement row : rows) {
			names.add(row.getText().trim());
		}
		return names;
	}

	public int getTotalAmount() {
		String total = wait.until(ExpectedConditions.visibilityOfElementLocated(totalAmount)).getText().trim();
		return total.isEmpty() ? 0 : Integer.parseInt(total);
	}

	public void deleteAllItemsFromCart() {
		openCartPage();

		int attempts = 0;
		while (!driver.findElements(deleteLinks).isEmpty() && attempts < 10) {
			driver.findElements(deleteLinks).get(0).click();
			sleep(1500);
			attempts++;
		}
	}

	// PURCHASE

	public void purchaseProduct(String name, String country, String city, String card, String month, String year) {
		openCartPage();
		openPlaceOrderModal();
		fillOrderForm(name, country, city, card, month, year);
		clickPurchase();
		wait.until(ExpectedConditions.visibilityOfElementLocated(successPopup));
	}

	public void addOneProductAndPurchase(int productIndex, String name, String country, String city, String card,
			String month, String year) {
		addProductToCartByIndex(productIndex);
		purchaseProduct(name, country, city, card, month, year);
	}

	void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}