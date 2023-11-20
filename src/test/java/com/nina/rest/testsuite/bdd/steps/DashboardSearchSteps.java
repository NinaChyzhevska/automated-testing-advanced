package com.nina.rest.testsuite.bdd.steps;

import com.nina.rest.model.Dashboard;
import com.nina.rest.model.response.SearchDashboardsResponse;
import com.nina.rest.testsuite.bdd.context.DashboardContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Then;
import org.apache.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DashboardSearchSteps {
    private final DashboardContext context;

    public DashboardSearchSteps(DashboardContext context) {
        this.context = context;
    }

    @And("I search the dashboard by name")
    public void searchDashboardByName() {
        var dashboardName = context.getDashboardName();
        var searchResponseByName = context.getDashboardClient().searchDashboards(dashboardName);
        context.setDashboardsSearchResponse(searchResponseByName);
    }

    @And("I search the dashboard by id")
    public void searchTheDashboardById() {
        var searchResult = context.getDashboardClient().getDashboardById(context.getDashboardId());
        context.setDashboardResponse(searchResult);
    }

    @Then("I can see its name and description")
    public void verifyDashboardNameAndDescription() {
        var dashboardResponse = context.getDashboardResponse();
        assertEquals(HttpStatus.SC_OK, dashboardResponse.getStatusCode());
        assertEquals(context.getDashboardName(), dashboardResponse.getDashboard().getName());
        assertEquals(context.getDashboardDescription(), dashboardResponse.getDashboard().getDescription());
    }

    @Then("the dashboard is not found")
    public void verifyDashboardNotExists() {
        assertEquals(HttpStatus.SC_NOT_FOUND, context.getDashboardResponse().getStatusCode());
    }

    @Then("I can see that the dashboard is present on the dashboard list")
    public void verifyDashboardPresentOnDashboardList() {
        var searchResultByName = context.getDashboardsSearchResponse();
        assertEquals(1, searchResultByName.getPage().getTotalElements());
        assertEquals(1, searchResultByName.getPage().getTotalPages());

        List<Dashboard> dashboards = searchResultByName.getContent();
        assertNotNull(dashboards);
        assertEquals(1, dashboards.size());
        Dashboard found = dashboards.get(0);
        assertEquals(context.getDashboardId(), found.getId());
    }

    @But("the list with results is empty")
    public void verifyResultsEmpty() {
        var dashboardName = context.getDashboardName();
        SearchDashboardsResponse responseAfterRemoval = context.getDashboardClient().searchDashboards(dashboardName);
        assertTrue(responseAfterRemoval.getContent().isEmpty());
        assertEquals(0, responseAfterRemoval.getPage().getTotalElements());
    }
}
