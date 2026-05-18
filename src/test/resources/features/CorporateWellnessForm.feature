Feature: Corporate Wellness Form on Practo

  As a corporate HR representative
  I want to fill in the corporate wellness enquiry form on Practo
  So that I can explore employee health and wellness plans

  Background:
    Given the user is on the Practo home page

  @corporate @regression
  Scenario: Fill and validate the corporate wellness enquiry form
    When the user clicks on the Corporate Wellness link
    Then a new tab should open with the corporate wellness page
    And the page title should contain "Employee Health | Corporate Health & Wellness Plans | Practo"
    When the user fills in the form with the following details
      | Name  | Company    | Phone   | Email                  |
      | Gopal | Cognizant  | 8970657 | gopal@cognizant.com    |
    Then the submit button state should be determined
    And the form interaction should complete successfully

  @corporate @negative
  Scenario: Validate corporate wellness form with missing phone number
    When the user clicks on the Corporate Wellness link
    Then a new tab should open with the corporate wellness page
    And the page title should contain "Employee Health | Corporate Health & Wellness Plans | Practo"
    When the user fills in the form with the following details
      | Name  | Company    | Phone | Email                  |
      | Gopal | Cognizant  |       | gopal@cognizant.com    |
    Then the submit button should be disabled

  @corporate @negative
  Scenario: Validate corporate wellness form with missing email
    When the user clicks on the Corporate Wellness link
    Then a new tab should open with the corporate wellness page
    And the page title should contain "Employee Health | Corporate Health & Wellness Plans | Practo"
    When the user fills in the form with the following details
      | Name  | Company    | Phone   | Email |
      | Gopal | Cognizant  | 8970657 |       |
    Then the submit button should be disabled
