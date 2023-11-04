package com.nina.rest.testsuite;

import com.nina.rest.client.DashboardClient;
import com.nina.rest.model.Dashboard;
import com.nina.rest.model.Widget;
import com.nina.rest.model.response.ResponseMessage;
import com.nina.rest.model.response.SearchDashboardsResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Date;
import java.util.List;

import static com.nina.rest.config.Config.TEST_WIDGET_ID;
import static org.junit.jupiter.api.Assertions.*;

public class DashboardTest extends BaseAuthTest {

    private final DashboardClient dashboardClient = new DashboardClient(userSession);

    @ParameterizedTest
    @MethodSource("com.nina.util.TestDataProvider#getDashboardDataForPositiveTest")
    public void createAndDeleteDashboardPositiveTest(String name, String description) {
        Dashboard created = dashboardClient.createDashboard(HttpStatus.SC_CREATED, name, description);
        Long id = created.getId();

        Dashboard searchResult = dashboardClient.getDashboardById(HttpStatus.SC_OK, id);
        assertEquals(name, searchResult.getName());
        assertEquals(description, searchResult.getDescription());

        deleteDashboard(id);
        dashboardClient.getDashboardById(HttpStatus.SC_NOT_FOUND, id);
    }

    @ParameterizedTest
    @MethodSource("com.nina.util.TestDataProvider#getDashboardDataForNegativeTest")
    public void createDashboardNegativeTest(String name, String description) {
        dashboardClient.createDashboard(HttpStatus.SC_BAD_REQUEST, name, description);
    }

    @ParameterizedTest
    @MethodSource("com.nina.util.TestDataProvider#getDashboardDataForSearchTest")
    public void searchDashboardOnListByName(String name, String description) {
        Dashboard created = dashboardClient.createDashboard(HttpStatus.SC_CREATED, name, description);

        SearchDashboardsResponse response = dashboardClient.searchDashboards(name);
        assertEquals(1, response.getPage().getTotalElements());
        assertEquals(1, response.getPage().getTotalPages());

        List<Dashboard> dashboards = response.getContent();
        assertNotNull(dashboards);
        assertEquals(1, dashboards.size());
        Dashboard found = dashboards.get(0);
        assertEquals(created.getId(), found.getId());

        deleteDashboard(created.getId());

        SearchDashboardsResponse responseAfterRemoval = dashboardClient.searchDashboards(name);
        assertTrue(responseAfterRemoval.getContent().isEmpty());
        assertEquals(0, responseAfterRemoval.getPage().getTotalElements());
    }

    @Test
    public void addWidgetToDashboard() {
        Dashboard dashboard = dashboardClient.createDashboard(HttpStatus.SC_CREATED, "AUTO_Widget" + new Date(), "description");
        Widget widget = new Widget();
        widget.setWidgetId(TEST_WIDGET_ID);
        Long dashboardId = dashboard.getId();

        dashboardClient.addWidgetToDashboard(HttpStatus.SC_OK, widget, dashboardId);

        Dashboard searchResult = dashboardClient.getDashboardById(HttpStatus.SC_OK, dashboardId);
        List<Widget> widgets = searchResult.getWidgets();
        assertEquals(1, widgets.size());
        assertEquals(TEST_WIDGET_ID, widgets.get(0).getWidgetId());
    }

    private void deleteDashboard(long id) {
        ResponseMessage deleteResult = dashboardClient.deleteDashboardById(id);
        assertEquals("Dashboard with ID = '" + id + "' successfully deleted.", deleteResult.getMessage());
    }
}
