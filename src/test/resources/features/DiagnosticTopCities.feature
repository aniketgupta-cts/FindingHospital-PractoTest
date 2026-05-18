Feature: Diagnostic Lab Test Cities on Practo

  As a user of Practo
  I want to view available cities for lab tests
  So that I can find diagnostic services in my city

  Background:
    Given the user is on the Practo home page

  @diagnostic @smoke
  Scenario: Retrieve top cities available for diagnostic lab tests
    When the user navigates back to the home page
    And the user clicks on "Lab Tests"
    Then the diagnostic page should be displayed
    And the user retrieves the list of top cities
    And the top cities list should not be empty
