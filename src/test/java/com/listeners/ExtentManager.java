package com.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {

	static ExtentReports extent;

	public static ExtentReports getInstance() {
		if (extent == null) {
			File reportDir = new File(System.getProperty("user.dir") + "/reports");
			if (!reportDir.exists()) {
				reportDir.mkdirs();
			}

			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport_" + timestamp + ".html";

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
			sparkReporter.config().setDocumentTitle("Demoblaze Automation Report");
			sparkReporter.config().setReportName("Demoblaze Login Report");

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);

			extent.setSystemInfo("Project", "Demoblaze");
			extent.setSystemInfo("Framework", "Selenium + TestNG");
			extent.setSystemInfo("Tester", "Priyanka");
		}
		return extent;
	}
}