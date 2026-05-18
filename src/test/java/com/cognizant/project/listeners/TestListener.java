package com.cognizant.project.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.cognizant.project.base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    // ── Suite start ────────────────────────────────────────────────────────

    @Override
    public void onStart(ITestContext context) {
        String timestamp  = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = "reports/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Practo Hackathon - Test Report");
        spark.config().setReportName("Automation Execution Report");

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Project",     "Practo Hackathon");
        extent.setSystemInfo("Team",        "INTQEA26QE003");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser",     context.getCurrentXmlTest().getParameter("browser"));

        log.info("===== Suite Started : {} =====", context.getName());
    }

    // ── Test start ─────────────────────────────────────────────────────────

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(
                result.getMethod().getMethodName(),
                result.getMethod().getDescription()
        );
        extentTest.set(test);
        log.info("Test Started  : {}", result.getMethod().getMethodName());
    }

    // ── Test pass ──────────────────────────────────────────────────────────

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().pass("✅ Test PASSED");
        log.info("Test PASSED   : {}", result.getMethod().getMethodName());
    }

    // ── Test fail ──────────────────────────────────────────────────────────

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());

        String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
        if (screenshotPath != null) {
            try {
                extentTest.get().addScreenCaptureFromPath(
                        screenshotPath,
                        "Failure Screenshot"
                );
            } catch (Exception e) {
                log.error("Could not attach screenshot: {}", e.getMessage());
            }
        }
        log.error("Test FAILED   : {} | Reason: {}",
                result.getMethod().getMethodName(),
                result.getThrowable().getMessage());
    }

    // ── Test skip ──────────────────────────────────────────────────────────

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("⚠️ Test SKIPPED");
        log.warn("Test SKIPPED  : {}", result.getMethod().getMethodName());
    }

    // ── Suite finish ───────────────────────────────────────────────────────

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) extent.flush();
        log.info("===== Suite Finished : {} =====", context.getName());
        log.info("Passed: {} | Failed: {} | Skipped: {}",
                context.getPassedTests().size(),
                context.getFailedTests().size(),
                context.getSkippedTests().size());
    }

    // ── Screenshot helper ──────────────────────────────────────────────────

    private String captureScreenshot(String testName) {
        try {
            WebDriver driver = BaseTest.getDriver();
            if (driver == null) return null;

            String folder    = "reports/screenshots/";
            Files.createDirectories(Paths.get(folder));

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath  = folder + testName + "_" + timestamp + ".png";

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), Paths.get(filePath));

            log.info("Screenshot saved: {}", filePath);
            return filePath;

        } catch (IOException e) {
            log.error("Screenshot failed: {}", e.getMessage());
            return null;
        }
    }
}