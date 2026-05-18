package com.cognizant.project.stepDefinitions;

import com.cognizant.project.pages.HospitalListingPage;
import com.cognizant.project.pages.HomePage;
import com.cognizant.project.base.BaseTest;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

public class HospitalSearch {

    WebDriver driver = BaseTest.getDriver();
    HospitalListingPage hospitalListingPage = new HospitalListingPage(driver);
    HomePage homePage = new HomePage(driver);

    // NOTE: "Given the user is on the Practo home page" is defined in
    // CorporateWellnessSteps — Cucumber finds it automatically via glue path.
    // Do NOT redefine it here.

    @When("the user searches for location {string}")
    public void the_user_searches_for_location(String location) throws InterruptedException {
        homePage.searchLocation(location);
        System.out.println("Entered location: " + location);
    }

    @And("the user searches for service {string}")
    public void the_user_searches_for_service(String service) throws InterruptedException {
        homePage.searchService(service);
        System.out.println("Searched for service: " + service);
    }

    @Then("the hospital listing page should be displayed")
    public void the_hospital_listing_page_should_be_displayed() {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(
                currentUrl.toLowerCase().contains("hospital"),
                "Hospital listing page not displayed. Current URL: " + currentUrl
        );
        System.out.println("Hospital listing page displayed. URL: " + currentUrl);
    }

    @And("the user retrieves hospitals with parking and rating above 3.5")
    public void the_user_retrieves_hospitals_with_parking_and_rating_above_3_5() throws InterruptedException {
        List<String> hospitals = hospitalListingPage.getHospitalsWithParking(3.5);
        System.out.println("Hospitals with parking and rating > 3.5:");
        hospitals.forEach(h -> System.out.println("  - " + h));
        BaseTest.setHospitalList(hospitals);
    }

    @And("the hospital list should be displayed successfully")
    public void the_hospital_list_should_be_displayed_successfully() {
        List<String> hospitals = BaseTest.getHospitalList();
        Assert.assertNotNull(hospitals, "Hospital list is null!");
        Assert.assertFalse(hospitals.isEmpty(),
                "No hospitals found with parking and rating above 3.5!");
        System.out.println("Verified: " + hospitals.size() + " hospital(s) found successfully");
    }
}