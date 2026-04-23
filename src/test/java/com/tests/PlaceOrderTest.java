package com.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.base.BaseTest;
import com.pages.PlaceOrderPage;
import com.utils.LogUtil;

public class PlaceOrderTest extends BaseTest {

	PlaceOrderPage page;

	@BeforeMethod
	public void setupPlaceOrderFlow() {
		page = new PlaceOrderPage(driver);
		boolean ready = page.addFirstProductToCartAndGoToCart();
		if (ready) {
			LogUtil.pass("Precondition passed: product added to cart and cart page opened.");
		} else {
			LogUtil.defect("Precondition failed: could not add product and open cart page.");
		}
		Assert.assertTrue(ready, "Precondition failed: could not add product and open cart page.");
	}

	@Test(description = "Verify Place Order button is visible on Cart page")
	public void verifyPlaceOrderButtonIsVisibleOnCartPage() {
		boolean visible = page.isPlaceOrderButtonDisplayed();

		if (visible) {
			LogUtil.pass("Place Order button is visible on Cart page.");
		} else {
			LogUtil.defect("Place Order button is not visible on Cart page.");
		}

		Assert.assertTrue(visible, "Place Order button should be visible on Cart page.");
	}

	@Test(description = "Verify clicking Place Order opens the order modal")
	public void verifyClickingPlaceOrderOpensTheOrderModal() {
		page.clickPlaceOrder();
		boolean displayed = page.isPlaceOrderModalDisplayed();

		if (displayed) {
			LogUtil.pass("Place Order modal opened successfully.");
		} else {
			LogUtil.defect("Place Order modal did not open.");
		}

		Assert.assertTrue(displayed, "Place Order modal should open.");
	}

	@Test(description = "Verify modal title is Place order")
	public void verifyModalTitleIsPlaceOrder() {
		page.clickPlaceOrder();
		String actualTitle = page.getPlaceOrderModalTitle();
		boolean correct = "Place order".equals(actualTitle);

		if (correct) {
			LogUtil.pass("Place Order modal title is correct.");
		} else {
			LogUtil.defect("Place Order modal title mismatch. Actual: " + actualTitle);
		}

		Assert.assertEquals(actualTitle, "Place order", "Place Order modal title is incorrect.");
	}

	@Test(description = "Verify all input fields are displayed in Place Order modal")
	public void verifyAllInputFieldsAreDisplayedInPlaceOrderModal() {
		page.clickPlaceOrder();

		boolean allVisible = page.isNameFieldDisplayed() && page.isCountryFieldDisplayed()
				&& page.isCityFieldDisplayed() && page.isCardFieldDisplayed() && page.isMonthFieldDisplayed()
				&& page.isYearFieldDisplayed();

		if (allVisible) {
			LogUtil.pass("All Place Order input fields are displayed.");
		} else {
			LogUtil.defect("One or more Place Order input fields are not displayed.");
		}

		Assert.assertTrue(allVisible, "All Place Order fields should be displayed.");
	}

	@Test(description = "Verify Total label is displayed in Place Order modal")
	public void verifyTotalLabelIsDisplayedInPlaceOrderModal() {
		page.clickPlaceOrder();
		String total = page.getTotalText();
		boolean displayed = total != null && !total.isEmpty();

		if (displayed) {
			LogUtil.pass("Total label/value is displayed in Place Order modal. Total: " + total);
		} else {
			LogUtil.defect("Total label/value is not displayed in Place Order modal.");
		}

		Assert.assertTrue(displayed, "Total should be displayed in Place Order modal.");
	}

	@Test(description = "Verify Close and Purchase buttons are visible")
	public void verifyCloseAndPurchaseButtonsAreVisible() {
		page.clickPlaceOrder();
		boolean bothVisible = page.isCloseButtonDisplayed() && page.isPurchaseButtonDisplayed();

		if (bothVisible) {
			LogUtil.pass("Close and Purchase buttons are visible.");
		} else {
			LogUtil.defect("Close or Purchase button is not visible.");
		}

		Assert.assertTrue(bothVisible, "Close and Purchase buttons should be visible.");
	}

