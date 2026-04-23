package com.tests;

import com.base.BaseTest;
import com.pages.LoginPage;
import com.utils.ExcelUtil;
import com.utils.LogUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

	@DataProvider(name = "validLoginData")
	public Object[][] validLoginData() {
		return ExcelUtil.getSheetData("login");
	}

	@Test(description = "Verify Login link is displayed on the navigation bar.")
	public void verifyLoginLinkIsDisplayedOnNavbar() {
		LoginPage loginPage = new LoginPage(driver, wait);

		boolean isDisplayed = loginPage.isLoginLinkDisplayed();

		if (isDisplayed) {
			LogUtil.pass("Login link is displayed on the navigation bar.");
		} else {
			LogUtil.defect("Login link is not displayed on the navigation bar.");
		}

		Assert.assertTrue(isDisplayed, "Login link is not displayed on the navigation bar.");
	}

	@Test(description = "Verify clicking the Login link opens the Login modal.")
	public void verifyClickingLoginLinkOpensLoginModal() {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		boolean isModalDisplayed = loginPage.isLoginModalDisplayed();

		if (isModalDisplayed) {
			LogUtil.pass("Clicking the Login link opens the Login modal.");
		} else {
			LogUtil.defect("Clicking the Login link did not open the Login modal.");
		}

		Assert.assertTrue(isModalDisplayed, "Login modal did not open.");
	}

	@Test(description = "Verify the Login modal contains Username and Password input fields.")
	public void verifyLoginModalContainsUsernameAndPasswordFields() {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();

		boolean usernameDisplayed = loginPage.isUsernameInputDisplayed();
		boolean passwordDisplayed = loginPage.isPasswordInputDisplayed();

		if (usernameDisplayed) {
			LogUtil.pass("Username input field is displayed in Login modal.");
		} else {
			LogUtil.defect("Username input field is missing in Login modal.");
		}

		if (passwordDisplayed) {
			LogUtil.pass("Password input field is displayed in Login modal.");
		} else {
			LogUtil.defect("Password input field is missing in Login modal.");
		}

		Assert.assertTrue(usernameDisplayed, "Username field is not displayed.");
		Assert.assertTrue(passwordDisplayed, "Password field is not displayed.");
	}

	@Test(description = "Verify the Login modal header text.")
	public void verifyLoginModalHeaderText() {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		String actualHeader = loginPage.getLoginModalHeaderText();

		if ("Log in".equals(actualHeader)) {
			LogUtil.pass("Login modal header text is correct: " + actualHeader);
		} else {
			LogUtil.defect("Login modal header text mismatch. Expected: 'Log in' but found: '" + actualHeader + "'");
		}

		Assert.assertEquals(actualHeader, "Log in", "Incorrect login modal header text.");
	}

	@Test(description = "Verify the Login modal contains Close button, close icon, and Log in button.")
	public void verifyLoginModalContainsButtons() {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();

		boolean closeButtonDisplayed = loginPage.isCloseButtonDisplayed();
		boolean closeIconDisplayed = loginPage.isCloseIconDisplayed();
		boolean loginButtonDisplayed = loginPage.isLoginButtonDisplayed();

		if (closeButtonDisplayed) {
			LogUtil.pass("Close button is displayed in Login modal.");
		} else {
			LogUtil.defect("Close button is missing in Login modal.");
		}

		if (closeIconDisplayed) {
			LogUtil.pass("Close icon is displayed in Login modal.");
		} else {
			LogUtil.defect("Close icon is missing in Login modal.");
		}

		if (loginButtonDisplayed) {
			LogUtil.pass("Log in button is displayed in Login modal.");
		} else {
			LogUtil.defect("Log in button is missing in Login modal.");
		}

		Assert.assertTrue(closeButtonDisplayed, "Close button is missing.");
		Assert.assertTrue(closeIconDisplayed, "Close icon is missing.");
		Assert.assertTrue(loginButtonDisplayed, "Log in button is missing.");
	}

	@Test(description = "Verify Username and Password labels are displayed correctly.")
	public void verifyUsernameAndPasswordLabels() {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();

		String actualUsernameLabel = loginPage.getUsernameLabelText();
		String actualPasswordLabel = loginPage.getPasswordLabelText();

		if ("Username:".equals(actualUsernameLabel)) {
			LogUtil.pass("Username label is displayed correctly: " + actualUsernameLabel);
		} else {
			LogUtil.defect("Username label mismatch. Expected: 'Username:' but found: '" + actualUsernameLabel + "'");
		}

		if ("Password:".equals(actualPasswordLabel)) {
			LogUtil.pass("Password label is displayed correctly: " + actualPasswordLabel);
		} else {
			LogUtil.defect("Password label mismatch. Expected: 'Password:' but found: '" + actualPasswordLabel + "'");
		}

		Assert.assertEquals(actualUsernameLabel, "Username:", "Username label mismatch.");
		Assert.assertEquals(actualPasswordLabel, "Password:", "Password label mismatch.");
	}

	@Test(description = "Verify validation behavior when Username is blank and Password is entered.")
	public void verifyValidationWhenUsernameBlank() {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		loginPage.enterPassword("priya@123");
		loginPage.clickLoginButton();

		String actualAlertText = loginPage.acceptAlertAndGetText();
		String expectedAlertText = "Please fill out Username and Password.";

		if (expectedAlertText.equals(actualAlertText)) {
			LogUtil.pass("Correct validation alert displayed when Username is blank: " + actualAlertText);
		} else {
			LogUtil.defect("Incorrect validation alert when Username is blank. Expected: '" + expectedAlertText
					+ "' but found: '" + actualAlertText + "'");
		}

		Assert.assertEquals(actualAlertText, expectedAlertText,
				"Incorrect alert when Username is blank and Password is entered.");
	}

	@Test(description = "Verify validation behavior when Password is blank and Username is entered.")
	public void verifyValidationWhenPasswordBlank() {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		loginPage.enterUsername("priyankapriya");
		loginPage.clickLoginButton();

		String actualAlertText = loginPage.acceptAlertAndGetText();
		String expectedAlertText = "Please fill out Username and Password.";

		if (expectedAlertText.equals(actualAlertText)) {
			LogUtil.pass("Correct validation alert displayed when Password is blank: " + actualAlertText);
		} else {
			LogUtil.defect("Incorrect validation alert when Password is blank. Expected: '" + expectedAlertText
					+ "' but found: '" + actualAlertText + "'");
		}

		Assert.assertEquals(actualAlertText, expectedAlertText,
				"Incorrect alert when Password is blank and Username is entered.");
	}

	@Test(description = "Verify alert message text is displayed correctly.")
	public void verifyAlertMessageTextDisplayedCorrectly() {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		loginPage.clickLoginButton();

		String actualAlertText = loginPage.acceptAlertAndGetText();
		String expectedAlertText = "Please fill out Username and Password.";

		if (expectedAlertText.equals(actualAlertText)) {
			LogUtil.pass("Alert message text is displayed correctly: " + actualAlertText);
		} else {
			LogUtil.defect("Alert message text mismatch. Expected: '" + expectedAlertText + "' but found: '"
					+ actualAlertText + "'");
		}

		Assert.assertEquals(actualAlertText, expectedAlertText, "Alert message text is incorrect.");
	}

	@Test(description = "Verify entered Username and Password are cleared after closing and reopening the Login modal.")
	public void verifyFieldsClearedAfterCloseAndReopen() {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		loginPage.enterUsername("priyankapriya");
		loginPage.enterPassword("priya@123");
		loginPage.closeModalUsingCloseButton();

		loginPage.clickLoginLink();

		String actualUsername = loginPage.getUsernameFieldValue();
		String actualPassword = loginPage.getPasswordFieldValue();

		if (actualUsername.isEmpty()) {
			LogUtil.pass("Username field is cleared after closing and reopening the Login modal.");
		} else {
			LogUtil.defect("Username field retained old value after reopening Login modal: " + actualUsername);
		}

		if (actualPassword.isEmpty()) {
			LogUtil.pass("Password field is cleared after closing and reopening the Login modal.");
		} else {
			LogUtil.defect("Password field retained old value after reopening Login modal: " + actualPassword);
		}

		Assert.assertTrue(actualUsername.isEmpty(), "Username field retained old value after reopening.");
		Assert.assertTrue(actualPassword.isEmpty(), "Password field retained old value after reopening.");
	}

	@Test(description = "Verify login succeeds for each valid Username and Password combination from test data.", dataProvider = "validLoginData")
	public void verifyLoginSucceedsForEachValidUsernameAndPassword(String username, String password) {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();

		boolean loggedIn = loginPage.isUserLoggedIn();
		String welcomeText = loggedIn ? loginPage.getWelcomeText() : "";

		if (loggedIn) {
			LogUtil.pass("Login succeeded for valid credentials. Username: " + username);
		} else {
			LogUtil.defect("Login failed for valid credentials. Username: " + username);
		}

		if (loggedIn && welcomeText.contains("Welcome")) {
			LogUtil.pass("Welcome text is displayed correctly after login for user: " + username);
		} else {
			LogUtil.defect("Welcome text missing or incorrect after login for user: " + username + ". Actual text: '"
					+ welcomeText + "'");
		}

		Assert.assertTrue(loggedIn, "User login failed for valid credentials. Username: " + username);
		Assert.assertTrue(welcomeText.contains("Welcome"), "Welcome text missing after login for user: " + username);

		loginPage.logoutIfLoggedIn();
	}

	@Test(description = "Verify Forgot Password option is available for credential recovery.")
	public void verifyForgotPasswordOptionAvailable() {
		LoginPage loginPage = new LoginPage(driver, wait);
		loginPage.clickLoginLink();

		boolean found = loginPage.hasForgotPasswordOption();

		if (found) {
			LogUtil.pass("Forgot Password option is available for credential recovery.");
		} else {
			LogUtil.defect("Forgot Password option is not available for credential recovery.");
		}

		Assert.assertTrue(found, "Forgot Password option is missing.");
	}

	@Test(description = "Verify logged-in user can access profile view option.", dataProvider = "validLoginData")
	public void verifyLoggedInUserCanAccessProfileViewOption(String username, String password) {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();

		boolean loggedIn = loginPage.isUserLoggedIn();

		if (!loggedIn) {
			LogUtil.defect("Could not verify profile view option because login failed for user: " + username);
		}

		Assert.assertTrue(loggedIn, "Login failed. Cannot verify profile view option.");

		boolean found = loginPage.hasProfileViewOption();

		if (found) {
			LogUtil.pass("Profile view option is available for logged-in user.");
		} else {
			LogUtil.defect("Profile view option is not available for logged-in user.");
		}

		Assert.assertTrue(found, "Profile view option is missing.");

		loginPage.logoutIfLoggedIn();
	}

	@Test(description = "Verify logged-in user can change password.", dataProvider = "validLoginData")
	public void verifyLoggedInUserCanChangePassword(String username, String password) {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();

		boolean loggedIn = loginPage.isUserLoggedIn();

		if (!loggedIn) {
			LogUtil.defect("Could not verify change password option because login failed for user: " + username);
		}

		Assert.assertTrue(loggedIn, "Login failed. Cannot verify change password option.");

		boolean found = loginPage.hasChangePasswordOption();

		if (found) {
			LogUtil.pass("Change password option is available for logged-in user.");
		} else {
			LogUtil.defect("Change password option is not available for logged-in user.");
		}

		Assert.assertTrue(found, "Change password option is missing.");

		loginPage.logoutIfLoggedIn();
	}

	@Test(description = "Verify logged-in user can view order or purchase history.", dataProvider = "validLoginData")
	public void verifyLoggedInUserCanViewOrderOrPurchaseHistory(String username, String password) {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();

		boolean loggedIn = loginPage.isUserLoggedIn();

		if (!loggedIn) {
			LogUtil.defect("Could not verify order or purchase history because login failed for user: " + username);
		}

		Assert.assertTrue(loggedIn, "Login failed. Cannot verify order or purchase history.");

		boolean found = loginPage.hasOrderHistoryOption();

		if (found) {
			LogUtil.pass("Order or purchase history option is available for logged-in user.");
		} else {
			LogUtil.defect("Order or purchase history option is not available for logged-in user.");
		}

		Assert.assertTrue(found, "Order or purchase history option is missing.");

		loginPage.logoutIfLoggedIn();
	}

	@Test(description = "Verify logged-in user can view saved payment or billing details.", dataProvider = "validLoginData")
	public void verifyLoggedInUserCanViewSavedPaymentOrBillingDetails(String username, String password) {
		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.clickLoginLink();
		loginPage.enterUsername(username);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();

		boolean loggedIn = loginPage.isUserLoggedIn();

		if (!loggedIn) {
			LogUtil.defect(
					"Could not verify saved payment or billing details because login failed for user: " + username);
		}

		Assert.assertTrue(loggedIn, "Login failed. Cannot verify saved payment or billing details.");

		boolean found = loginPage.hasSavedBillingOption();

		if (found) {
			LogUtil.pass("Saved payment or billing details are available for logged-in user.");
		} else {
			LogUtil.defect("Saved payment or billing details are not available for logged-in user.");
		}

		Assert.assertTrue(found, "Saved payment or billing details option is missing.");

		loginPage.logoutIfLoggedIn();
	}

	@Test(description = "Verify Password field provides show/hide password option.")
	public void verifyPasswordFieldProvidesShowHideOption() {
		LoginPage loginPage = new LoginPage(driver, wait);
		loginPage.clickLoginLink();

		boolean found = loginPage.hasShowHidePasswordOption();

		if (found) {
			LogUtil.pass("Show/Hide password option is available in the Password field.");
		} else {
			LogUtil.defect("Show/Hide password option is not available in the Password field.");
		}

		Assert.assertTrue(found, "Show/Hide password option is missing.");
	}
}