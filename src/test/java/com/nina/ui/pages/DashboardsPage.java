package com.nina.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class DashboardsPage {
    private final SelenideElement dashboardButton = $x("//div[@*[contains(., 'addDashboardButton')]]");
    private final SelenideElement dashboardSearch = $x("//input[@*[contains(., 'inputSearch')]]");

    public SelenideElement getDashboardButton() {
        return dashboardButton;
    }

    public SelenideElement getDashboardSearch() {
        return dashboardSearch;
    }
}
