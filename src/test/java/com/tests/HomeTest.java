package com.tests;

import com.base.BaseTest;
import com.pages.HomePage;
import com.utils.LogUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {

	@Test(description = "Verify that page title contains STORE")
	public void verifyPageTitleContainsStore() {
		HomePage homePage = new HomePage(driver, wait);

		String title = homePage.getPageTitle();
		boolean result = title.contains("STORE");

		if (result) {
			LogUtil.pass("Page title contains STORE. Actual title: " + title);
		} else {
			LogUtil.defect("Page title does not contain STORE. Actual title: " + title);
		}

		Assert.assertTrue(result, "Page title does not contain STORE.");
	}

	@Test(description = "Verify that all main navigation bar links are visible on page load")
	public void verifyMainNavigationBarLinksVisible() {
		HomePage homePage = new HomePage(driver, wait);

		boolean brandVisible = homePage.isBrandLogoDisplayed();
		boolean homeVisible = homePage.isHomeLinkDisplayed();
		boolean contactVisible = homePage.isContactLinkDisplayed();
		boolean aboutVisible = homePage.isAboutLinkDisplayed();
		boolean cartVisible = homePage.isCartLinkDisplayed();
		boolean loginVisible = homePage.isLoginLinkDisplayed();
		boolean signupVisible = homePage.isSignupLinkDisplayed();

		boolean allVisible = brandVisible && homeVisible && contactVisible && aboutVisible && cartVisible
				&& loginVisible && signupVisible;

		if (allVisible) {
			LogUtil.pass("All main navigation bar links are visible on page load.");
		} else {
			LogUtil.defect("One or more main navigation bar links are not visible on page load.");
		}

		Assert.assertTrue(allVisible, "All main navigation bar links are not visible.");
	}

	@Test(description = "Verify that the brand logo text is displayed as PRODUCT STORE")
	public void verifyBrandLogoText() {
		HomePage homePage = new HomePage(driver, wait);

		String actualText = homePage.getBrandLogoText();
		boolean result = actualText.equals("PRODUCT STORE");

		if (result) {
			LogUtil.pass("Brand logo text is displayed correctly: " + actualText);
		} else {
			LogUtil.defect("Brand logo text mismatch. Actual: " + actualText);
		}

		Assert.assertEquals(actualText, "PRODUCT STORE", "Brand logo text is incorrect.");
	}

	@Test(description = "Verify that the Logout link is not visible before the user logs in")
	public void verifyLogoutLinkNotVisibleBeforeLogin() {
		HomePage homePage = new HomePage(driver, wait);

		boolean logoutVisible = homePage.isLogoutLinkDisplayed();

		if (!logoutVisible) {
			LogUtil.pass("Logout link is not visible before login.");
		} else {
			LogUtil.defect("Logout link is visible before login.");
		}

		Assert.assertFalse(logoutVisible, "Logout link should not be visible before login.");
	}

	@Test(description = "Verify that clicking the Home link keeps the user on the home page and products are displayed")
	public void verifyClickingHomeKeepsUserOnHomePage() {
		HomePage homePage = new HomePage(driver, wait);

		homePage.clickHome();

		boolean onHomePage = homePage.isOnHomePage();
		boolean productsVisible = homePage.areProductCardsVisible();

		if (onHomePage && productsVisible) {
			LogUtil.pass("Clicking Home keeps user on home page and products are displayed.");
		} else {
			LogUtil.defect("Clicking Home did not keep user on home page or products are not displayed.");
		}

		Assert.assertTrue(onHomePage, "User is not on home page after clicking Home.");
		Assert.assertTrue(productsVisible, "Products are not displayed after clicking Home.");
	}

	@Test(description = "Verify that clicking the Cart link navigates the user to the cart page")
	public void verifyCartNavigation() {
		HomePage homePage = new HomePage(driver, wait);

		homePage.clickCart();
		boolean onCartPage = homePage.isOnCartPage();

		if (onCartPage) {
			LogUtil.pass("Clicking Cart navigates user to the cart page.");
		} else {
			LogUtil.defect("Clicking Cart did not navigate user to the cart page.");
		}

		Assert.assertTrue(onCartPage, "User is not navigated to the cart page.");
	}

	@Test(description = "Verify that clicking the Contact link opens the Contact modal")
	public void verifyContactModalOpens() {
		HomePage homePage = new HomePage(driver, wait);

		homePage.clickContact();
		boolean contactModalDisplayed = homePage.isContactModalDisplayed();

		if (contactModalDisplayed) {
			LogUtil.pass("Contact modal opens successfully.");
		} else {
			LogUtil.defect("Contact modal did not open.");
		}

		Assert.assertTrue(contactModalDisplayed, "Contact modal is not displayed.");
	}

	@Test(description = "Verify that clicking the About link opens the About modal")
	public void verifyAboutModalOpens() {
		HomePage homePage = new HomePage(driver, wait);

		homePage.clickAbout();
		boolean aboutModalDisplayed = homePage.isAboutModalDisplayed();

		if (aboutModalDisplayed) {
			LogUtil.pass("About modal opens successfully.");
		} else {
			LogUtil.defect("About modal did not open.");
		}

		Assert.assertTrue(aboutModalDisplayed, "About modal is not displayed.");
	}

	@Test(description = "Verify that the homepage carousel is visible on page load")
	public void verifyCarouselVisibleOnPageLoad() {
		HomePage homePage = new HomePage(driver, wait);

		boolean carouselDisplayed = homePage.isCarouselDisplayed();

		if (carouselDisplayed) {
			LogUtil.pass("Homepage carousel is visible on page load.");
		} else {
			LogUtil.defect("Homepage carousel is not visible on page load.");
		}

		Assert.assertTrue(carouselDisplayed, "Homepage carousel is not visible.");
	}

	@Test(description = "Verify that the carousel contains exactly 3 slides")
	public void verifyCarouselContainsExactlyThreeSlides() {
		HomePage homePage = new HomePage(driver, wait);

		int slideCount = homePage.getCarouselSlideCount();

		if (slideCount == 3) {
			LogUtil.pass("Carousel contains exactly 3 slides.");
		} else {
			LogUtil.defect("Carousel slide count mismatch. Actual count: " + slideCount);
		}

		Assert.assertEquals(slideCount, 3, "Carousel does not contain exactly 3 slides.");
	}

	@Test(description = "Verify that clicking the Next button changes the active carousel slide")
	public void verifyCarouselNextButtonChangesSlide() {
		HomePage homePage = new HomePage(driver, wait);

		boolean changed = homePage.didCarouselMoveToNextSlide();

		if (changed) {
			LogUtil.pass("Carousel Next button changes the active slide.");
		} else {
			LogUtil.defect("Carousel Next button did not change the active slide.");
		}

		Assert.assertTrue(changed, "Carousel Next button did not change the active slide.");
	}

	@Test(description = "Verify that clicking the Previous button changes the active carousel slide back")
	public void verifyCarouselPreviousButtonChangesSlideBack() {
		HomePage homePage = new HomePage(driver, wait);

		homePage.didCarouselMoveToNextSlide();
		boolean changedBack = homePage.didCarouselMoveToPreviousSlide();

		if (changedBack) {
			LogUtil.pass("Carousel Previous button changes the active slide back.");
		} else {
			LogUtil.defect("Carousel Previous button did not change the active slide back.");
		}

		Assert.assertTrue(changedBack, "Carousel Previous button did not change the active slide back.");
	}

	@Test(description = "Verify that all product category links are visible")
	public void verifyAllProductCategoryLinksVisible() {
		HomePage homePage = new HomePage(driver, wait);

		boolean phonesVisible = homePage.isPhonesCategoryDisplayed();
		boolean laptopsVisible = homePage.isLaptopsCategoryDisplayed();
		boolean monitorsVisible = homePage.isMonitorsCategoryDisplayed();

		boolean allVisible = phonesVisible && laptopsVisible && monitorsVisible;

		if (allVisible) {
			LogUtil.pass("All product category links are visible.");
		} else {
			LogUtil.defect("One or more product category links are not visible.");
		}

		Assert.assertTrue(allVisible, "All product category links are not visible.");
	}

	@Test(description = "Verify that clicking the Phones category displays at least one product")
	public void verifyPhonesCategoryDisplaysProducts() {
		HomePage homePage = new HomePage(driver, wait);

		homePage.clickPhonesCategory();
		int count = homePage.getVisibleProductCount();

		if (count > 0) {
			LogUtil.pass("Phones category displays products. Product count: " + count);
		} else {
			LogUtil.defect("Phones category does not display any products.");
		}

		Assert.assertTrue(count > 0, "Phones category does not display products.");
	}

	@Test(description = "Verify that clicking the Laptops category displays at least one product")
	public void verifyLaptopsCategoryDisplaysProducts() {
		HomePage homePage = new HomePage(driver, wait);

		homePage.clickLaptopsCategory();
		int count = homePage.getVisibleProductCount();

		if (count > 0) {
			LogUtil.pass("Laptops category displays products. Product count: " + count);
		} else {
			LogUtil.defect("Laptops category does not display any products.");
		}

		Assert.assertTrue(count > 0, "Laptops category does not display products.");
	}

	@Test(description = "Verify that clicking the Monitors category displays at least one product")
	public void verifyMonitorsCategoryDisplaysProducts() {
		HomePage homePage = new HomePage(driver, wait);

		homePage.clickMonitorsCategory();
		int count = homePage.getVisibleProductCount();

		if (count > 0) {
			LogUtil.pass("Monitors category displays products. Product count: " + count);
		} else {
			LogUtil.defect("Monitors category does not display any products.");
		}

		Assert.assertTrue(count > 0, "Monitors category does not display products.");
	}

	@Test(description = "Verify that each category filter returns at least one product")
	public void verifyEachCategoryFilterReturnsProducts() {
		HomePage homePage = new HomePage(driver, wait);

		homePage.clickPhonesCategory();
		int phonesCount = homePage.getVisibleProductCount();

		homePage.clickLaptopsCategory();
		int laptopsCount = homePage.getVisibleProductCount();

		homePage.clickMonitorsCategory();
		int monitorsCount = homePage.getVisibleProductCount();

		boolean allHaveProducts = phonesCount > 0 && laptopsCount > 0 && monitorsCount > 0;

		if (allHaveProducts) {
			LogUtil.pass("Each category filter returns at least one product.");
		} else {
			LogUtil.defect("One or more category filters returned no products.");
		}

		Assert.assertTrue(allHaveProducts, "One or more category filters returned no products.");
	}

	@Test(description = "Verify that product cards are loaded and visible on the home page initially")
	public void verifyProductCardsLoadedInitially() {
		HomePage homePage = new HomePage(driver, wait);

		boolean productsVisible = homePage.areProductCardsVisible();

		if (productsVisible) {
			LogUtil.pass("Product cards are loaded and visible on the home page initially.");
		} else {
			LogUtil.defect("Product cards are not visible on the home page initially.");
		}

		Assert.assertTrue(productsVisible, "Product cards are not visible on the home page initially.");
	}
}