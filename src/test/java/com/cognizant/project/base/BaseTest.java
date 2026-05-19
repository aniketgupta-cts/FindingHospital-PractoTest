package com.cognizant.project.base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public class BaseTest {
    public static WebDriver driver;
    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @Before
    public void setup() {
        if (driver != null) return;

        String browser = com.cognizant.project.tests.PractoTest.browserName;
        log.info("Initializing Browser: " + browser);

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
//            options.addArguments("--headless=new"); // headless does not work
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");
//            options.addArguments("--headless=new"); // headless does not work
            driver = new EdgeDriver(options);
        } else {
            log.error("Browser not supported: " + browser);
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.practo.com/");
        log.info("Navigated to Practo home page");
    }

    @After
    public void tearDown() {
        // do nothing here - keep browser open between scenarios
    }

    public static void quitDriver() {
        if (driver != null) {
            log.info("Closing browser");
            driver.quit();
            driver = null;
        }
    }
}