	@Test(description = "Verify user can type into all Place Order fields")
	public void verifyUserCanTypeIntoAllPlaceOrderFields() {
		page.clickPlaceOrder();
		page.fillPlaceOrderForm("Priya", "India", "Bangalore", "123456789012", "04", "2028");

		boolean typed = "Priya".equals(page.getNameValue()) && "India".equals(page.getCountryValue())
				&& "Bangalore".equals(page.getCityValue()) && "123456789012".equals(page.getCardValue())
				&& "04".equals(page.getMonthValue()) && "2028".equals(page.getYearValue());

		if (typed) {
			LogUtil.pass("User can type into all Place Order fields.");
		} else {
			LogUtil.defect("User could not type correctly into one or more Place Order fields.");
		}

		Assert.assertTrue(typed, "User input should be accepted in all Place Order fields.");
	}

	@Test(description = "Verify modal closes using Close button")
	public void verifyModalClosesUsingCloseButton() {
		page.clickPlaceOrder();
		page.clickCloseButton();
		boolean closed = !page.isPlaceOrderModalDisplayed();

		if (closed) {
			LogUtil.pass("Place Order modal closes using Close button.");
		} else {
			LogUtil.defect("Place Order modal did not close using Close button.");
		}

		Assert.assertTrue(closed, "Place Order modal should close using Close button.");
	}

	@Test(description = "Verify modal closes using X icon")
	public void verifyModalClosesUsingXIcon() {
		page.clickPlaceOrder();
		page.clickCloseXButton();
		boolean closed = !page.isPlaceOrderModalDisplayed();

		if (closed) {
			LogUtil.pass("Place Order modal closes using X icon.");
		} else {
			LogUtil.defect("Place Order modal did not close using X icon.");
		}

		Assert.assertTrue(closed, "Place Order modal should close using X icon.");
	}

