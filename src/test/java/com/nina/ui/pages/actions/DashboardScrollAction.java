package com.nina.ui.pages.actions;

import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebElement;

public class DashboardScrollAction {
    private final WebBrowserDriver webBrowserDriver;
    private final WebElement dashboardElement;

    public DashboardScrollAction(WebBrowserDriver webBrowserDriver, String dashboardName) {
        this.webBrowserDriver = webBrowserDriver;
        this.dashboardElement = getDashboardElement(dashboardName);
    }

    public DashboardScrollAction scrollToElement() {
        webBrowserDriver.executeScript("arguments[0].scrollIntoView(true);", dashboardElement);
        return this;
    }

    public DashboardScrollAction verifyElementIsVisible() {
        dashboardElement.waitForElementVisibility();
        return this;
    }

    public void clickElement() {
        webBrowserDriver.executeScript("arguments[0].click();", dashboardElement);
    }

    private WebElement getDashboardElement(String dashboardName) {
        return webBrowserDriver.findElement("//a[contains(@class, 'dashboardTable') and contains(text(), '"
                + dashboardName + "')]");
    }
}
