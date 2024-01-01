package com.nina.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class DashboardDetailsPage {
    private final SelenideElement buttonDelete = $x("//button[span[text()='Delete']]");
    private final SelenideElement buttonEdit = $x("//button[span[text()='Edit']]");
    private final SelenideElement buttonAddNewWidget = $x("//button[span[text()='Add new widget']]");
    private final SelenideElement widgetType = $x("//div[contains(@class, 'widgetTypeItem') and text()='Overall statistics']");
    private final SelenideElement buttonNextStep = $x("//button[span[text()='Next step']]");
    private final SelenideElement filterOption = $x("//*[contains(@class, 'filterOptions_')]");
    private final SelenideElement deleteWidgetHiddenButton = $x("//div[contains(@class, 'widgetHeader__control--SQilp') and contains(@class, 'widgetHeader__mobile-hide--CFUwl')][2]");
    private final SelenideElement emptyWidgetsGrid = $x("//*[contains(@class, 'emptyWidgetGrid__empty-widget--')]");

    public SelenideElement getButtonDelete() {
        return buttonDelete;
    }

    public SelenideElement getButtonEdit() {
        return buttonEdit;
    }

    public SelenideElement getButtonAddNewWidget() {
        return buttonAddNewWidget;
    }

    public SelenideElement getWidgetType() {
        return widgetType;
    }

    public SelenideElement getButtonNextStep() {
        return buttonNextStep;
    }

    public SelenideElement getFilterOption() {
        return filterOption;
    }

    public SelenideElement getDeleteWidgetHiddenButton() {
        return deleteWidgetHiddenButton;
    }

    public SelenideElement getEmptyWidgetsGrid() {
        return emptyWidgetsGrid;
    }

    public SelenideElement getDashboardTitle(String dashboardName) {
        return $x("//span[@title='%s' and text()='%s']".formatted(dashboardName, dashboardName));
    }

    public SelenideElement getWidgetHeader(String widgetName) {
        return $x("//div[contains(@class, 'widgetHeader__widget-name-block') and text()='%s']".formatted(widgetName));
    }
}
