package com.tests;

import com.base.BaseTest;
import com.pages.SignupPage;
import com.utils.ExcelUtil;
import com.utils.LogUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SignupTest extends BaseTest {

    @DataProvider(name = "validSignupData")
    public Object[][] validSignupData() {
        return ExcelUtil.getSheetData("signup");
    }

    @DataProvider(name = "existingUserSignupData")
    public Object[][] existingUserSignupData() {
        return ExcelUtil.getSheetData("existingSignup");
    }

    @DataProvider(name = "weakSignupData")
    public Object[][] weakSignupData() {
        return ExcelUtil.getSheetData("weakSignupData");
    }

    @Test(description = "Verify Sign up link is visible in navbar.")
    public void verifySignUpLinkIsVisibleInNavbar() {
        SignupPage signupPage = new SignupPage(driver, wait);

        boolean isDisplayed = signupPage.isSignUpLinkDisplayed();

        if (isDisplayed) {
            LogUtil.pass("Sign up link is visible in navbar.");
        } else {
            LogUtil.defect("Sign up link is not visible in navbar.");
        }

        Assert.assertTrue(isDisplayed, "Sign up link is not visible in navbar.");
    }

    @Test(description = "Verify clicking Sign up opens signup modal.")
    public void verifyClickingSignUpOpensSignupModal() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        boolean isModalDisplayed = signupPage.isSignUpModalDisplayed();

        if (isModalDisplayed) {
            LogUtil.pass("Clicking Sign up opens signup modal.");
        } else {
            LogUtil.defect("Clicking Sign up did not open signup modal.");
        }

        Assert.assertTrue(isModalDisplayed, "Signup modal did not open.");
    }

    @Test(description = "Verify signup modal title.")
    public void verifySignupModalTitle() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        String actualTitle = signupPage.getSignUpModalTitle();

        if ("Sign up".equals(actualTitle)) {
            LogUtil.pass("Signup modal title is correct: " + actualTitle);
        } else {
            LogUtil.defect("Signup modal title mismatch. Expected: 'Sign up' but found: '" + actualTitle + "'");
        }

        Assert.assertEquals(actualTitle, "Sign up", "Signup modal title mismatch.");
    }

    @Test(description = "Verify username and password fields are present.")
    public void verifyUsernameAndPasswordFieldsArePresent() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();

        boolean usernameDisplayed = signupPage.isUsernameInputDisplayed();
        boolean passwordDisplayed = signupPage.isPasswordInputDisplayed();

        if (usernameDisplayed) {
            LogUtil.pass("Username field is present in signup modal.");
        } else {
            LogUtil.defect("Username field is missing in signup modal.");
        }

        if (passwordDisplayed) {
            LogUtil.pass("Password field is present in signup modal.");
        } else {
            LogUtil.defect("Password field is missing in signup modal.");
        }

        Assert.assertTrue(usernameDisplayed, "Username field is missing in signup modal.");
        Assert.assertTrue(passwordDisplayed, "Password field is missing in signup modal.");
    }

    @Test(description = "Verify Close button, Close icon, and Sign up button are visible.")
    public void verifyButtonsAreVisibleInSignupModal() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();

        boolean closeButtonDisplayed = signupPage.isCloseButtonDisplayed();
        boolean closeIconDisplayed = signupPage.isCloseIconDisplayed();
        boolean signUpButtonDisplayed = signupPage.isSignUpButtonDisplayed();

        if (closeButtonDisplayed) {
            LogUtil.pass("Close button is visible in signup modal.");
        } else {
            LogUtil.defect("Close button is missing in signup modal.");
        }

        if (closeIconDisplayed) {
            LogUtil.pass("Close icon is visible in signup modal.");
        } else {
            LogUtil.defect("Close icon is missing in signup modal.");
        }

        if (signUpButtonDisplayed) {
            LogUtil.pass("Sign up button is visible in signup modal.");
        } else {
            LogUtil.defect("Sign up button is missing in signup modal.");
        }

        Assert.assertTrue(closeButtonDisplayed, "Close button is missing.");
        Assert.assertTrue(closeIconDisplayed, "Close icon is missing.");
        Assert.assertTrue(signUpButtonDisplayed, "Sign up button is missing.");
    }

    @Test(description = "Verify username field label.")
    public void verifyUsernameFieldLabel() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        String actualLabel = signupPage.getUsernameLabelText();

        if ("Username:".equals(actualLabel)) {
            LogUtil.pass("Username field label is correct: " + actualLabel);
        } else {
            LogUtil.defect("Username field label mismatch. Expected: 'Username:' but found: '" + actualLabel + "'");
        }

        Assert.assertEquals(actualLabel, "Username:", "Username label mismatch.");
    }

    @Test(description = "Verify password field label.")
    public void verifyPasswordFieldLabel() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        String actualLabel = signupPage.getPasswordLabelText();

        if ("Password:".equals(actualLabel)) {
            LogUtil.pass("Password field label is correct: " + actualLabel);
        } else {
            LogUtil.defect("Password field label mismatch. Expected: 'Password:' but found: '" + actualLabel + "'");
        }

        Assert.assertEquals(actualLabel, "Password:", "Password label mismatch.");
    }

    @Test(description = "Verify submission with empty username.")
    public void verifySubmissionWithEmptyUsername() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        signupPage.enterPassword("priyankapriya");
        signupPage.clickSignUpButton();

        String actualAlert = signupPage.acceptAlertAndGetText();
        String expectedAlert = "Please fill out Username and Password.";

        if (expectedAlert.equals(actualAlert)) {
            LogUtil.pass("Correct validation alert displayed when username is empty: " + actualAlert);
        } else {
            LogUtil.defect("Incorrect validation alert when username is empty. Expected: '" +
                    expectedAlert + "' but found: '" + actualAlert + "'");
        }

        Assert.assertEquals(actualAlert, expectedAlert, "Incorrect alert when username is empty.");
    }

    @Test(description = "Verify submission with empty password.")
    public void verifySubmissionWithEmptyPassword() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        signupPage.enterUsername("priya@123");
        signupPage.clickSignUpButton();

        String actualAlert = signupPage.acceptAlertAndGetText();
        String expectedAlert = "Please fill out Username and Password.";

        if (expectedAlert.equals(actualAlert)) {
            LogUtil.pass("Correct validation alert displayed when password is empty: " + actualAlert);
        } else {
            LogUtil.defect("Incorrect validation alert when password is empty. Expected: '" +
                    expectedAlert + "' but found: '" + actualAlert + "'");
        }

        Assert.assertEquals(actualAlert, expectedAlert, "Incorrect alert when password is empty.");
    }

    @Test(description = "Verify signup form contains email field.")
    public void verifySignupFormContainsEmailField() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        boolean emailFieldPresent = signupPage.hasEmailField();

        if (emailFieldPresent) {
            LogUtil.pass("Signup form contains email field.");
        } else {
            LogUtil.defect("Signup form does not contain email field.");
        }

        Assert.assertTrue(emailFieldPresent, "Signup form does not contain email field.");
    }

    @Test(description = "Verify weak credentials are rejected.", dataProvider = "weakSignupData")
    public void verifyWeakCredentialsAreRejected(String username, String password) {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        signupPage.enterUsername(username);
        signupPage.enterPassword(password);
        signupPage.clickSignUpButton();

        String actualAlert = signupPage.acceptAlertAndGetText();

        boolean rejected =
                actualAlert.toLowerCase().contains("weak") ||
                actualAlert.toLowerCase().contains("invalid") ||
                actualAlert.toLowerCase().contains("not allowed") ||
                actualAlert.toLowerCase().contains("password");

        if (rejected) {
            LogUtil.pass("Weak credentials were rejected. Alert: " + actualAlert);
        } else {
            LogUtil.defect("Weak credentials were accepted or not properly rejected. Username: " +
                    username + ", Password: " + password + ", Alert: " + actualAlert);
        }

        Assert.assertTrue(rejected,
                "Weak credentials were not rejected properly. Alert was: " + actualAlert);
    }

    @Test(description = "Verify data is cleared after closing and reopening signup modal.")
    public void verifyDataIsClearedAfterClosingAndReopeningSignupModal() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        signupPage.enterUsername("priyankapriya");
        signupPage.enterPassword("priya@123");
        signupPage.closeModalUsingCloseButton();

        signupPage.clickSignUpLink();

        String actualUsername = signupPage.getUsernameFieldValue();
        String actualPassword = signupPage.getPasswordFieldValue();

        if (actualUsername.isEmpty()) {
            LogUtil.pass("Username field is cleared after closing and reopening signup modal.");
        } else {
            LogUtil.defect("Username field retained old value after reopening signup modal: " + actualUsername);
        }

        if (actualPassword.isEmpty()) {
            LogUtil.pass("Password field is cleared after closing and reopening signup modal.");
        } else {
            LogUtil.defect("Password field retained old value after reopening signup modal: " + actualPassword);
        }

        Assert.assertTrue(actualUsername.isEmpty(), "Username field retained old value after reopening.");
        Assert.assertTrue(actualPassword.isEmpty(), "Password field retained old value after reopening.");
    }

    @Test(description = "Verify signup with valid new user.", dataProvider = "validSignupData")
    public void verifySignupWithValidNewUser(String username, String password) {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        signupPage.enterUsername(username);
        signupPage.enterPassword(password);
        signupPage.clickSignUpButton();

        String actualAlert = signupPage.acceptAlertAndGetText();

        boolean success =
                actualAlert.equalsIgnoreCase("Sign up successful.") ||
                actualAlert.toLowerCase().contains("successful");

        if (success) {
            LogUtil.pass("Signup succeeded for valid new user: " + username + ". Alert: " + actualAlert);
        } else {
            LogUtil.defect("Signup failed for valid new user: " + username + ". Alert: " + actualAlert);
        }

        Assert.assertTrue(success, "Signup failed for valid new user. Alert: " + actualAlert);
    }

    @Test(description = "Verify that Signup modal does not include a Confirm Password field.")
    public void verifySignupModalDoesNotIncludeConfirmPasswordField() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        boolean confirmPasswordPresent = signupPage.hasConfirmPasswordField();

        if (!confirmPasswordPresent) {
            LogUtil.defect("Signup modal does not include a Confirm Password field.");
        } else {
            LogUtil.pass("Confirm Password field is present in signup modal.");
        }

        Assert.assertTrue(confirmPasswordPresent, "Confirm Password field is missing in signup modal.");
    }

    @Test(description = "Verify that Password field provide a show/hide toggle option.")
    public void verifyPasswordFieldProvidesShowHideToggleOption() {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        boolean togglePresent = signupPage.hasShowHidePasswordToggle();

        if (togglePresent) {
            LogUtil.pass("Password field provides show/hide toggle option.");
        } else {
            LogUtil.defect("Password field does not provide show/hide toggle option.");
        }

        Assert.assertTrue(togglePresent, "Show/hide toggle option is missing in Password field.");
    }

    @Test(description = "Verify signup with existing user.", dataProvider = "existingUserSignupData")
    public void verifySignupWithExistingUser(String username, String password) {
        SignupPage signupPage = new SignupPage(driver, wait);

        signupPage.clickSignUpLink();
        signupPage.enterUsername(username);
        signupPage.enterPassword(password);
        signupPage.clickSignUpButton();

        String actualAlert = signupPage.acceptAlertAndGetText();

        boolean duplicateRejected =
                actualAlert.equalsIgnoreCase("This user already exist.") ||
                actualAlert.toLowerCase().contains("already exist");

        if (duplicateRejected) {
            LogUtil.pass("Existing user signup was correctly rejected for user: " + username +
                    ". Alert: " + actualAlert);
        } else {
            LogUtil.defect("Existing user signup was not rejected correctly for user: " + username +
                    ". Alert: " + actualAlert);
        }

        Assert.assertTrue(duplicateRejected,
                "Existing user signup was not rejected correctly. Alert: " + actualAlert);
    }
}