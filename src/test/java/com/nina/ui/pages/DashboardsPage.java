package com.nina.ui.pages;

import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebElement;

import java.util.List;

public class DashboardsPage {
    private final WebBrowserDriver driver;

    public DashboardsPage(WebBrowserDriver driver) {
        this.driver = driver;
    }

    public WebElement getDashboardPopupButton() {
        return driver.findElement("//div[@*[contains(., 'addDashboardButton')]]");
    }

    public WebElement getDashboardSearch() {
        return driver.findElement("//input[@*[contains(., 'inputSearch')]]");
    }

    public List<WebElement> getDashboardsList() {
        return driver.findElements("//*[contains(@class, 'gridRow__grid-row-wrapper')]//a");
    }
}
