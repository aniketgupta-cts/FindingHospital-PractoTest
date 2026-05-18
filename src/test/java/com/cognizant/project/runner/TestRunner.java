package com.cognizant.project.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features  = "src/test/resources/features",
        glue      = "com.cognizant.project.stepdefs",
        plugin    = {
                "pretty",
                "html:reports/cucumber-html-report.html",
                "json:reports/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // Enables parallel scenario execution
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}