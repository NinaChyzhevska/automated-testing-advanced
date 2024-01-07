package com.nina.ui.pages;

import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebElement;

public class DashboardDetailsPage {
    private final WebBrowserDriver driver;

    public DashboardDetailsPage(WebBrowserDriver driver) {
        this.driver = driver;
    }

    public WebElement getButtonDelete() {
        return driver.findElement("//button[span[text()='Delete']]");
    }

    public WebElement getButtonEdit() {
        return driver.findElement("//button[span[text()='Edit']]");
    }

    public WebElement getButtonAddNewWidget() {
        return driver.findElement("//button[span[text()='Add new widget']]");
    }

    public WebElement getWidgetType() {
        return driver.findElement("//div[contains(@class, 'widgetTypeItem') and text()='Overall statistics']");
    }

    public WebElement getButtonNextStep() {
        return driver.findElement("//button[span[text()='Next step']]");
    }

    public WebElement getFilterOption() {
        return driver.findElement("//*[contains(@class, 'filterOptions_')]");
    }

    public WebElement getDeleteWidgetHiddenButton() {
        return driver.findElement("//div[contains(@class, 'widgetHeader__control--SQilp') and contains(@class, 'widgetHeader__mobile-hide--CFUwl')][2]");
    }

    public WebElement getEmptyWidgetsGrid() {
        return driver.findElement("//*[contains(@class, 'emptyWidgetGrid__empty-widget--')]");
    }

    public WebElement getDashboardTitle(String dashboardName) {
        return driver.findElement("//span[@title='" + dashboardName + "' and text()='" + dashboardName + "']");
    }

    public WebElement getWidgetHeader(String widgetName) {
        return driver.findElement("//div[contains(@class, 'widgetHeader__widget-name-block') and text()='" + widgetName + "']");
    }
}
