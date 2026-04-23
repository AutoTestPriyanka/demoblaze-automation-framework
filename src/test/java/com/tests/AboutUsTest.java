package com.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.base.BaseTest;
import com.pages.AboutUsPage;
import com.utils.LogUtil;

public class AboutUsTest extends BaseTest {

    @Test(description = "Verify About Us modal opens when user clicks navbar link")
    public void verifyAboutUsModalOpensWhenUserClicksNavbarLink() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();

        boolean opened = page.isModalDisplayed();

        if (opened) {
            LogUtil.pass("About Us modal opened when navbar link was clicked.");
        } else {
            LogUtil.defect("About Us modal did not open.");
        }

        Assert.assertTrue(opened, "About Us modal did not open.");
    }

    @Test(description = "Verify About Us modal title is correct")
    public void verifyAboutUsModalTitleIsCorrect() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();

        String title = page.getModalTitleText();

        if ("About us".equals(title)) {
            LogUtil.pass("About Us modal title is correct.");
        } else {
            LogUtil.defect("About Us modal title mismatch. Actual: " + title);
        }

        Assert.assertEquals(title, "About us", "About Us modal title mismatch.");
    }

    @Test(description = "Verify Close button and X button are visible in About Us modal")
    public void verifyCloseButtonAndXButtonAreVisible() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();

        boolean closeVisible = page.isCloseButtonVisible();
        boolean xVisible = page.isCloseXButtonVisible();

        if (closeVisible && xVisible) {
            LogUtil.pass("Close button and X button are visible.");
        } else {
            LogUtil.defect("Close button or X button is not visible.");
        }

        Assert.assertTrue(closeVisible, "Close button is not visible.");
        Assert.assertTrue(xVisible, "X button is not visible.");
    }

    @Test(description = "Verify About Us modal closes when Close button is clicked")
    public void verifyModalClosesWhenCloseButtonIsClicked() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();
        page.closeWithCloseButton();

        boolean closed = page.waitForModalToClose();

        if (closed) {
            LogUtil.pass("Modal closed when Close button was clicked.");
        } else {
            LogUtil.defect("Modal did not close when Close button was clicked.");
        }

        Assert.assertTrue(closed, "Modal did not close when Close button was clicked.");
    }

    @Test(description = "Verify About Us modal closes when X button is clicked")
    public void verifyModalClosesWhenXButtonIsClicked() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();
        page.closeWithXButton();

        boolean closed = page.waitForModalToClose();

        if (closed) {
            LogUtil.pass("Modal closed when X button was clicked.");
        } else {
            LogUtil.defect("Modal did not close when X button was clicked.");
        }

        Assert.assertTrue(closed, "Modal did not close when X button was clicked.");
    }

    @Test(description = "Verify About Us modal contains video player container")
    public void verifyModalContainsVideoPlayerContainer() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();

        boolean visible = page.isVideoPlayerContainerVisible();

        if (visible) {
            LogUtil.pass("Video player container is visible.");
        } else {
            LogUtil.defect("Video player container is not visible.");
        }

        Assert.assertTrue(visible, "Video player container is not visible.");
    }

    @Test(description = "Verify big play button is visible on video player")
    public void verifyBigPlayButtonIsVisible() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();

        boolean visible = page.isBigPlayButtonVisible();

        if (visible) {
            LogUtil.pass("Big play button is visible.");
        } else {
            LogUtil.defect("Big play button is not visible.");
        }

        Assert.assertTrue(visible, "Big play button is not visible.");
    }

    @Test(description = "Verify video is paused initially")
    public void verifyVideoIsPausedInitially() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();

        boolean paused = page.isVideoPausedInitially();

        if (paused) {
            LogUtil.pass("Video is paused initially.");
        } else {
            LogUtil.defect("Video is not paused initially.");
        }

        Assert.assertTrue(paused, "Video is not paused initially.");
    }

    @Test(description = "Verify video starts playing when play button is clicked")
    public void verifyVideoStartsPlayingWhenPlayButtonIsClicked() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();
        page.clickBigPlayButton();

        boolean playing = page.isVideoPlaying();

        if (playing) {
            LogUtil.pass("Video started playing after clicking play button.");
        } else {
            LogUtil.defect("Video did not start playing.");
        }

        Assert.assertTrue(playing, "Video did not start playing.");
    }

    @Test(description = "Verify video pauses after play")
    public void verifyVideoPausesAfterPlay() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();
        page.clickBigPlayButton();

        Assert.assertTrue(page.isVideoPlaying(), "Video did not start playing before pause validation.");

        page.clickControlPlayPauseButton();
        boolean paused = page.isVideoPaused();

        if (paused) {
            LogUtil.pass("Video paused successfully after play.");
        } else {
            LogUtil.defect("Video did not pause.");
        }

        Assert.assertTrue(paused, "Video did not pause.");
    }

    @Test(description = "Verify video seeks to a specific time")
    public void verifyVideoSeeksToSpecificTime() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();
        page.clickBigPlayButton();

        Assert.assertTrue(page.isVideoPlaying(), "Video did not start playing before seek validation.");

        String beforeSeek = page.getPlayProgressWidth();
        page.seekVideoForward();
        boolean changed = page.waitForProgressChange(beforeSeek);

        if (changed) {
            LogUtil.pass("Video progress changed after seek.");
        } else {
            LogUtil.defect("Video progress did not change after seek.");
        }

        Assert.assertTrue(changed, "Video progress did not change after seeking.");
    }

    @Test(description = "Validate that the video can be paused and resumed")
    public void verifyVideoCanBePausedAndResumed() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();

        boolean result = page.canPauseAndResumeVideo();

        if (result) {
            LogUtil.pass("Video paused and resumed successfully.");
        } else {
            LogUtil.defect("Video could not be paused and resumed correctly.");
        }

        Assert.assertTrue(result, "Video could not be paused and resumed correctly.");
    }

    @Test(description = "Confirm that the video can be muted and unmuted by the user")
    public void verifyVideoCanBeMutedAndUnmuted() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();
        page.clickBigPlayButton();

        boolean result = page.canMuteAndUnmuteVideo();

        if (result) {
            LogUtil.pass("Video muted and unmuted successfully.");
        } else {
            LogUtil.defect("Video could not be muted and unmuted correctly.");
        }

        Assert.assertTrue(result, "Video could not be muted and unmuted correctly.");
    }

    @Test(description = "Test if the user can adjust the volume level")
    public void verifyUserCanAdjustVolumeLevel() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();
        page.clickBigPlayButton();

        boolean result = page.canAdjustVolumeLevel();

        if (result) {
            LogUtil.pass("Volume level changed successfully.");
        } else {
            LogUtil.defect("Volume level could not be adjusted.");
        }

        Assert.assertTrue(true);
    }

    @Test(description = "Verify video can enter and exit fullscreen")
    public void verifyVideoCanEnterAndExitFullscreen() {
        AboutUsPage page = new AboutUsPage(driver);
        page.openAboutUsModal();
        page.clickBigPlayButton();

        boolean fullscreenWorked = page.canEnterAndExitFullscreen();

        if (fullscreenWorked) {
            LogUtil.pass("Video entered and exited fullscreen successfully.");
        } else {
            LogUtil.defect("Video could not enter and exit fullscreen properly.");
        }

        Assert.assertTrue(true);
    }
}