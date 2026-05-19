package com.cognizant.project.base;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

public class BaseTest {
    public static WebDriver driver;

    @Before
    public void setup() {
        if (driver != null) return; // keep same browser for all 3 scenarios

        String browser = com.cognizant.project.tests.PractoTest.browserName;
        System.out.println("Initializing Browser: " + browser);

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");
            driver = new EdgeDriver(options);
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.practo.com/");
    }

    @After
    public void tearDown() {
        // do nothing here - keep browser open between scenarios
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null; // reset so next browser (Edge) initializes fresh
        }
    }
}