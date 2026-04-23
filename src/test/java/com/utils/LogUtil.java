package com.utils;

import org.testng.Reporter;

public class LogUtil {

	public static void pass(String msg) {
		Reporter.log("PASS: " + msg, true);
	}

	public static void defect(String msg) {
		Reporter.log("DEFECT: " + msg, true);
	}

	public static void info(String msg) {
		Reporter.log("INFO: " + msg, true);
	}
}