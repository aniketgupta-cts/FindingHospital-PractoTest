Feature: Finding Hospitals on Practo

  Background:
    Given the user is on the Practo home page

  Scenario: Find Hospitals in Bangalore open 24x7 with parking and rating above 3.5
    When the user searches for "Bangalore" location and "Hospital" service
    Then the hospitals with parking and rating above 3.5 are displayed

  Scenario: Capture top cities from Diagnostics page
    When the user clicks on Lab Tests
    Then the top diagnostic cities are displayed

  Scenario: Fill Corporate Wellness form with invalid details and capture warning
    When the user navigates to Corporate Wellness page
    And the user fills the form with name "Gopal" organization "Cognizant" phone "8970657" email "gopal@cognizant.com"
    Then the submit button status is captured