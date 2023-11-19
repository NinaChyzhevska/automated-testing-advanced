package com.nina.ui.testsuite.bdd.steps;

import com.nina.ui.steps.DashboardsSteps;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DashboardUiSteps {
    private static final String TEST_DASHBOARD_NAME = "demo";

    private final DashboardsSteps dashboardsSteps;

    public DashboardUiSteps(DashboardsSteps dashboardsSteps) {
        this.dashboardsSteps = dashboardsSteps;
    }

    @When("I search the dashboard by name")
    public void searchTheDashboardByName() {
        dashboardsSteps.searchDashboard(TEST_DASHBOARD_NAME);
    }

    @And("I switch to dashboard page")
    public void switchToDashboardPage() {
        dashboardsSteps.switchToDefaultProject();
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
