package com.utils;

import org.openqa.selenium.WebDriver;

public class DriverFactory {

    static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void unload() {
        driverThreadLocal.remove();
    }
}