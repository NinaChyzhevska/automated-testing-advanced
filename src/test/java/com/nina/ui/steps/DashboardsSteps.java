package com.nina.ui.steps;

import com.nina.ui.pages.DashboardsPage;

import static com.nina.ui.util.driver.Waiters.waitPageToLoad;

public class DashboardsSteps {
    private final DashboardsPage dashboardsPage = new DashboardsPage();

    public void createDashboard() {
        dashboardsPage.getDashboardButton().click();
        waitPageToLoad();
    }

    public void searchDashboard(String searchValue) {
        dashboardsPage.getDashboardSearch().sendKeys(searchValue);
        waitPageToLoad();
    }
}
