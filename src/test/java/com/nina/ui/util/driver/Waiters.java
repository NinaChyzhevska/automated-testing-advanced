package com.nina.ui.util.driver;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class Waiters {
    public static void waitPageToLoad() {
        Selenide.Wait().withTimeout(Duration.ofSeconds(30)).until(webDriver -> ((JavascriptExecutor) webDriver))
                .executeScript("return document.readyState").equals("complete");
    }
}
