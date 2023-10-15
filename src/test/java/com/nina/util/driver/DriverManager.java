package com.nina.util.driver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.nina.configuration.UITestsConfig;

public class DriverManager {
    public static void initDriver() {
        Configuration.timeout = UITestsConfig.WAIT_TIMEOUT;
        Configuration.browserSize = UITestsConfig.BROWSER_SCREEN_SIZE;
        Configuration.browser = UITestsConfig.getBrowser();
    }

    public static void shutDownDriver() {
        Selenide.clearBrowserCookies();
        Selenide.closeWebDriver();
    }
}
