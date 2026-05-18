package com.cognizant.project.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.List;

public class BaseTest {

    protected WebDriver driver;

    // ThreadLocal for parallel execution safety
    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

    // Shared data stores
    private static List<String> citiesList;
    private static List<String> hospitalList;

    @BeforeClass
    @Parameters("browser")
    public void setup(@Optional("Chrome") String browser) {
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

        // Store in ThreadLocal so step defs can access it
        threadDriver.set(driver);
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            threadDriver.remove();
        }
    }

    // Static getter for step definitions to access driver
    public static WebDriver getDriver() {
        return threadDriver.get();
    }

    // Cities list helpers
    public static void setCitiesList(List<String> list) { citiesList = list; }
    public static List<String> getCitiesList() { return citiesList; }

    // Hospital list helpers
    public static void setHospitalList(List<String> list) { hospitalList = list; }
    public static List<String> getHospitalList() { return hospitalList; }
}