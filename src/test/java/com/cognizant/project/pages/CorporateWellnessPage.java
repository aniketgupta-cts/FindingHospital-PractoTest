package com.cognizant.project.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.Set;

public class CorporateWellnessPage {
    WebDriver driver;

    // Locators
    private By name        = By.id("name");
    private By orgName     = By.id("organizationName");
    private By contact     = By.id("contactNumber");
    private By email       = By.id("officialEmailId");
    private By orgSize     = By.id("organizationSize");
    private By interest    = By.id("interestedIn");
    private By submitBtn   = By.xpath("//header//button[@type='submit']");

    public CorporateWellnessPage(WebDriver driver) {
        this.driver = driver;
    }

    // ── Used by PractoTest (original) ──────────────────────────────────────────

    public void fillForm(String uName, String uOrg, String uPhone, String uEmail) {
        if (uName  != null && !uName.isEmpty())  driver.findElement(name).sendKeys(uName);
        if (uOrg   != null && !uOrg.isEmpty())   driver.findElement(orgName).sendKeys(uOrg);
        if (uPhone != null && !uPhone.isEmpty()) driver.findElement(contact).sendKeys(uPhone);
        if (uEmail != null && !uEmail.isEmpty()) driver.findElement(email).sendKeys(uEmail);

        new Select(driver.findElement(orgSize)).selectByVisibleText("<500");
        new Select(driver.findElement(interest)).selectByVisibleText("Taking a demo");
    }

    public boolean isSubmitEnabled() {
        return driver.findElement(submitBtn).isEnabled();
    }

    public void clickSubmit() {
        if (isSubmitEnabled()) {
            driver.findElement(submitBtn).click();
        }
    }

    // ── Used by Cucumber step definitions ─────────────────────────────────────

    /** Clicks For Corporates → Health & Wellness Plans on home page */
    public void clickCorporateWellnessLink() {
        driver.findElement(By.xpath("//*[text()='For Corporates' and @class='nav-interact']")).click();
        driver.findElement(By.xpath("//*[text()='Health & Wellness Plans' and @class='nav-interact']")).click();
    }

    /** Switches focus to the newly opened Corporate Wellness tab */
    public void switchToNewTab() {
        String currentHandle = driver.getWindowHandle();
        Set<String> allHandles = driver.getWindowHandles();
        for (String handle : allHandles) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                String title = driver.getTitle();
                if (title.contains("Employee Health") || title.contains("Corporate Health")) {
                    break;
                }
            }
        }
    }

    /** Alias used by Cucumber steps — delegates to isSubmitEnabled() */
    public boolean isSubmitButtonEnabled() {
        return isSubmitEnabled();
    }

    /** Alias used by Cucumber steps — delegates to clickSubmit() */
    public void clickSubmitIfEnabled() {
        clickSubmit();
    }
}