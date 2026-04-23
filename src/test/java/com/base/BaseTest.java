package com.base;

import com.utils.ConfigReader;
import com.utils.DriverFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.getProperty("browser");
        String url = ConfigReader.getProperty("url");
        String timeout = ConfigReader.getProperty("timeout");

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        } else {
            throw new RuntimeException("Browser not supported: " + browser);
        }

        driver.manage().window().maximize();

        // Avoid mixing implicit + explicit waits
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(timeout)));

        DriverFactory.setDriver(driver);
        driver.get(url);

     //   Reporter.log("INFO: Browser launched and application opened.", true);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
         //   Reporter.log("INFO: Browser closed.", true);
        }
        DriverFactory.unload();
    }
}