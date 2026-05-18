package com.cognizant.project.stepDefinitions;

import com.cognizant.project.pages.DiagnosticPage;
import com.cognizant.project.pages.HomePage;
import com.cognizant.project.base.BaseTest;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

public class DiagnosticTopCities {

    WebDriver driver = BaseTest.getDriver();
    DiagnosticPage diagnosticPage = new DiagnosticPage(driver);
    HomePage homePage = new HomePage(driver);

    // NOTE: "Given the user is on the Practo home page" is defined in
    // CorporateWellnessSteps — Cucumber finds it automatically via glue path.
    // Do NOT redefine it here.

    @When("the user navigates back to the home page")
    public void the_user_navigates_back_to_the_home_page() {
        driver.get("https://www.practo.com");
        System.out.println("Navigated back to Practo home page");
    }

    @And("the user clicks on {string}")
    public void the_user_clicks_on(String linkText) {
        homePage.clickLabTests();
        System.out.println("Clicked on: " + linkText);
    }

    @Then("the diagnostic page should be displayed")
    public void the_diagnostic_page_should_be_displayed() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("lab-tests") || currentUrl.contains("diagnostic"),
                "Diagnostic page not displayed. Current URL: " + currentUrl
        );
        System.out.println("Diagnostic page displayed. URL: " + currentUrl);
    }

    @And("the user retrieves the list of top cities")
    public void the_user_retrieves_the_list_of_top_cities() {
        List<String> cities = diagnosticPage.getTopCities();
        System.out.println("Top cities retrieved: " + cities);
        BaseTest.setCitiesList(cities);
    }

    @And("the top cities list should not be empty")
    public void the_top_cities_list_should_not_be_empty() {
        List<String> cities = BaseTest.getCitiesList();
        Assert.assertNotNull(cities, "Cities list is null!");
        Assert.assertFalse(cities.isEmpty(), "Cities list is empty!");
        System.out.println("Verified: Top cities list has " + cities.size() + " cities:");
        cities.forEach(city -> System.out.println("  - " + city));
    }
}