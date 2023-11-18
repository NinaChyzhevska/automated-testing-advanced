package com.nina.rest.testsuite.bdd.steps;

import com.nina.rest.client.AuthClient;
import com.nina.rest.client.DashboardClient;
import com.nina.rest.model.Dashboard;
import com.nina.rest.model.UserSession;
import com.nina.rest.model.Widget;
import com.nina.rest.model.response.DashboardResponse;
import com.nina.rest.model.response.ResponseMessage;
import com.nina.rest.model.response.SearchDashboardsResponse;
import com.nina.util.EnvironmentPropertyLoader;
import io.cucumber.java.en.*;
import org.apache.http.HttpStatus;

import java.util.Date;
import java.util.List;

import static com.nina.rest.config.Config.TEST_WIDGET_ID;
import static org.junit.jupiter.api.Assertions.*;

public class DashboardApiSteps {
    private final AuthClient authClient = new AuthClient();
    private DashboardClient dashboardClient;

    private Long dashboardId;
    private DashboardResponse searchResult;
    private SearchDashboardsResponse searchResultByName;

    private String expectedName;
    private String expectedDescription;

    @Given("I am logged in to the report portal")
    public void loginIntoReportPortal() {
        UserSession userSession = authClient.login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
        dashboardClient = new DashboardClient(userSession);
    }

    @When("I create a dashboard with {string} and {string}")
    public void createDashboard(String name, String description) {
        expectedName = name;
        expectedDescription = description;
        Dashboard created = dashboardClient.createDashboard(HttpStatus.SC_CREATED, name, description);
        dashboardId = created.getId();
    }

    @And("I search the dashboard by id")
    public void searchTheDashboardById() {
        searchResult = dashboardClient.getDashboardById(dashboardId);
    }

    @Then("I can see its name and description")
    public void verifyDashboardNameAndDescription() {
        assertEquals(HttpStatus.SC_OK, searchResult.getStatusCode());
        assertEquals(expectedName, searchResult.getDashboard().getName());
        assertEquals(expectedDescription, searchResult.getDashboard().getDescription());
    }

    @When("I delete dashboard by id")
    public void deleteDashboardById() {
        ResponseMessage deleteResult = dashboardClient.deleteDashboardById(dashboardId);
        assertEquals("Dashboard with ID = '" + dashboardId + "' successfully deleted.", deleteResult.getMessage());
    }

    @Then("the dashboard is not found")
    public void verifyDashboardNotExists() {
        assertEquals(HttpStatus.SC_NOT_FOUND, searchResult.getStatusCode());
    }

    @When("I create a new dashboard")
    public void createDashboard() {
        String dashboardName = "AUTO_Widget" + new Date();
        Dashboard dashboard = dashboardClient.createDashboard(HttpStatus.SC_CREATED, dashboardName, "description");
        dashboardId = dashboard.getId();
    }

    @And("add one widget to the newest created dashboard")
    public void addWidgetToDashboard() {
        Widget widget = new Widget();
        widget.setWidgetId(TEST_WIDGET_ID);
        dashboardClient.addWidgetToDashboard(HttpStatus.SC_OK, widget, dashboardId);
    }

    @Then("I can see the widget in the dashboard")
    public void verifyDashboardWidgetExists() {
        Dashboard searchResult = dashboardClient.getDashboardById(HttpStatus.SC_OK, dashboardId);
        List<Widget> widgets = searchResult.getWidgets();
        assertEquals(1, widgets.size());
        assertEquals(TEST_WIDGET_ID, widgets.get(0).getWidgetId());
    }

    @And("I search the dashboard by name")
    public void searchDashboardByName() {
        searchResultByName = dashboardClient.searchDashboards(expectedName);
    }

    @Then("I can see that the dashboard is present on the dashboard list")
    public void verifyDashboardPresentOnDashboardList() {
        assertEquals(1, searchResultByName.getPage().getTotalElements());
        assertEquals(1, searchResultByName.getPage().getTotalPages());

        List<Dashboard> dashboards = searchResultByName.getContent();
        assertNotNull(dashboards);
        assertEquals(1, dashboards.size());
        Dashboard found = dashboards.get(0);
        assertEquals(dashboardId, found.getId());
    }

    @But("the list with results is empty")
    public void verifyResultsEmpty() {
        SearchDashboardsResponse responseAfterRemoval = dashboardClient.searchDashboards(expectedName);
        assertTrue(responseAfterRemoval.getContent().isEmpty());
        assertEquals(0, responseAfterRemoval.getPage().getTotalElements());
    }
}
