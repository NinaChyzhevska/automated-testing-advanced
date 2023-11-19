package com.nina.rest.testsuite.bdd.steps;

import com.nina.rest.model.Dashboard;
import com.nina.rest.model.Widget;
import com.nina.rest.testsuite.bdd.context.DashboardContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpStatus;

import java.util.Date;
import java.util.List;

import static com.nina.rest.config.Config.TEST_WIDGET_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardManagementSteps {
    private final DashboardContext context;

    public DashboardManagementSteps(DashboardContext context) {
        this.context = context;
    }

    @When("I create a dashboard with {string} and {string}")
    public void createDashboard(String name, String description) {
        Dashboard created = context.getDashboardClient().createDashboard(HttpStatus.SC_CREATED, name, description);
        context.setDashboardId(created.getId());
        context.setDashboardName(name);
        context.setDashboardDescription(description);
    }

    @When("I delete dashboard by id")
    public void deleteDashboardById() {
        var dashboardId = context.getDashboardId();
        var deleteResult = context.getDashboardClient().deleteDashboardById(dashboardId);
        assertEquals("Dashboard with ID = '" + dashboardId + "' successfully deleted.", deleteResult.getMessage());
    }

    @When("I create a new dashboard")
    public void createDashboard() {
        String dashboardName = "AUTO_Widget" + new Date();
        Dashboard dashboard = context.getDashboardClient().createDashboard(HttpStatus.SC_CREATED,
                dashboardName, "description");
        context.setDashboardId(dashboard.getId());
    }

    @And("add one widget to the newest created dashboard")
    public void addWidgetToDashboard() {
        Widget widget = new Widget();
        widget.setWidgetId(TEST_WIDGET_ID);
        context.getDashboardClient().addWidgetToDashboard(HttpStatus.SC_OK, widget, context.getDashboardId());
    }

    @Then("I can see the widget in the dashboard")
    public void verifyDashboardWidgetExists() {
        Dashboard searchResult = context.getDashboardClient().getDashboardById(HttpStatus.SC_OK, context.getDashboardId());
        List<Widget> widgets = searchResult.getWidgets();
        assertEquals(1, widgets.size());
        assertEquals(TEST_WIDGET_ID, widgets.get(0).getWidgetId());
    }
}
