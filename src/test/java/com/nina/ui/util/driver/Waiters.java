package com.nina.ui.util.driver;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.Wait;

public class Waiters {
    private static final long DEFAULT_TIMEOUT_SECONDS = 30L;

    public static void waitPageToLoad() {
        Wait().withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static void waitUntilElementToBeClickable(SelenideElement element) {
        Wait().until(ExpectedConditions.elementToBeClickable(element.toWebElement()));
    }

    public static void waitForElementVisibility(SelenideElement element) {
        element.shouldBe(Condition.visible);
    }
}
