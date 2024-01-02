package com.nina.ui.steps;

import com.nina.ui.pages.DashboardDetailsPage;
import com.nina.ui.pages.DashboardsPage;
import com.nina.ui.pages.actions.DashboardScrollAction;
import com.nina.ui.pages.actions.WidgetsSwapAction;
import com.nina.ui.pages.elements.Popup;
import com.nina.ui.pages.elements.Popup.PopupType;
import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebElement;
import com.nina.util.EnvironmentPropertyLoader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DashboardsSteps {

    private static final String DEFAULT_PROJECT_NAME = "vinni_personal";

    private final WebBrowserDriver webDriver;
    private final DashboardsPage dashboardsPage;
    private final DashboardDetailsPage dashboardDetailsPage;
    private WidgetsSwapAction widgetsSwapAction;

    public DashboardsSteps(WebBrowserDriver webDriver) {
        this.webDriver = webDriver;
        this.dashboardsPage = new DashboardsPage(webDriver);
        this.dashboardDetailsPage = new DashboardDetailsPage(webDriver);
    }

    public DashboardsSteps createDashboard(String dashboardName, String dashboardDescription) {
        dashboardsPage.getDashboardPopupButton().click();
        new Popup(webDriver, PopupType.ADD)
                .waitToLoad()
                .enterName(dashboardName)
                .enterDescription(dashboardDescription)
                .submit();
        webDriver.waitPageToLoad();
        return this;
    }

    public DashboardsSteps switchToDefaultProject() {
        var url = String.format("%s/ui/#%s/dashboard", EnvironmentPropertyLoader.getProperty("hostUrl"),
                DEFAULT_PROJECT_NAME);
        webDriver.open(url);
        webDriver.waitPageToLoad();
        return this;
    }

    public DashboardsSteps searchDashboard(String searchValue) {
        dashboardsPage.getDashboardSearch().sendKeys(searchValue);
        webDriver.waitPageToLoad();
        return this;
    }

    public DashboardsSteps assertDashboardsSize(int expectedSize) {
        webDriver.waitUntil(() -> assertThat(dashboardsPage.getDashboardsList(), hasSize(expectedSize)));
        return this;
    }

    public DashboardsSteps assertDashboardsListContains(String dashboardName) {
        webDriver.waitUntil(() -> {
            boolean containsDashboard = dashboardsPage.getDashboardsList().stream().map(WebElement::getText)
                    .anyMatch(text -> text.toLowerCase().contains(dashboardName));
            assertThat(containsDashboard, is(true));
        });
        return this;
    }

    public DashboardsSteps assertDashboardContainsName(String dashboardName) {
        dashboardDetailsPage.getDashboardTitle(dashboardName).shouldHaveText(dashboardName);
        return this;
    }

    public DashboardsSteps deleteDashboard() {
        dashboardDetailsPage.getButtonDelete().click();
        new Popup(webDriver, PopupType.DELETE).waitToLoad().submit();
        webDriver.waitPageToLoad();
        return this;
    }

    public DashboardsSteps updateDashboardNameAndDescription(String updatedDashboardName, String updatedDashboardDescription) {
        dashboardDetailsPage.getButtonEdit().click();
        new Popup(webDriver, PopupType.UPDATE)
                .waitToLoad()
                .enterName(updatedDashboardName)
                .enterDescription(updatedDashboardDescription)
                .submit();
        dashboardDetailsPage.getDashboardTitle(updatedDashboardName).waitForElementVisibility();
        return this;
    }

    public DashboardsSteps addWidgetToDashboard(String widgetName) {
        dashboardDetailsPage.getButtonAddNewWidget().click();
        dashboardDetailsPage.getWidgetType().waitForElementVisibility();
        dashboardDetailsPage.getWidgetType().click();
        dashboardDetailsPage.getButtonNextStep().click();
        dashboardDetailsPage.getFilterOption().waitForElementVisibility();
        dashboardDetailsPage.getFilterOption().click();
        dashboardDetailsPage.getButtonNextStep().click();
        new Popup(webDriver, PopupType.ADD)
                .enterName(widgetName)
                .submit();
        dashboardDetailsPage.getWidgetHeader(widgetName).waitForElementVisibility();
        return this;
    }

    public DashboardsSteps removeWidgetFromDashboard(String widgetName) {
        WebElement widgetHeader = dashboardDetailsPage.getWidgetHeader(widgetName);
        widgetHeader.moveTo();
        dashboardDetailsPage.getDeleteWidgetHiddenButton().click();
        new Popup(webDriver, PopupType.DELETE).submit();
        dashboardDetailsPage.getButtonAddNewWidget().waitUntilElementToBeClickable();
        return this;
    }

    public DashboardsSteps assertWidgetIsPresentOnDashboard(String widgetName) {
        dashboardDetailsPage.getWidgetHeader(widgetName).shouldHaveText(widgetName);
        return this;
    }

    public void assertThatThereIsNoWidgets() {
        dashboardDetailsPage.getEmptyWidgetsGrid().waitForElementVisibility();
    }

    public DashboardsSteps changeWidgetsOrder(String firstWidget, String secondWidget) {
        dashboardDetailsPage.getWidgetHeader(firstWidget).waitForElementVisibility();
        dashboardDetailsPage.getWidgetHeader(secondWidget).waitForElementVisibility();
        this.widgetsSwapAction = new WidgetsSwapAction(webDriver, firstWidget, secondWidget);
        widgetsSwapAction.swapWidgets();
        return this;
    }

    public DashboardsSteps verifyThatWidgetsReordered() {
        widgetsSwapAction.verifyThatWidgetsSwapped();
        return this;
    }

    public DashboardsSteps selectDashboard(String dashboardName) {
        new DashboardScrollAction(webDriver, dashboardName)
                .scrollToElement()
                .verifyElementIsVisible()
                .clickElement();
        return this;
    }
}
