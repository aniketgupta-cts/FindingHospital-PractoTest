package com.cognizant.project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    private static final Logger log = LogManager.getLogger(ScreenshotUtil.class);

    // Takes a screenshot — your manager's code, browser name added
    public static void takeScreenshot(WebDriver driver, String screenshotName) throws IOException {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File sourceScreenshotPath = screenshot.getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS").format(new Date());
        String browser = com.cognizant.project.tests.PractoTest.browserName;
        String targetPath = System.getProperty("user.dir") + "\\target\\screenshots\\"
                + browser + "_" + screenshotName + "_" + timestamp + ".png";
        File destinationScreenshotPath = new File(targetPath);
        destinationScreenshotPath.getParentFile().mkdirs(); // create folders if not exist
        FileHandler.copy(sourceScreenshotPath, destinationScreenshotPath);
        log.info("Screenshot saved: {}", destinationScreenshotPath.getName());
    }
}