	@Test(description = "Verify purchase succeeds when all valid details are entered")
	public void verifyPurchaseSucceedsWhenAllValidDetailsAreEntered() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "India", "Bangalore", "123456789012", "04", "2028");

		if (purchased) {
			LogUtil.pass("Purchase succeeds with valid details.");
		} else {
			LogUtil.defect("Purchase did not succeed with valid details.");
		}

		Assert.assertTrue(purchased, "Purchase should succeed with valid details.");
	}

	@Test(description = "Verify confirmation popup shows success message after valid purchase")
	public void verifyConfirmationPopupShowsSuccessMessageAfterValidPurchase() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "India", "Bangalore", "123456789012", "04", "2028");
		Assert.assertTrue(purchased, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean successMessage = text != null && !text.isEmpty();

		if (successMessage) {
			LogUtil.pass("Confirmation popup shows success details.");
		} else {
			LogUtil.defect("Confirmation popup success message/details are missing.");
		}

		Assert.assertTrue(successMessage, "Confirmation popup should show success message.");
	}

	@Test(description = "Verify confirmation popup contains purchase details")
	public void verifyConfirmationPopupContainsPurchaseDetails() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "India", "Bangalore", "123456789012", "04", "2028");
		Assert.assertTrue(purchased, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean containsDetails = text.contains("Id:") && text.contains("Amount:") && text.contains("Card Number:")
				&& text.contains("Name:") && text.contains("Date:");

		if (containsDetails) {
			LogUtil.pass("Confirmation popup contains purchase details.");
		} else {
			LogUtil.defect("Confirmation popup does not contain full purchase details. Actual: " + text);
		}

		Assert.assertTrue(containsDetails, "Confirmation popup should contain purchase details.");
	}

	@Test(description = "Verify OK button in confirmation popup closes the popup")
	public void verifyOkButtonInConfirmationPopupClosesThePopup() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "India", "Bangalore", "123456789012", "04", "2028");
		Assert.assertTrue(purchased, "Purchase should succeed first.");

		boolean okVisible = page.isConfirmationOkButtonDisplayed();

		if (okVisible) {
			LogUtil.pass("OK button is visible in confirmation popup.");
		} else {
			LogUtil.defect("OK button is not visible in confirmation popup.");
		}

		Assert.assertTrue(okVisible, "OK button should be visible in confirmation popup.");

		page.clickConfirmationOk();
		boolean closed = !page.isConfirmationPopupDisplayed();

		if (closed) {
			LogUtil.pass("OK button closes confirmation popup.");
		} else {
			LogUtil.defect("OK button did not close confirmation popup.");
		}

		Assert.assertTrue(closed, "Confirmation popup should close after clicking OK.");
	}

	@Test(description = "Verify cart is cleared after successful purchase")
	public void verifyCartIsClearedAfterSuccessfulPurchase() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "India", "Bangalore", "123456789012", "04", "2028");
		Assert.assertTrue(purchased, "Purchase should succeed first.");

		boolean cartCleared = page.isCartClearedAfterPurchase();

		if (cartCleared) {
			LogUtil.pass("Cart is cleared after successful purchase.");
		} else {
			LogUtil.defect("Cart is not cleared after successful purchase.");
		}

		Assert.assertTrue(cartCleared, "Cart should be cleared after successful purchase.");
	}

	@Test(description = "Verify purchase with all fields blank")
	public void verifyPurchaseWithAllFieldsBlank() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("", "", "", "", "", "");

		if (purchased) {
			LogUtil.defect("Purchase is allowed with all fields blank.");
		} else {
			LogUtil.pass("Purchase is blocked with all fields blank.");
		}

		Assert.assertFalse(purchased, "Purchase should not be allowed with all fields blank.");
	}

	@Test(description = "Verify purchase with only Name filled")
	public void verifyPurchaseWithOnlyNameFilled() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "", "", "", "", "");

		if (purchased) {
			LogUtil.defect("Purchase is allowed with only Name filled.");
		} else {
			LogUtil.pass("Purchase is blocked with only Name filled.");
		}

		Assert.assertFalse(purchased, "Purchase should not be allowed with only Name filled.");
	}

	@Test(description = "Verify purchase with Name and Card only")
	public void verifyPurchaseWithNameAndCardOnly() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "", "", "123456789012", "", "");

		if (purchased) {
			LogUtil.defect("Purchase is allowed with Name and Card only.");
		} else {
			LogUtil.pass("Purchase is blocked with Name and Card only.");
		}

		Assert.assertFalse(purchased, "Purchase should not be allowed with Name and Card only.");
	}

	@Test(description = "Verify purchase with invalid credit card format")
	public void verifyPurchaseWithInvalidCreditCardFormat() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "India", "Bangalore", "12AB34@@", "04", "2028");

		if (purchased) {
			LogUtil.defect("Purchase is allowed with invalid credit card format.");
		} else {
			LogUtil.pass("Purchase is blocked with invalid credit card format.");
		}

		Assert.assertFalse(purchased, "Purchase should not be allowed with invalid credit card format.");
	}

	@Test(description = "Verify purchase with alphabetic credit card value")
	public void verifyPurchaseWithAlphabeticCreditCardValue() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "India", "Bangalore", "ABCDEFGHIJ", "04", "2028");

		if (purchased) {
			LogUtil.defect("Purchase is allowed with alphabetic credit card value.");
		} else {
			LogUtil.pass("Purchase is blocked with alphabetic credit card value.");
		}

		Assert.assertFalse(purchased, "Purchase should not be allowed with alphabetic credit card value.");
	}

	@Test(description = "Verify purchase with special characters in Name field")
	public void verifyPurchaseWithSpecialCharactersInNameField() {
		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("@@@###", "India", "Bangalore", "123456789012", "04", "2028");

		if (purchased) {
			LogUtil.defect("Purchase is allowed with special characters in Name field.");
		} else {
			LogUtil.pass("Purchase is blocked with special characters in Name field.");
		}

		Assert.assertFalse(purchased, "Purchase should not be allowed with special characters in Name field.");
	}

	@Test(description = "Verify purchase with long input values in all fields")
	public void verifyPurchaseWithLongInputValuesInAllFields() {
		page.clickPlaceOrder();

		String longText = page.generateLongText(300);
		boolean purchased = page.purchaseWithData(longText, longText, longText, longText, longText, longText);

		if (purchased) {
			LogUtil.defect("Purchase is allowed with very long input values.");
		} else {
			LogUtil.pass("Purchase is blocked or safely handled with long input values.");
		}

		Assert.assertFalse(purchased, "Purchase should not be allowed with overly long input values.");
	}

	@Test(description = "Verify Month field accepts numeric input")
	public void verifyMonthFieldAcceptsNumericInput() {
		page.clickPlaceOrder();
		page.enterMonth("12");

		String value = page.getMonthValue();
		boolean numeric = "12".equals(value) && page.isNumeric(value);

		if (numeric) {
			LogUtil.pass("Month field accepts numeric input.");
		} else {
			LogUtil.defect("Month field does not accept numeric input correctly.");
		}

		Assert.assertTrue(numeric, "Month field should accept numeric input.");
	}

	@Test(description = "Verify Year field accepts numeric input")
	public void verifyYearFieldAcceptsNumericInput() {
		page.clickPlaceOrder();
		page.enterYear("2028");

		String value = page.getYearValue();
		boolean numeric = "2028".equals(value) && page.isNumeric(value);

		if (numeric) {
			LogUtil.pass("Year field accepts numeric input.");
		} else {
			LogUtil.defect("Year field does not accept numeric input correctly.");
		}

		Assert.assertTrue(numeric, "Year field should accept numeric input.");
	}

	// ================= PURCHASE VALIDATION TESTS =================

	@Test(description = "Verify purchase confirmation popup is displayed")
	public void verifyPurchaseConfirmationPopupDisplayed() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");

		if (displayed) {
			LogUtil.pass("Purchase confirmation popup is displayed.");
		} else {
			LogUtil.defect("Purchase confirmation popup is not displayed.");
		}

		Assert.assertTrue(displayed);
	}

	@Test(description = "Verify purchase success title")
	public void verifyPurchaseSuccessTitle() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String title = driver.findElement(By.cssSelector(".sweet-alert h2")).getText();
		boolean correct = "Thank you for your purchase!".equals(title);

		if (correct) {
			LogUtil.pass("Purchase success title is correct.");
		} else {
			LogUtil.defect("Purchase success title mismatch. Actual: " + title);
		}

		Assert.assertEquals(title, "Thank you for your purchase!");
	}

	@Test(description = "Verify purchase details present")
	public void verifyPurchaseDetailsPresent() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean valid = text.contains("Id:") && text.contains("Amount:");

		if (valid) {
			LogUtil.pass("Purchase details are present in confirmation popup.");
		} else {
			LogUtil.defect("Purchase details are missing in confirmation popup. Actual: " + text);
		}

		Assert.assertTrue(valid);
	}

	@Test(description = "Verify purchase amount matches cart")
	public void verifyPurchaseAmountCorrect() {
		String cartTotal = page.getTotalText();

		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String popup = page.getConfirmationText();
		boolean matched = popup.contains(cartTotal);

		if (matched) {
			LogUtil.pass("Purchase amount matches cart total.");
		} else {
			LogUtil.defect("Purchase amount does not match cart total. Cart: " + cartTotal + ", Popup: " + popup);
		}

		Assert.assertTrue(matched);
	}

	@Test(description = "Verify OK button visible")
	public void verifyOkButtonVisible() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		boolean visible = page.isConfirmationOkButtonDisplayed();

		if (visible) {
			LogUtil.pass("OK button is visible.");
		} else {
			LogUtil.defect("OK button is not visible.");
		}

		Assert.assertTrue(visible);
	}

	@Test(description = "Verify purchase details not empty")
	public void verifyDetailsNotEmpty() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean notEmpty = !text.isEmpty();

		if (notEmpty) {
			LogUtil.pass("Purchase details are not empty.");
		} else {
			LogUtil.defect("Purchase details are empty.");
		}

		Assert.assertFalse(text.isEmpty());
	}

	@Test(description = "Verify card number displayed")
	public void verifyCardDisplayed() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean present = text.contains("Card Number") || text.contains("Card");

		if (present) {
			LogUtil.pass("Card number label/details are displayed.");
		} else {
			LogUtil.defect("Card number label/details are not displayed. Actual: " + text);
		}

		Assert.assertTrue(present);
	}

	@Test(description = "Verify name displayed correctly")
	public void verifyNameDisplayed() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean present = text.contains("Priya");

		if (present) {
			LogUtil.pass("User name is displayed correctly.");
		} else {
			LogUtil.defect("User name is not displayed correctly. Actual: " + text);
		}

		Assert.assertTrue(present);
	}

	@Test(description = "Verify date displayed")
	public void verifyDateDisplayed() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean present = text.contains("Date");

		if (present) {
			LogUtil.pass("Date is displayed in confirmation popup.");
		} else {
			LogUtil.defect("Date is not displayed in confirmation popup. Actual: " + text);
		}

		Assert.assertTrue(present);
	}

	@Test(description = "Verify unique order ID generated")
	public void verifyUniqueOrderId() {
		page.clickPlaceOrder();
		boolean firstDisplayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(firstDisplayed, "First purchase should succeed.");

		String first = page.getConfirmationText();
		page.clickConfirmationOk();

		boolean readyAgain = page.addFirstProductToCartAndGoToCart();
		Assert.assertTrue(readyAgain, "Precondition failed for second purchase.");

		page.clickPlaceOrder();
		boolean secondDisplayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(secondDisplayed, "Second purchase should succeed.");

		String second = page.getConfirmationText();
		boolean unique = !first.equals(second);

		if (unique) {
			LogUtil.pass("Unique order ID/content generated for separate purchases.");
		} else {
			LogUtil.defect("Order confirmation content was duplicated across purchases.");
		}

		Assert.assertNotEquals(first, second);
	}

	@Test(description = "Verify purchase without cart items")
	public void verifyPurchaseWithoutCartItems() {
		boolean deleted = page.deleteAllCartItems();
		Assert.assertTrue(deleted || page.isCartEmpty(), "Cart could not be cleared.");

		page.clickPlaceOrder();
		boolean purchased = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");

		if (purchased) {
			LogUtil.defect("Purchase without cart items is allowed.");
		} else {
			LogUtil.pass("Purchase without cart items is blocked.");
		}

		Assert.assertFalse(purchased);
	}

	@Test(description = "Verify popup only appears after success")
	public void verifyPopupOnlyAfterSuccess() {
		page.clickPlaceOrder();
		boolean success = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");

		if (success) {
			LogUtil.pass("Popup appears after successful purchase.");
		} else {
			LogUtil.defect("Popup did not appear after successful purchase.");
		}

		Assert.assertTrue(success);
	}

	@Test(description = "Verify popup contains Amount")
	public void verifyPopupContainsAmount() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean present = text.contains("Amount");

		if (present) {
			LogUtil.pass("Popup contains Amount.");
		} else {
			LogUtil.defect("Popup does not contain Amount. Actual: " + text);
		}

		Assert.assertTrue(present);
	}

	@Test(description = "Verify popup contains Id")
	public void verifyPopupContainsId() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean present = text.contains("Id");

		if (present) {
			LogUtil.pass("Popup contains Id.");
		} else {
			LogUtil.defect("Popup does not contain Id. Actual: " + text);
		}

		Assert.assertTrue(present);
	}

	@Test(description = "Verify popup contains Date")
	public void verifyPopupContainsDate() {
		page.clickPlaceOrder();
		boolean displayed = page.purchaseWithData("Priya", "India", "BLR", "123456", "04", "2028");
		Assert.assertTrue(displayed, "Purchase should succeed first.");

		String text = page.getConfirmationText();
		boolean present = text.contains("Date");

		if (present) {
			LogUtil.pass("Popup contains Date.");
		} else {
			LogUtil.defect("Popup does not contain Date. Actual: " + text);
		}

		Assert.assertTrue(present);
	}
}