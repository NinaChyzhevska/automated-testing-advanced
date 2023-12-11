package com.nina.rest.testsuite;

import com.nina.rest.client.DashboardClient;
import com.nina.rest.model.Dashboard;
import com.nina.rest.model.Widget;
import com.nina.rest.model.response.ResponseMessage;
import com.nina.rest.model.response.SearchDashboardsResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import static com.nina.rest.config.Config.TEST_WIDGET_ID;
import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class DashboardTest extends BaseAuthTest {
    private static final Logger logger = LoggerFactory.getLogger(DashboardTest.class);

    private static DashboardClient dashboardClient;

    @BeforeAll
    public static void setUp() {
        dashboardClient = new DashboardClient(userSession);
    }

    @ParameterizedTest
    @MethodSource("com.nina.util.TestDataProvider#getDashboardDataForPositiveTest")
    public void createAndDeleteDashboardPositiveTest(String name, String description) {
        logger.info("Starting positive dashboard creation test for " + name);
        Dashboard created = dashboardClient.createDashboard(HttpStatus.SC_CREATED, name, description);
        Long id = created.getId();

        Dashboard searchResult = dashboardClient.getDashboardById(HttpStatus.SC_OK, id);
        assertEquals(name, searchResult.getName());
        assertEquals(description, searchResult.getDescription());

        deleteDashboard(id);
        dashboardClient.getDashboardById(HttpStatus.SC_NOT_FOUND, id);
    }

    @Test
    public void updateDashboardTest() {
        String dashboardName = "AUTO_" + new Date();
        Dashboard dashboard = dashboardClient.createDashboard(HttpStatus.SC_CREATED, dashboardName, "description");
        Long dashboardId = dashboard.getId();
        String newDashboardName = "AUTO_UPDATED_NAME_ " + new Date();
        String newDescription = "desc2";

        dashboardClient.updateDashboard(dashboardId, newDashboardName, newDescription);
        Dashboard searchResult = dashboardClient.getDashboardById(HttpStatus.SC_OK, dashboardId);
        assertEquals(newDashboardName, searchResult.getName());
        assertEquals(newDescription, searchResult.getDescription());

    }

    @ParameterizedTest
    @MethodSource("com.nina.util.TestDataProvider#getDashboardDataForNegativeTest")
    public void createDashboardNegativeTest(String name, String description) {
        logger.info("Starting negative dashboard creation test for " + name);
        dashboardClient.createDashboard(HttpStatus.SC_BAD_REQUEST, name, description);
    }

    @ParameterizedTest
    @MethodSource("com.nina.util.TestDataProvider#getDashboardDataForSearchTest")
    public void searchDashboardOnListByName(String name, String description) {
        logger.info("Starting dashboard search test for " + name);
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
    public void addAndRemoveWidgetFromDashboard() {
        String dashboardName = "AUTO_Widget" + new Date();
        logger.info("Starting widget adding to dashboard test for " + dashboardName);
        Dashboard dashboard = dashboardClient.createDashboard(HttpStatus.SC_CREATED, dashboardName, "description");
        Widget widget = new Widget();
        widget.setWidgetId(TEST_WIDGET_ID);
        Long dashboardId = dashboard.getId();

        dashboardClient.addWidgetToDashboard(HttpStatus.SC_OK, widget, dashboardId);

        Dashboard searchResult = dashboardClient.getDashboardById(HttpStatus.SC_OK, dashboardId);
        List<Widget> widgets = searchResult.getWidgets();
        assertEquals(1, widgets.size());
        assertEquals(TEST_WIDGET_ID, widgets.get(0).getWidgetId());

        dashboardClient.removeWidgetFromDashboard(dashboardId);
        Dashboard searchResultAfterRemoval = dashboardClient.getDashboardById(HttpStatus.SC_OK, dashboardId);
        List<Widget> widgetsCheck = searchResultAfterRemoval.getWidgets();
        assertEquals(0, widgetsCheck.size());
    }

    private void deleteDashboard(long id) {
        ResponseMessage deleteResult = dashboardClient.deleteDashboardById(id);
        assertEquals("Dashboard with ID = '" + id + "' successfully deleted.", deleteResult.getMessage());
    }
}
