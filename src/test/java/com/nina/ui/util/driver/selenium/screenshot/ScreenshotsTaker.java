package com.nina.ui.util.driver.selenium.screenshot;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenshotsTaker {

    private static WebDriver webDriver;

    public static void initDriver(WebDriver webDriver) {
        ScreenshotsTaker.webDriver = webDriver;
    }

    public static void takeScreenshot(String pathname) throws IOException {
        File src = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(src, new File(pathname));
    }
}
