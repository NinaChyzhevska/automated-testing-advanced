package com.nina.ui.testsuite;

import com.nina.ui.steps.DashboardsSteps;
import com.nina.ui.steps.LoginSteps;
import com.nina.util.EnvironmentPropertyLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Date;

public class DashboardsTest extends BaseTest {

    private static final String DEFAULT_DASHBOARD_NAME = "DEMO DASHBOARD";
    private static final String LAUNCHES_DURATION_WIDGET = "LAUNCHES DURATION CHART";
    private static final String LAUNCHES_PERCENTAGE_WIDGET = "INVESTIGATED PERCENTAGE OF LAUNCHES";

    @BeforeEach
    public void login() {
        new LoginSteps().login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"demo", "1333"})
    public void searchDashboard(String searchValue) {
        new DashboardsSteps()
                .switchToDefaultProject()
                .searchDashboard(searchValue)
                .assertDashboardsSize(1)
                .assertDashboardsListContains(searchValue);
    }

    @Test
    public void createAndDeleteDashboard() {
        String dashboardName = "Created dashboard " + new Date();
        String dashboardDescription = "test";
        new DashboardsSteps()
                .switchToDefaultProject()
                .createDashboard(dashboardName, dashboardDescription)
                .assertDashboardContainsName(dashboardName)
                .deleteDashboard()
                .searchDashboard(dashboardName)
                .assertDashboardsSize(0);
    }

    @Test
    public void editDashboard() {
        String dashboardName = "Initial dashboard name " + new Date();
        String dashboardDescription = "test";
        String updatedDashboardName = "Updated name";
        String updatedDashboardDescription = "updated desc";
        new DashboardsSteps()
                .switchToDefaultProject()
                .createDashboard(dashboardName, dashboardDescription)
                .assertDashboardContainsName(dashboardName)
                .updateDashboardNameAndDescription(updatedDashboardName, updatedDashboardDescription)
                .assertDashboardContainsName(updatedDashboardName)
                .deleteDashboard();
    }

    @Test
    public void addAndRemoveWidget() {
        String dashboardName = "Dashboard with widgets " + new Date();
        String dashboardDescription = "test";
        String widgetName = "Widget name " + new Date();
        new DashboardsSteps()
                .switchToDefaultProject()
                .createDashboard(dashboardName, dashboardDescription)
                .assertDashboardContainsName(dashboardName)
                .addWidgetToDashboard(widgetName)
                .assertWidgetIsPresentOnDashboard(widgetName)
                .removeWidgetFromDashboard(widgetName)
                .assertThatThereIsNoWidgets();
    }

    @Test
    public void changeWidgetOrder() {
        new DashboardsSteps()
                .switchToDefaultProject()
                .selectDashboard(DEFAULT_DASHBOARD_NAME)
                .changeWidgetsOrder(LAUNCHES_DURATION_WIDGET, LAUNCHES_PERCENTAGE_WIDGET)
                .verifyThatWidgetsReordered();
    }
}
