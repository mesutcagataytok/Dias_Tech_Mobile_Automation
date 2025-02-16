@Case2
Feature: Akakce Mobile App - Searching and Filtering

  @case2
  Scenario Outline: Locate and sort a laptop in Akakce mobile app
    Given User opens the Akakce mobile app
    When User skips the login if prompted with a sign-in screen
    And User types "<SearchKey>" into the search field and starts the search
    And User taps the filtering option
    And User navigates to Computers → Components section and selects View Items
    And User arranges the items in descending price order
    And User selects the 10th product from the displayed results
    And User presses the Visit Product button
    Then User should confirm the presence of the Go to Seller button on the product details page

    Examples:
      |SearchKey|
      | laptop  |
