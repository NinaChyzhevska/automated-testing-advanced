package com.nina.configuration;

public class UITestsConfig {
    public static final long WAIT_TIMEOUT = 15000L;
    public static final String BROWSER_SCREEN_SIZE = "1920x1080";

    public static String getBrowser() {
        return System.getProperty("browser", "chrome");
    }
}
