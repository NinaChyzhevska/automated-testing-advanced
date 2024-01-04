package com.nina.ui.util.driver;

import java.time.Duration;
import java.util.List;

public interface WebBrowserDriver {
    void initDriver();

    void shutdownDriver();

    void open(String url);

    WebElement findElement(String path);

    List<WebElement> findElements(String xpath);

    Object executeScript(String script, WebElement... elements);

    void waitPageToLoad();

    void waitUntil(Runnable runnable, Duration timeout);
}
