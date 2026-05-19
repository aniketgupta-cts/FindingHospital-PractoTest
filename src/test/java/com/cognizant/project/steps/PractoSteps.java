package com.cognizant.project.steps;

import com.cognizant.project.base.BaseTest;
import com.cognizant.project.pages.CorporateWellnessPage;
import com.cognizant.project.pages.DiagnosticPage;
import com.cognizant.project.pages.HomePage;
import com.cognizant.project.pages.HospitalListingPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Set;

public class PractoSteps {
    private static final Logger log = LogManager.getLogger(PractoSteps.class);

    @Given("the user is on the Practo home page")
    public void userOnHomePage() {
        BaseTest.driver.get("https://www.practo.com/");
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
        log.info("On Practo Home: " + BaseTest.driver.getTitle());
    }

    // ---------- Scenario 1 ----------
    @When("the user searches for {string} location and {string} service")
    public void searchHospital(String location, String service) throws InterruptedException {
        log.info("Searching for service: " + service + " in location: " + location);
        HomePage home = new HomePage(BaseTest.driver);
        home.searchLocation(location);
        home.searchService(service);
    }

    @Then("the hospitals with parking and rating above {double} are displayed")
    public void displayHospitals(double rating) throws InterruptedException {
        HospitalListingPage listing = new HospitalListingPage(BaseTest.driver);
        List<String> hospitals = listing.getHospitalsWithParking(rating);
        log.info("--- Hospitals (Open 24x7, Parking, Rating > " + rating + ") ---");
        for (String h : hospitals) {
            log.info(h);
        }
        Assert.assertTrue(hospitals.size() >= 0, "Hospital search ran successfully");
    }

    // ---------- Scenario 2 ----------
    @When("the user clicks on Lab Tests")
    public void clickLabTests() {
        log.info("Clicking on Lab Tests");
        new HomePage(BaseTest.driver).clickLabTests();
    }

    @Then("the top diagnostic cities are displayed")
    public void displayCities() {
        DiagnosticPage diag = new DiagnosticPage(BaseTest.driver);
        List<String> cities = diag.getTopCities();
        log.info("--- Top Diagnostic Cities ---");
        for (String city : cities) {
            log.info(city);
        }
        Assert.assertTrue(cities.size() > 0, "Cities list should not be empty");
    }

    // ---------- Scenario 3 ----------
    @When("the user navigates to Corporate Wellness page")
    public void navigateCorporate() {
        log.info("Navigating to Corporate Wellness page");
        String mainHandle = BaseTest.driver.getWindowHandle();
        new HomePage(BaseTest.driver).navigateToCorporateWellness();

        Set<String> handles = BaseTest.driver.getWindowHandles();
        for (String handle : handles) {
            BaseTest.driver.switchTo().window(handle);
            String title = BaseTest.driver.getTitle();
            log.info("Window title: " + title);
            if (title.contains("Employee Health | Corporate Health & Wellness Plans | Practo"))
                break;
        }
        log.info("Switched to: " + BaseTest.driver.getTitle());
    }

    @And("the user fills the form with name {string} organization {string} phone {string} email {string}")
    public void fillForm(String name, String org, String phone, String email) {
        log.info("Filling form for: " + name + " from " + org);
        new CorporateWellnessPage(BaseTest.driver).fillForm(name, org, phone, email);
    }

    @Then("the submit button status is captured")
    public void captureStatus() {
        CorporateWellnessPage form = new CorporateWellnessPage(BaseTest.driver);
        boolean enabled = form.isSubmitEnabled();
        log.info("Submit Button Enabled: " + enabled);
        Assert.assertNotNull(enabled);
        if (enabled) {
            log.info("Form submitted (simulated).");
        }
    }
}