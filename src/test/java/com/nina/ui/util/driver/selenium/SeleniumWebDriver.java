package com.nina.ui.util.driver.selenium;

import com.nina.ui.configuration.UITestsConfig;
import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class SeleniumWebDriver implements WebBrowserDriver {

    private WebDriver webDriver;
    private WebDriverWait waiter;

    @Override
    public void initDriver() {
        String browserName = UITestsConfig.getBrowser();
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("selenium.headless", "false"));
        switch (browserName.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--window-size=" + UITestsConfig.BROWSER_SCREEN_SIZE);
                if (isHeadless) {
                    chromeOptions.addArguments("--headless");
                }
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--width=" + UITestsConfig.BROWSER_SCREEN_SIZE.split("x")[0]);
                firefoxOptions.addArguments("--height=" + UITestsConfig.BROWSER_SCREEN_SIZE.split("x")[1]);
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                }
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            case "safari":
                webDriver = new SafariDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
        var timeout = Duration.ofMillis(UITestsConfig.WAIT_TIMEOUT);
        webDriver.manage().timeouts().implicitlyWait(timeout);
        waiter = new WebDriverWait(webDriver, timeout);
    }

    @Override
    public void shutdownDriver() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Override
    public void open(String url) {
        webDriver.get(url);
    }

    @Override
    public WebElement findElement(String path) {
        var seleniumElement = webDriver.findElement(By.xpath(path));
        return new SeleniumWebElementAdapter(seleniumElement, webDriver, waiter);
    }

    @Override
    public List<WebElement> findElements(String xpath) {
        var elements = webDriver.findElements(By.xpath(xpath));
        return elements.stream()
                .map(seleniumElement -> (WebElement) new SeleniumWebElementAdapter(seleniumElement, webDriver, waiter))
                .toList();
    }

    @Override
    public Object executeScript(String script, WebElement... elements) {
        var args = Arrays.stream(elements).map(WebElement::getInternalElement).toArray();
        return ((JavascriptExecutor) webDriver).executeScript(script, args);
    }

    @Override
    public void waitPageToLoad() {
        waiter.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    @Override
    public void waitUntil(Runnable runnable) {
        waiter.until(webDriver -> {
            try {
                runnable.run();
                return true;
            } catch (Throwable e) {
                return false;
            }
        });
    }
}
