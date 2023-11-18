package com.nina.ui.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import com.nina.ui.pages.DashboardsPage;
import com.nina.util.EnvironmentPropertyLoader;

import static com.nina.ui.util.driver.Waiters.waitPageToLoad;

public class DashboardsSteps {

    private static final String DEFAULT_PROJECT_NAME = "vinni_personal";

    private final DashboardsPage dashboardsPage = new DashboardsPage();

    public DashboardsSteps createDashboard() {
        dashboardsPage.getDashboardButton().click();
        waitPageToLoad();
        return this;
    }

    public DashboardsSteps switchToDefaultProject() {
        var url = String.format("%s/ui/#%s/dashboard", EnvironmentPropertyLoader.getProperty("hostUrl"),
                DEFAULT_PROJECT_NAME);
        Selenide.open(url);
        waitPageToLoad();
        return this;
    }

    public DashboardsSteps searchDashboard(String searchValue) {
        dashboardsPage.getDashboardSearch().sendKeys(searchValue);
        waitPageToLoad();
        return this;
    }

    public DashboardsSteps assertDashboardsSize(int expectedSize) {
        dashboardsPage.getDashboardsList().shouldHave(CollectionCondition.size(expectedSize));
        return this;
    }

    public DashboardsSteps assertDashboardsListContains(String dashboardName) {
        dashboardsPage.getDashboardsList().shouldHave(CollectionCondition.texts(dashboardName));
        return this;
    }
}
