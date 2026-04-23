package com.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AboutUsPage {

	private final WebDriver driver;
	private final WebDriverWait wait;
	private final Actions actions;

	// Navbar and modal
	private final By aboutUsLink = By.linkText("About us");
	private final By modal = By.id("videoModal");
	private final By modalTitle = By.id("videoModalLabel");
	private final By closeButton = By.xpath("//div[@id='videoModal']//button[normalize-space()='Close']");
	private final By closeXButton = By.xpath("//div[@id='videoModal']//button[@class='close']");

	// Video area
	private final By videoPlayerContainer = By.cssSelector("#videoModal .video-js");
	private final By bigPlayButton = By.cssSelector("#videoModal .vjs-big-play-button");
	private final By controlPlayPauseButton = By.cssSelector("#videoModal .vjs-play-control");
	private final By progressControl = By.cssSelector("#videoModal .vjs-progress-control");
	private final By playProgressBar = By.cssSelector("#videoModal .vjs-play-progress");
	private final By muteButton = By.cssSelector("#videoModal .vjs-mute-control");
	private final By volumePanel = By.cssSelector("#videoModal .vjs-volume-panel, #videoModal .vjs-volume-menu-button");
	private final By volumeBar = By.cssSelector("#videoModal .vjs-volume-bar, #videoModal .vjs-volume-level");
	private final By fullscreenButton = By.cssSelector("#videoModal .vjs-fullscreen-control");

	public AboutUsPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		this.actions = new Actions(driver);
	}

	// ---------- Modal ----------

	public void openAboutUsModal() {
		wait.until(ExpectedConditions.elementToBeClickable(aboutUsLink)).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(modal));
	}

	public boolean isModalDisplayed() {
		return isVisible(modal);
	}

	public String getModalTitleText() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle)).getText().trim();
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isCloseButtonVisible() {
		return isVisible(closeButton);
	}

	public boolean isCloseXButtonVisible() {
		return isVisible(closeXButton);
	}

	public void closeWithCloseButton() {
		wait.until(ExpectedConditions.elementToBeClickable(closeButton)).click();
	}

	public void closeWithXButton() {
		wait.until(ExpectedConditions.elementToBeClickable(closeXButton)).click();
	}

	public boolean waitForModalToClose() {
		try {
			return wait.until(ExpectedConditions.invisibilityOfElementLocated(modal));
		} catch (Exception e) {
			return false;
		}
	}

	// ---------- Video basic ----------

	public boolean isVideoPlayerContainerVisible() {
		return isVisible(videoPlayerContainer);
	}

	public boolean isBigPlayButtonVisible() {
		return isVisible(bigPlayButton);
	}

	public void clickBigPlayButton() {
		wait.until(ExpectedConditions.elementToBeClickable(bigPlayButton)).click();
	}

	public void clickControlPlayPauseButton() {
		wait.until(ExpectedConditions.elementToBeClickable(controlPlayPauseButton)).click();
	}

	public boolean isVideoPausedInitially() {
		return getPlayerClass().contains("vjs-paused");
	}

	public boolean isVideoPlaying() {
		return getPlayerClass().contains("vjs-playing");
	}

	public boolean isVideoPaused() {
		return getPlayerClass().contains("vjs-paused");
	}

	private String getPlayerClass() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(videoPlayerContainer))
					.getAttribute("class");
		} catch (Exception e) {
			return "";
		}
	}

	// ---------- Seek ----------

	public String getPlayProgressWidth() {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(playProgressBar)).getCssValue("width");
		} catch (Exception e) {
			return "";
		}
	}

	public void seekVideoForward() {
		WebElement seekBar = wait.until(ExpectedConditions.visibilityOfElementLocated(progressControl));
		int width = seekBar.getSize().getWidth();
		int moveX = (int) (width * 0.80) - (width / 2);

		actions.moveToElement(seekBar, moveX, 0).click().perform();
	}

	public boolean waitForProgressChange(String beforeWidth) {
		try {
			return wait.until(driver -> {
				String afterWidth = getPlayProgressWidth();
				return !afterWidth.isEmpty() && !afterWidth.equals(beforeWidth);
			});
		} catch (Exception e) {
			return false;
		}
	}

	// ---------- Pause / Resume ----------

	public boolean canPauseAndResumeVideo() {
		try {
			clickBigPlayButton();

			if (!isVideoPlaying()) {
				return false;
			}

			clickControlPlayPauseButton();
			if (!isVideoPaused()) {
				return false;
			}

			clickControlPlayPauseButton();
			return isVideoPlaying();
		} catch (Exception e) {
			return false;
		}
	}

	// ---------- Mute / Unmute ----------

	public boolean isMuteButtonVisible() {
		return isVisible(muteButton);
	}

	public void muteVideo() {
		wait.until(ExpectedConditions.elementToBeClickable(muteButton)).click();
	}

	public void unmuteVideo() {
		wait.until(ExpectedConditions.elementToBeClickable(muteButton)).click();
	}

	public boolean isVideoMuted() {
		try {
			WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(muteButton));
			String classes = button.getAttribute("class");
			String title = button.getAttribute("title");
			return (classes != null && classes.contains("vjs-vol-0"))
					|| (title != null && title.toLowerCase().contains("unmute"));
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isVideoUnmuted() {
		return !isVideoMuted();
	}

	public boolean canMuteAndUnmuteVideo() {
		try {
			if (!isMuteButtonVisible()) {
				return false;
			}

			muteVideo();
			if (!isVideoMuted()) {
				return false;
			}

			unmuteVideo();
			return isVideoUnmuted();
		} catch (Exception e) {
			return false;
		}
	}

	// ---------- Volume ----------

	public void hoverOnVolumePanel() {
		WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(volumePanel));
		actions.moveToElement(panel).pause(Duration.ofMillis(500)).perform();
	}

	public boolean isVolumeBarVisibleAfterHover() {
		try {
			hoverOnVolumePanel();
			return wait.until(ExpectedConditions.visibilityOfElementLocated(volumeBar)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getVolumeIndicator() {
		try {
			WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(volumePanel));
			String classes = panel.getAttribute("class");
			String aria = panel.getAttribute("aria-valuenow");
			return (classes == null ? "" : classes) + "|" + (aria == null ? "" : aria);
		} catch (Exception e) {
			return "";
		}
	}

	public void setVolumeLow() {
		hoverOnVolumePanel();
		WebElement bar = wait.until(ExpectedConditions.visibilityOfElementLocated(volumeBar));
		int width = bar.getSize().getWidth();
		actions.moveToElement(bar, -(width / 2) + (int) (width * 0.20), 0).click().perform();
	}

	public void setVolumeHigh() {
		hoverOnVolumePanel();
		WebElement bar = wait.until(ExpectedConditions.visibilityOfElementLocated(volumeBar));
		int width = bar.getSize().getWidth();
		actions.moveToElement(bar, -(width / 2) + (int) (width * 0.80), 0).click().perform();
	}

	public boolean canAdjustVolumeLevel() {
		try {
			if (!isMuteButtonVisible()) {
				return false;
			}

			if (!isVolumeBarVisibleAfterHover()) {
				return false;
			}

			String before = getVolumeIndicator();

			setVolumeLow();
			Thread.sleep(1000);
			String afterLow = getVolumeIndicator();

			setVolumeHigh();
			Thread.sleep(1000);
			String afterHigh = getVolumeIndicator();

			return (!before.isEmpty() && !afterLow.isEmpty() && !before.equals(afterLow))
					|| (!afterLow.isEmpty() && !afterHigh.isEmpty() && !afterLow.equals(afterHigh));

		} catch (Exception e) {
			return false;
		}
	}
	// ---------- Fullscreen ----------

	public boolean isFullscreenButtonVisible() {
		return isVisible(fullscreenButton);
	}

	public boolean canEnterAndExitFullscreen() {
		try {
			if (!isFullscreenButtonVisible()) {
				return false;
			}

			wait.until(ExpectedConditions.elementToBeClickable(fullscreenButton)).click();
			Thread.sleep(2000);

			boolean entered = isFullscreenActive();

			actions.sendKeys(Keys.ESCAPE).perform();
			Thread.sleep(1500);

			boolean exited = !isFullscreenActive();

			return entered && exited;

		} catch (Exception e) {
			return false;
		}
	}

	public boolean isFullscreenActive() {
		try {
			Object result = ((JavascriptExecutor) driver).executeScript("return document.fullscreenElement != null;");
			return result instanceof Boolean && (Boolean) result;
		} catch (Exception e) {
			return false;
		}
	}

	// ---------- Helper ----------

	private boolean isVisible(By locator) {
		try {
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}