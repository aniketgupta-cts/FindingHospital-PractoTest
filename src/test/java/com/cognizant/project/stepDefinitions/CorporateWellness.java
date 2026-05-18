package com.cognizant.project.stepDefinitions;

import com.cognizant.project.pages.CorporateWellnessPage;
import com.cognizant.project.base.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class CorporateWellness {

    WebDriver driver = BaseTest.getDriver();
    CorporateWellnessPage corporateWellnessPage = new CorporateWellnessPage(driver);

    @Given("the user is on the Practo home page")
    public void the_user_is_on_the_practo_home_page() {
        driver.get("https://www.practo.com");
        System.out.println("Navigated to Practo home page");
    }

    @When("the user clicks on the Corporate Wellness link")
    public void the_user_clicks_on_the_corporate_wellness_link() {
        corporateWellnessPage.clickCorporateWellnessLink();
        System.out.println("Clicked on Corporate Wellness link");
    }

    @Then("a new tab should open with the corporate wellness page")
    public void a_new_tab_should_open_with_the_corporate_wellness_page() {
        corporateWellnessPage.switchToNewTab();
        System.out.println("Switched to new Corporate Wellness tab");
    }

    @And("the page title should contain {string}")
    public void the_page_title_should_contain(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertTrue(actualTitle.contains(expectedTitle),
                "Page title mismatch! Expected: " + expectedTitle + " | Actual: " + actualTitle);
        System.out.println("Page title verified: " + actualTitle);
    }

    @When("the user fills in the form with the following details")
    public void the_user_fills_in_the_form_with_the_following_details(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String name    = row.get("Name");
            String company = row.get("Company");
            String phone   = row.get("Phone");
            String email   = row.get("Email");
            corporateWellnessPage.fillForm(name, company, phone, email);
            System.out.println("Form filled — Name: " + name + ", Company: " + company
                    + ", Phone: " + phone + ", Email: " + email);
        }
    }

    @Then("the submit button state should be determined")
    public void the_submit_button_state_should_be_determined() {
        boolean isEnabled = corporateWellnessPage.isSubmitEnabled();
        System.out.println("Submit button enabled: " + isEnabled);
    }

    @And("the form interaction should complete successfully")
    public void the_form_interaction_should_complete_successfully() {
        corporateWellnessPage.clickSubmit();
        System.out.println("Form submitted successfully");
    }

    @Then("the submit button should be disabled")
    public void the_submit_button_should_be_disabled() {
        boolean isEnabled = corporateWellnessPage.isSubmitEnabled();
        Assert.assertFalse(isEnabled, "Submit button should be DISABLED but it is enabled!");
        System.out.println("Verified: Submit button is correctly disabled");
    }
}