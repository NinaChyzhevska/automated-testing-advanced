package com.nina.ui.util.driver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.nina.ui.configuration.UITestsConfig;

public class DriverManager {
    public static void initDriver() {
        Configuration.timeout = UITestsConfig.WAIT_TIMEOUT;
        Configuration.browserSize = UITestsConfig.BROWSER_SCREEN_SIZE;
        Configuration.browser = UITestsConfig.getBrowser();

        var headlessProperty = System.getProperty("selenide.headless", "false");
        Configuration.headless = Boolean.parseBoolean(headlessProperty);
    }

    public static void shutDownDriver() {
        Selenide.clearBrowserCookies();
        Selenide.closeWebDriver();
    }
}
