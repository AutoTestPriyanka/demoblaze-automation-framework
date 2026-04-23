package com.tests;

import com.base.BaseTest;
import com.pages.ContactPage;
import com.utils.LogUtil;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ContactTest extends BaseTest {

    @DataProvider(name = "partialContactData")
    public Object[][] partialContactData() {
        return new Object[][]{
                {"", "", "", "Blank form"},
                {"priyanka@gmail.com", "", "", "Only email"},
                {"", "Priyanka", "", "Only name"},
                {"", "", "Test message", "Only message"},
                {"priyanka@gmail.com", "Priyanka", "", "Email and name only"},
                {"priyanka@gmail.com", "", "Test message", "Email and message only"},
                {"", "Priyanka", "Test message", "Name and message only"}
        };
    }

    @Test(description = " Verify that the Contact modal opens when the user clicks the Contact link in the navbar")
    public void verifyContactModalOpens() {
        ContactPage cp = new ContactPage(driver);

        cp.openModal();
        boolean modalDisplayed = cp.isModalDisplayed();

        if (modalDisplayed) {
            LogUtil.pass("Contact modal opens successfully.");
        } else {
            LogUtil.defect("Contact modal did not open.");
        }

        Assert.assertTrue(modalDisplayed, "Contact modal should open successfully.");
    }

    @Test(description = "Validate that the Contact modal title is displayed correctly")
    public void validateContactModalTitle() {
        ContactPage cp = new ContactPage(driver);

        cp.openModal();

        String actualTitle = cp.getModalTitleText();
        String expectedTitle = "Contact us";

        if (expectedTitle.equals(actualTitle)) {
            LogUtil.pass("Modal title is correct: " + actualTitle);
        } else {
            LogUtil.defect("Modal title mismatch. Expected: "
                    + expectedTitle + ", Actual: " + actualTitle);
        }

        Assert.assertEquals(actualTitle, expectedTitle,
                "Modal title should be 'Contact us'.");
    }

    @Test(description = "Verify all input fields and action buttons are visible in the Contact modal")
    public void verifyAllFieldsAndButtonsVisible() {
        ContactPage cp = new ContactPage(driver);

        cp.openModal();

        boolean emailVisible = cp.isEmailFieldDisplayed();
        boolean nameVisible = cp.isNameFieldDisplayed();
        boolean messageVisible = cp.isMessageFieldDisplayed();
        boolean sendVisible = cp.isSendButtonDisplayed();
        boolean closeVisible = cp.isCloseButtonDisplayed();
        boolean xVisible = cp.isXButtonDisplayed();

        boolean allVisible = emailVisible && nameVisible && messageVisible
                && sendVisible && closeVisible && xVisible;

        if (allVisible) {
            LogUtil.pass("All fields and action buttons are visible.");
        } else {
            LogUtil.defect(" One or more fields/buttons are not visible.");
        }

        Assert.assertTrue(allVisible,
                "Contact Email, Contact Name, Message, Close, Send message, and X should be visible.");
    }

    @Test(description = "Verify that submitting the Contact form with all fields empty is not allowed")
    public void verifyEmptyFormSubmissionNotAllowed() {
        ContactPage cp = new ContactPage(driver);

        cp.openModal();
        cp.fillContactForm("", "", "");

        String alertText = cp.clickSendAndGetAlertText();

        if (alertText == null) {
            LogUtil.pass("Empty form submission was blocked.");
        } else {
            LogUtil.defect("Empty form submitted. Alert text: " + alertText);
        }

        Assert.assertNull(alertText,
                "System should block empty form submission or show validation instead of success alert.");
    }

    @Test(dataProvider = "partialContactData",
            description = "Validate partial form submission with missing mandatory fields")
    public void validatePartialFormSubmission(String email, String name, String message, String scenario) {
        ContactPage cp = new ContactPage(driver);

        cp.openModal();
        cp.fillContactForm(email, name, message);

        String alertText = cp.clickSendAndGetAlertText();

        if (alertText == null) {
            LogUtil.pass("" + scenario + " submission was blocked.");
        } else {
            LogUtil.defect("" + scenario + " submitted unexpectedly. Alert text: " + alertText);
        }

        Assert.assertNull(alertText,
                scenario + " should not be submitted. System should prevent submission or show validation.");
    }

    @Test(description = " Verify that invalid email format is rejected during Contact form submission")
    public void verifyInvalidEmailFormatRejected() {
        ContactPage cp = new ContactPage(driver);

        cp.openModal();
        cp.fillContactForm("invalid-email", "Priyanka", "Test message");

        String alertText = cp.clickSendAndGetAlertText();

        if (alertText == null) {
            LogUtil.pass("Invalid email format was rejected.");
        } else {
            LogUtil.defect("Invalid email format submitted unexpectedly. Alert text: " + alertText);
        }

        Assert.assertNull(alertText,
                "System should reject invalid email format.");
    }

    @Test(description = "Verify that message exceeding allowed length is not accepted")
    public void verifyOverlengthMessageNotAccepted() {
        ContactPage cp = new ContactPage(driver);

        String longMessage = "A".repeat(1001);

        cp.openModal();
        cp.fillContactForm("priyanka@gmail.com", "Priyanka", longMessage);

        String alertText = cp.clickSendAndGetAlertText();

        if (alertText == null) {
            LogUtil.pass(" Overlength message was blocked.");
        } else {
            LogUtil.defect("Overlength message submitted unexpectedly. Alert text: " + alertText);
        }

        Assert.assertNull(alertText,
                "System should restrict message length or block submission when message exceeds allowed length.");
    }

    @Test(description = "Verify that Contact form submits successfully with valid data")
    public void verifyValidContactFormSubmission() {
        ContactPage cp = new ContactPage(driver);

        cp.openModal();
        cp.fillContactForm("priyanka@gmail.com", "Priyanka", "Hello from automation");

        String alertText = cp.clickSendAndGetAlertText();

        if (alertText != null && alertText.equals("Thanks for the message!!")) {
            LogUtil.pass("Valid Contact form submitted successfully. Alert: " + alertText);
        } else {
            LogUtil.defect("Valid form submission failed or alert text is incorrect. Actual: " + alertText);
        }

        Assert.assertNotNull(alertText, "Success alert should be displayed for valid Contact form submission.");
        Assert.assertEquals(alertText, "Thanks for the message!!",
                "Success alert text is incorrect for valid Contact form submission.");
    }

    @Test(description = "Verify that all fields are cleared after closing and reopening the Contact modal")
    public void verifyFieldsClearedAfterReopen() {
        ContactPage cp = new ContactPage(driver);

        cp.openModal();
        cp.fillContactForm("priyanka@gmail.com", "Priyanka", "Field clear test");
        cp.closeModalWithCloseButton();

        cp.openModal();

        String emailValue = cp.getEmailFieldValue();
        String nameValue = cp.getNameFieldValue();
        String messageValue = cp.getMessageFieldValue();

        boolean fieldsCleared = emailValue.isEmpty() && nameValue.isEmpty() && messageValue.isEmpty();

        if (fieldsCleared) {
            LogUtil.pass("All fields are cleared after reopening the modal.");
        } else {
            LogUtil.defect("Fields are not cleared after reopening modal."
                    + " Email='" + emailValue + "', Name='" + nameValue + "', Message='" + messageValue + "'");
        }

        Assert.assertTrue(fieldsCleared,
                "All fields should be empty after closing and reopening the Contact modal.");
    }

    @Test(description = "Validate alert message displayed for invalid Contact form submission")
    public void validateAlertTextForInvalidSubmission() {
        ContactPage cp = new ContactPage(driver);

        cp.openModal();
        cp.fillContactForm("", "", "");

        String alertText = cp.clickSendAndGetAlertText();

        boolean properValidationShown = alertText != null && !alertText.trim().isEmpty()
                && !alertText.equals("Thanks for the message!!");

        if (properValidationShown) {
            LogUtil.pass("Proper validation alert displayed for invalid submission. Alert: " + alertText);
        } else {
            LogUtil.defect("Proper validation alert not displayed for invalid submission. Actual alert: " + alertText);
        }

        Assert.assertTrue(properValidationShown,
                "Proper validation alert message should be displayed for invalid Contact form submission.");
    }
}