Feature: Hospital Search on Practo

  As a user of Practo
  I want to search for hospitals in a specific location
  So that I can find highly-rated hospitals with parking facilities

  Background:
    Given the user is on the Practo home page

  @hospital @smoke
  Scenario: Search for hospitals with parking in Bangalore with rating above 3.5
    When the user searches for location "Bangalore"
    And the user searches for service "Hospital"
    Then the hospital listing page should be displayed
    And the user retrieves hospitals with parking and rating above 3.5
    And the hospital list should be displayed successfully
