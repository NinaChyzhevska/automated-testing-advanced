Feature: Dashboard
  Background:
    Given I am logged in to the report portal

  Scenario Outline: Create and delete dashboard
    When I create a dashboard with <name> and <description>
    And I search the dashboard by id
    Then I can see its name and description
    When I delete dashboard by id
    And I search the dashboard by id
    Then the dashboard is not found
    Examples:
      |name|description|
      |"Chinese chars 这是一个带有特殊字符的消息"|"description jaCNlF9gqMvCEQRirEWnMFo0DIaXPC2ASqrdShK2meX4N58pLUSrNxuS6IudJOKC0F3X0SUMCXAHUwybE8ZPaTqPB"|
      |"empty desc"                 |""|
      |"Boundary 128 Positive Test jaCNlF9gqMvCEQRirEWnMFo0DIaXPC2ASqrdShK2meX4N58pLUSrNxuS6IudJOKC0F3X0SUMCXAHUwybE8ZPaTqPB123456trtrtf"|"desc"|
      |"12345 Numbers"                                                                                                                   |"desc"|
      |"Cyrillic characters абвгдЇьґє"                                                                                                   |"desc"|
      |"&<>?][}{/$^*;:!@#~% other common special chars"                                                                                         |"desc"|
      |"min"                                                                                                                                    |"desc"|

  Scenario: Add widget to the dashboard
    When I create a new dashboard
    And add one widget to the newest created dashboard
    Then I can see the widget in the dashboard

  Scenario Outline: Search dashboard on list using name
    When I create a dashboard with <name> and <description>
    And I search the dashboard by name
    Then I can see that the dashboard is present on the dashboard list
    When I delete dashboard by id
    And I search the dashboard by id
    But the list with results is empty
    Examples:
      |name|description|
      |"Chinese chars 是一个带有特殊字符的消息"| "description jaCNlF9gqMvCEQRirEWnMFo0DIaXPC2ASqrdShK2meX4N58pLUSrNxuS6IudJOKC0F3X0SUMCXAHUwybE8ZPaTqPB"|
      |"desc empty"                | ""                                                                                                     |
      |"Boundary 128 Positive Test naCNlF9gqMvCEQRirEWnMFo0DIaXPC2ASqrdShK2meX4N58pLUSrNxuS6IudJOKC0F3X0SUMCXAHUwybE8ZPaTqPB123456trtrtf"| "desc"|
      |"1234567 Numbers"                                                                                                                 | "desc"|
      |"Cyrillic characters абвгдЇьґєя"                                                                                                  | "desc"|
      |"<>?][}{/&$^*;:!@#~% other common special chars"                                                                                  | "desc"|
      |"one"                                                                                                                             | "desc"|

