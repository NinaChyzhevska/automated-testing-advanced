package com.nina.ui.testsuite;

import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebDriverFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static WebBrowserDriver webDriver;

    @BeforeAll
    static void init() {
        webDriver = WebDriverFactory.getWebDriver();
        webDriver.initDriver();
    }

    @AfterAll
    static void shutDown() {
        if (webDriver != null) {
            webDriver.shutdownDriver();
        }
    }
}
