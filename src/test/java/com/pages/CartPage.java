package com.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

	 WebDriver driver;
	 WebDriverWait wait;

	// Home page
	 By homeProducts = By.cssSelector("#tbodyid .card-title a");

	// Product details page
	 By productName = By.cssSelector(".name");
	 By productPrice = By.cssSelector(".price-container");
	 By addToCartButton = By.xpath("//a[contains(text(),'Add to cart')]");

	// Navbar
	 By cartLink = By.id("cartur");

	// Cart page
	 By cartTable = By.id("tbodyid");
	 By cartRows = By.cssSelector("#tbodyid tr");
	 By totalLabel = By.id("totalp");
	 By placeOrderButton = By.xpath("//button[contains(text(),'Place Order')]");

	// Place order modal
	 By placeOrderModal = By.id("orderModal");

	public CartPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	}

	// ---------------- HOME PAGE ----------------

	public String getFirstProductNameOnHomePage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(homeProducts));
		List<WebElement> products = driver.findElements(homeProducts);
		if (products.isEmpty()) {
			return "";
		}
		return products.get(0).getText().trim();
	}

	public String getSecondProductNameOnHomePage() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(homeProducts));
		List<WebElement> products = driver.findElements(homeProducts);
		if (products.size() < 2) {
			return "";
		}
		return products.get(1).getText().trim();
	}

	public void openFirstProduct() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(homeProducts));
		List<WebElement> products = driver.findElements(homeProducts);
		if (!products.isEmpty()) {
			clickUsingJsIfNeeded(products.get(0));
			wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
		}
	}

	public void openProductByName(String expectedName) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(homeProducts));
		List<WebElement> products = driver.findElements(homeProducts);

		for (WebElement product : products) {
			if (product.getText().trim().equalsIgnoreCase(expectedName)) {
				clickUsingJsIfNeeded(product);
				wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
				return;
			}
		}

		throw new RuntimeException("Product not found: " + expectedName);
	}

	public void navigateToHomePage() {
		driver.navigate().to("https://demoblaze.com/index.html");
		wait.until(ExpectedConditions.visibilityOfElementLocated(homeProducts));
	}

	// ---------------- PRODUCT DETAILS ----------------

	public String getProductDetailsName() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isAddToCartButtonDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartButton)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getProductPriceFromDetailsPage() {
		try {
			String priceText = wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice)).getText().trim();
			String numeric = priceText.replaceAll("[^0-9]", "");
			if (numeric.isEmpty()) {
				return 0;
			}
			return Integer.parseInt(numeric);
		} catch (Exception e) {
			return 0;
		}
	}

	public String clickAddToCartAndGetAlertText() {
		wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();

		try {
			Alert alert = wait.until(ExpectedConditions.alertIsPresent());
			String text = alert.getText().trim();
			alert.accept();
			return text;
		} catch (TimeoutException | NoAlertPresentException e) {
			return null;
		}
	}

	public boolean addProductToCartByName(String productName) {
		try {
			navigateToHomePage();
			openProductByName(productName);

			if (!isAddToCartButtonDisplayed()) {
				return false;
			}

			String alertText = clickAddToCartAndGetAlertText();
			return alertText != null && !alertText.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	// ---------------- CART PAGE ----------------

	public void clickCartLink() {
		if (isOnCartPage()) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
			return;
		}

		WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(cartLink));
		clickUsingJsIfNeeded(cart);

		wait.until(ExpectedConditions.urlContains("cart.html"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
	}

	public boolean isOnCartPage() {
		return driver.getCurrentUrl().contains("cart.html");
	}

	public boolean isCartTableDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public int getCartRowCount() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
			waitForCartRowsToLoad();
			return driver.findElements(cartRows).size();
		} catch (Exception e) {
			return 0;
		}
	}

	public List<String> getCartProductTitles() {
		List<String> titles = new ArrayList<>();

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
			waitForCartRowsToLoad();

			List<WebElement> rows = driver.findElements(cartRows);

			for (WebElement row : rows) {
				List<WebElement> cells = row.findElements(By.tagName("td"));
				if (cells.size() >= 2) {
					String title = cells.get(1).getText().trim();
					if (!title.isEmpty()) {
						titles.add(title);
					}
				}
			}
		} catch (Exception e) {
			// ignore
		}

		return titles;
	}

	public String getFirstCartProductTitle() {
		List<String> titles = getCartProductTitles();
		if (titles.isEmpty()) {
			return "";
		}
		return titles.get(0);
	}

	public boolean isProductPresentInCart(String expectedProduct) {
		List<String> titles = getCartProductTitles();

		for (String title : titles) {
			if (title.equalsIgnoreCase(expectedProduct)) {
				return true;
			}
		}
		return false;
	}

	public boolean waitForProductInCart(String expectedProduct) {
		try {
			return wait.until(driver -> isProductPresentInCart(expectedProduct));
		} catch (Exception e) {
			return false;
		}
	}

	public int getProductCountInCartByTitle(String expectedProduct) {
		int count = 0;
		List<String> titles = getCartProductTitles();

		for (String title : titles) {
			if (title.equalsIgnoreCase(expectedProduct)) {
				count++;
			}
		}
		return count;
	}

	public String getCartTotalText() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(totalLabel));
			return driver.findElement(totalLabel).getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isPlaceOrderButtonDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderButton)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickPlaceOrder() {
		wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderModal));
	}

	public boolean isPlaceOrderModalDisplayed() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(placeOrderModal)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deleteFirstCartItem() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
			waitForCartRowsToLoad();

			int beforeCount = driver.findElements(cartRows).size();
			if (beforeCount == 0) {
				return false;
			}

			WebElement firstRow = driver.findElements(cartRows).get(0);
			WebElement deleteLink = firstRow.findElement(By.linkText("Delete"));
			clickUsingJsIfNeeded(deleteLink);

			return wait.until(driver -> driver.findElements(cartRows).size() < beforeCount);
		} catch (Exception e) {
			return false;
		}
	}

	public boolean deleteAllCartItems() {
		try {
			clickCartLink();

			int guard = 0;
			while (getCartRowCount() > 0 && guard < 20) {
				boolean deleted = deleteFirstCartItem();
				if (!deleted) {
					return false;
				}
				guard++;
			}

			return getCartRowCount() == 0;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isCartEmpty() {
		return getCartRowCount() == 0;
	}

	// ---------------- HELPERS ----------------

	void waitForCartRowsToLoad() {
		try {
			wait.until(driver -> {
				List<WebElement> rows = driver.findElements(cartRows);

				if (rows.isEmpty()) {
					String total = getCartTotalText();
					return total.isEmpty() || total.equals("0");
				}

				for (WebElement row : rows) {
					List<WebElement> cells = row.findElements(By.tagName("td"));
					if (cells.size() < 3) {
						return false;
					}

					String title = cells.get(1).getText().trim();
					String price = cells.get(2).getText().trim();

					if (title.isEmpty() || price.isEmpty()) {
						return false;
					}
				}
				return true;
			});
		} catch (Exception e) {
			// ignore
		}
	}

	void clickUsingJsIfNeeded(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element)).click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}
	}
}