package com.nina.ui.testsuite.bdd.steps;

import com.nina.ui.steps.DashboardsSteps;
import com.nina.ui.steps.LoginSteps;
import com.nina.util.EnvironmentPropertyLoader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.codeborne.selenide.Selenide.open;

public class DashboardSteps {

    private static final String TEST_DASHBOARD_NAME = "demo";
    private final DashboardsSteps dashboardsSteps = new DashboardsSteps();

    @When("I search the dashboard by name")
    public void searchTheDashboardByName() {
        dashboardsSteps.searchDashboard(TEST_DASHBOARD_NAME);
    }

    @And("I switch to dashboard page")
    public void switchToDashboardPage() {
        dashboardsSteps.switchToDefaultProject();
    }

    @Given("I am logged in to the report portal in the UI")
    public void loginToReportPortal() {
        open(EnvironmentPropertyLoader.getProperty("hostUrl"));
        new LoginSteps().login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
    }

    @Then("I can see that the result list contains name of my dashboard")
    public void verifyThatListContainsExpectedDashboard() {
        dashboardsSteps.assertDashboardsListContains(TEST_DASHBOARD_NAME);
    }

    @And("the row result is one")
    public void verifySingleDashboard() {
        dashboardsSteps.assertDashboardsSize(1);
    }
}
