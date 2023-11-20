Feature: Dashboard

  Scenario: Search dashboard by name
    Given I am logged in to the report portal in the UI
    And I switch to dashboard page
    When I search the dashboard by name
    Then I can see that the result list contains name of my dashboard
    And the row result is one
