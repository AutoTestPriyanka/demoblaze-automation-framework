package com.listeners;

import com.aventstack.extentreports.*;
import com.utils.DriverFactory;
import com.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import java.util.List;

public class TestListener implements ITestListener {

	static final ExtentReports extent = ExtentManager.getInstance();
	static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String description = result.getMethod().getDescription();

		ExtentTest test = extent.createTest(methodName);

		if (description != null && !description.isEmpty()) {
			test.info("Description: " + description);
		}

		testThread.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTest test = testThread.get();
		attachReporterLogs(test, result);
		test.pass("Test passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTest test = testThread.get();

		attachReporterLogs(test, result);
		test.fail(result.getThrowable());

		WebDriver driver = DriverFactory.getDriver();
		if (driver != null) {
			String screenshotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
			if (screenshotPath != null) {
				try {
					test.fail("Screenshot on failure",
							MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
				} catch (Exception e) {
					test.warning("Unable to attach screenshot: " + e.getMessage());
				}
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTest test = testThread.get();
		attachReporterLogs(test, result);
		test.skip("Test skipped");
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

	void attachReporterLogs(ExtentTest test, ITestResult result) {
		List<String> logs = Reporter.getOutput(result);
		if (logs == null || logs.isEmpty()) {
			return;
		}

		for (String log : logs) {
			if (log.startsWith("PASS:")) {
				test.pass(log);
			} else if (log.startsWith("FAIL:")) {
				test.fail(log);
			} else if (log.startsWith("DEFECT:")) {
				test.fail("<b style='color:red;'>DEFECT:</b> " + log.replace("DEFECT:", "").trim());
			} else {
				test.info(log);
			}
		}
	}
}