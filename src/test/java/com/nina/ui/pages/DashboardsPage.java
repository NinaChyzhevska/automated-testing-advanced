package com.nina.ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardsPage {
    private final SelenideElement dashboardPopupButton = $x("//div[@*[contains(., 'addDashboardButton')]]");
    private final SelenideElement dashboardSearch = $x("//input[@*[contains(., 'inputSearch')]]");
    private final ElementsCollection dashboardsList = $$(By.xpath("//*[contains(@class, 'gridRow__grid-row-wrapper')]//a"));

    public SelenideElement getDashboardPopupButton() {
        return dashboardPopupButton;
    }

    public SelenideElement getDashboardSearch() {
        return dashboardSearch;
    }

    public ElementsCollection getDashboardsList() {
        return dashboardsList;
    }
}
