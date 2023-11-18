package com.nina.ui.util.driver;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;

public class Waiters {
    private static final long DEFAULT_TIMEOUT_SECONDS = 30L;

    public static void waitPageToLoad() {
        Selenide.Wait().withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static void waitUntil(Runnable function) {
        Selenide.Wait().withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .until(webDriver -> {
                    try {
                        function.run();
                        return true;
                    } catch (Throwable e) {
                        return false;
                    }
                });
    }
}
