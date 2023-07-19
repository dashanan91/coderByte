Feature: basic functional check of school functionalities

  Background: Loading Driver to execute Test
    Given User loads driver to run test on "chrome" browser

  Scenario: filling staff add and making payment
    Given User loads landing page of school webpage taking url from "credentials/loginCreds.properties"
    When User enters login credentials taking data from "credentials/loginCreds.properties"
    Then User should login to the landing page verified from logged in user as "AFREEN PARVEZ SAYED"
    When User clicks on StaffProfileManagement dropdown and clicks on StaffAdd page
    Then User should land on a page requesting details of staff to be added
    And User fills login details from excel file "newStaff.xlsx"
    Then User clicks on FeeConfigurationMenu and verifies "Fee Collection" as heading of page
    When User enters "200011312" into GlocbalStudentSearch box and searches for student
    Then User should find one pending Invoice section from which user chooses payment mode as "Cash" under payments
    And User clicks on pay button to collect the fee
    And User verifies that payment has been completed successfully
