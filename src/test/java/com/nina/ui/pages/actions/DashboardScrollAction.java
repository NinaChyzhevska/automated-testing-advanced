package com.nina.ui.pages.actions;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.JavascriptExecutor;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardScrollAction {
    private final SelenideElement dashboardElement;

    public DashboardScrollAction(String dashboardName) {
        this.dashboardElement = getDashboardElement(dashboardName);
    }

    public DashboardScrollAction scrollToElement() {
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].scrollIntoView(true);",
                dashboardElement);
        return this;
    }

    public DashboardScrollAction verifyElementIsVisible() {
        dashboardElement.shouldBe(visible);
        return this;
    }

    public void clickElement() {
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].click();", dashboardElement);
    }

    private SelenideElement getDashboardElement(String dashboardName) {
        return $x("//a[contains(@class, 'dashboardTable') and contains(text(), '%s')]".formatted(dashboardName));
    }
}
