package com.tests;

import com.base.BaseTest;
import com.pages.LogoutPage;
import com.utils.ExcelUtil;
import com.utils.LogUtil;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LogoutTest extends BaseTest {

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() {
        return ExcelUtil.getSheetData("login");
    }

    @Test(description = "Verify Logout Link Not Visible Before Login")
    public void verifyLogoutLinkNotVisibleBeforeLogin() {
        LogoutPage logoutPage = new LogoutPage(driver, wait);

        boolean logoutVisible = logoutPage.isLogoutLinkDisplayed();

        if (!logoutVisible) {
            LogUtil.pass("Logout link is not visible before login.");
        } else {
            LogUtil.defect("Logout link is visible before login.");
        }

        Assert.assertFalse(logoutVisible, "Logout link should not be visible before login.");
    }

    @Test(description = "Verify Logout Link Visibility After Login", dataProvider = "validLoginData")
    public void verifyLogoutLinkVisibilityAfterLogin(String username, String password) {
        LogoutPage logoutPage = new LogoutPage(driver, wait);

        logoutPage.login(username, password);
        boolean logoutVisible = logoutPage.isLogoutLinkDisplayed();

        if (logoutVisible) {
            LogUtil.pass("Logout link is visible after login for user: " + username);
        } else {
            LogUtil.defect("Logout link is not visible after login for user: " + username);
        }

        Assert.assertTrue(logoutVisible, "Logout link is not visible after login.");
    }

    @Test(description = "Verify Successful Logout Functionality", dataProvider = "validLoginData")
    public void verifySuccessfulLogoutFunctionality(String username, String password) {
        LogoutPage logoutPage = new LogoutPage(driver, wait);

        logoutPage.login(username, password);
        logoutPage.clickLogout();
        boolean loggedOut = logoutPage.waitForLogoutState();

        if (loggedOut) {
            LogUtil.pass("Logout functionality works successfully for user: " + username);
        } else {
            LogUtil.defect("Logout functionality failed for user: " + username);
        }

        Assert.assertTrue(loggedOut, "Logout functionality failed.");
    }

    @Test(description = "Verify Session Is Cleared After Logout", dataProvider = "validLoginData")
    public void verifySessionIsClearedAfterLogout(String username, String password) {
        LogoutPage logoutPage = new LogoutPage(driver, wait);

        logoutPage.login(username, password);
        logoutPage.clickLogout();
        logoutPage.waitForLogoutState();

        boolean loggedOut = logoutPage.isUserLoggedOut();

        if (loggedOut) {
            LogUtil.pass("User session is cleared after logout for user: " + username);
        } else {
            LogUtil.defect("User session is not cleared after logout for user: " + username);
        }

        Assert.assertTrue(loggedOut, "Session is not cleared after logout.");
    }

    @Test(description = "Verify Back Navigation After Logout", dataProvider = "validLoginData")
    public void verifyBackNavigationAfterLogout(String username, String password) {
        LogoutPage logoutPage = new LogoutPage(driver, wait);

        logoutPage.login(username, password);
        logoutPage.clickLogout();
        logoutPage.waitForLogoutState();
        logoutPage.navigateBack();

        boolean stillLoggedOut = !logoutPage.isLogoutLinkDisplayed() && !logoutPage.isWelcomeUserDisplayed();

        if (stillLoggedOut) {
            LogUtil.pass("Back navigation after logout does not restore session for user: " + username);
        } else {
            LogUtil.defect("Back navigation after logout restores session for user: " + username);
        }

        Assert.assertTrue(stillLoggedOut, "User session was restored after browser back navigation.");
    }

    @Test(description = "Verify Login Link Visibility After Logout", dataProvider = "validLoginData")
    public void verifyLoginLinkVisibilityAfterLogout(String username, String password) {
        LogoutPage logoutPage = new LogoutPage(driver, wait);

        logoutPage.login(username, password);
        logoutPage.clickLogout();
        logoutPage.waitForLogoutState();

        boolean loginVisible = logoutPage.isLoginLinkDisplayed();

        if (loginVisible) {
            LogUtil.pass("Login link is visible after logout for user: " + username);
        } else {
            LogUtil.defect("Login link is not visible after logout for user: " + username);
        }

        Assert.assertTrue(loginVisible, "Login link is not visible after logout.");
    }

    @Test(description = "Verify Welcome Text Disappears After Logout", dataProvider = "validLoginData")
    public void verifyWelcomeTextDisappearsAfterLogout(String username, String password) {
        LogoutPage logoutPage = new LogoutPage(driver, wait);

        logoutPage.login(username, password);
        String welcomeBeforeLogout = logoutPage.getWelcomeText();

        logoutPage.clickLogout();
        logoutPage.waitForLogoutState();

        boolean welcomeGone = !logoutPage.isWelcomeUserDisplayed();

        if (!welcomeBeforeLogout.isEmpty() && welcomeGone) {
            LogUtil.pass("Welcome text disappears after logout for user: " + username);
        } else {
            LogUtil.defect("Welcome text does not disappear after logout for user: " + username);
        }

        Assert.assertTrue(welcomeGone, "Welcome text is still visible after logout.");
    }

    @Test(description = "Verify Logout Link Becomes Invisible After Logout", dataProvider = "validLoginData")
    public void verifyLogoutLinkBecomesInvisibleAfterLogout(String username, String password) {
        LogoutPage logoutPage = new LogoutPage(driver, wait);

        logoutPage.login(username, password);
        logoutPage.clickLogout();
        logoutPage.waitForLogoutState();

        boolean logoutGone = !logoutPage.isLogoutLinkDisplayed();

        if (logoutGone) {
            LogUtil.pass("Logout link becomes invisible after logout for user: " + username);
        } else {
            LogUtil.defect("Logout link is still visible after logout for user: " + username);
        }

        Assert.assertTrue(logoutGone, "Logout link is still visible after logout.");
    }

    @Test(description = "Verify Logout Function with Multiple Users", dataProvider = "validLoginData")
    public void verifyLogoutFunctionWithMultipleUsers(String username, String password) {
        LogoutPage logoutPage = new LogoutPage(driver, wait);

        logoutPage.login(username, password);
        boolean loggedIn = logoutPage.isUserLoggedIn();

        if (loggedIn) {
            LogUtil.pass("Login successful before logout for user: " + username);
        } else {
            LogUtil.defect("Login failed before logout for user: " + username);
        }

        Assert.assertTrue(loggedIn, "Precondition failed: login unsuccessful for user: " + username);

        logoutPage.clickLogout();
        boolean loggedOut = logoutPage.waitForLogoutState();

        if (loggedOut) {
            LogUtil.pass("Logout successful for user: " + username);
        } else {
            LogUtil.defect("Logout failed for user: " + username);
        }

        Assert.assertTrue(loggedOut, "Logout failed for user: " + username);
    }
}