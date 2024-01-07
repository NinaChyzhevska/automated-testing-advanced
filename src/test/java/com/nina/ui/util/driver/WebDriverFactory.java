package com.nina.ui.util.driver;

import com.nina.ui.util.driver.selenide.SelenideWebDriver;
import com.nina.ui.util.driver.selenium.SeleniumWebDriver;
import com.nina.util.EnvironmentPropertyLoader;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    private static final WebBrowserDriver WEB_DRIVER = resolveWebDriver();

    public static WebBrowserDriver getWebDriver() {
        return WEB_DRIVER;
    }

    private static WebBrowserDriver resolveWebDriver() {
        var selectedFramework = ObjectUtils.defaultIfNull(EnvironmentPropertyLoader.getProperty("testingFramework"),
                "selenide");
        logger.info("Using %s web framework".formatted(selectedFramework));
        var webDriver = resolveWebDriver(selectedFramework);
        return new LoggingWebDriver(webDriver);
    }

    private static WebBrowserDriver resolveWebDriver(String testingFramework) {
        return switch (testingFramework) {
            case "selenide" -> new SelenideWebDriver();
            case "selenium" -> new SeleniumWebDriver();
            default -> throw new IllegalArgumentException("Unsupported testing framework: " + testingFramework);
        };
    }
}
