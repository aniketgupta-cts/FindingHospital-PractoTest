package com.cognizant.project.tests;

import com.cognizant.project.base.BaseTest;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.cognizant.project.steps", "com.cognizant.project.base"},
        plugin = {"pretty"}
)
public class PractoTest extends AbstractTestNGCucumberTests {

    public static String browserName = "chrome";

    @BeforeClass
    @Parameters("browser")
    public void setBrowser(@Optional("chrome") String browser) {
        browserName = browser;
    }

    @AfterClass
    public void closeBrowser() {
        BaseTest.quitDriver();
    }
}