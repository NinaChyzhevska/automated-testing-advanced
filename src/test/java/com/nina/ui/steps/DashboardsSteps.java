package com.nina.ui.steps;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.nina.ui.pages.DashboardDetailsPage;
import com.nina.ui.pages.DashboardsPage;
import com.nina.ui.pages.actions.DashboardScrollAction;
import com.nina.ui.pages.actions.WidgetsSwapAction;
import com.nina.ui.pages.elements.Popup;
import com.nina.ui.pages.elements.Popup.PopupType;
import com.nina.util.EnvironmentPropertyLoader;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.text;
import static com.nina.ui.util.driver.Waiters.*;

public class DashboardsSteps {

    private static final String DEFAULT_PROJECT_NAME = "vinni_personal";

    private final DashboardsPage dashboardsPage = new DashboardsPage();
    private final DashboardDetailsPage dashboardDetailsPage = new DashboardDetailsPage();
    private WidgetsSwapAction widgetsSwapAction;

    public DashboardsSteps createDashboard(String dashboardName, String dashboardDescription) {
        dashboardsPage.getDashboardPopupButton().click();
        new Popup(PopupType.ADD)
                .waitToLoad()
                .enterName(dashboardName)
                .enterDescription(dashboardDescription)
                .submit();
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

    public DashboardsSteps assertDashboardContainsName(String dashboardName) {
        dashboardDetailsPage.getDashboardTitle(dashboardName).shouldHave(text(dashboardName));
        return this;
    }

    public DashboardsSteps deleteDashboard() {
        dashboardDetailsPage.getButtonDelete().click();
        new Popup(PopupType.DELETE).waitToLoad().submit();
        waitPageToLoad();
        return this;
    }

    public DashboardsSteps updateDashboardNameAndDescription(String updatedDashboardName, String updatedDashboardDescription) {
        dashboardDetailsPage.getButtonEdit().click();
        new Popup(PopupType.UPDATE)
                .waitToLoad()
                .enterName(updatedDashboardName)
                .enterDescription(updatedDashboardDescription)
                .submit();
        waitForElementVisibility(dashboardDetailsPage.getDashboardTitle(updatedDashboardName));
        return this;
    }

    public DashboardsSteps addWidgetToDashboard(String widgetName) {
        dashboardDetailsPage.getButtonAddNewWidget().click();
        waitForElementVisibility(dashboardDetailsPage.getWidgetType());
        dashboardDetailsPage.getWidgetType().click();
        dashboardDetailsPage.getButtonNextStep().click();
        waitForElementVisibility(dashboardDetailsPage.getFilterOption());
        dashboardDetailsPage.getFilterOption().click();
        dashboardDetailsPage.getButtonNextStep().click();
        new Popup(PopupType.ADD)
                .waitToLoad()
                .enterName(widgetName)
                .submit();
        waitForElementVisibility(dashboardDetailsPage.getWidgetHeader(widgetName));
        return this;
    }

    public DashboardsSteps removeWidgetFromDashboard(String widgetName) {
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        actions.moveToElement(dashboardDetailsPage.getWidgetHeader(widgetName)).perform();
        dashboardDetailsPage.getDeleteWidgetHiddenButton().click();
        new Popup(PopupType.DELETE).waitToLoad().submit();
        waitUntilElementToBeClickable(dashboardDetailsPage.getButtonAddNewWidget());
        return this;
    }

    public DashboardsSteps assertWidgetIsPresentOnDashboard(String widgetName) {
        dashboardDetailsPage.getWidgetHeader(widgetName).shouldHave(text(widgetName));
        return this;
    }

    public void assertThatThereIsNoWidgets() {
        waitForElementVisibility(dashboardDetailsPage.getEmptyWidgetsGrid());
    }

    public DashboardsSteps changeWidgetsOrder(String firstWidget, String secondWidget) {
        waitForElementVisibility(dashboardDetailsPage.getWidgetHeader(firstWidget));
        waitForElementVisibility(dashboardDetailsPage.getWidgetHeader(secondWidget));
        this.widgetsSwapAction = new WidgetsSwapAction(firstWidget, secondWidget);
        widgetsSwapAction.swapWidgets();
        return this;
    }

    public DashboardsSteps verifyThatWidgetsReordered() {
        widgetsSwapAction.verifyThatWidgetsSwapped();
        return this;
    }

    public DashboardsSteps selectDashboard(String dashboardName) {
        new DashboardScrollAction(dashboardName).scrollToElement().verifyElementIsVisible().clickElement();
        return this;
    }
}
