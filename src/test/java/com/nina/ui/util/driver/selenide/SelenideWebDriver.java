package com.nina.ui.util.driver.selenide;

import com.codeborne.selenide.*;
import com.nina.ui.configuration.UITestsConfig;
import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class SelenideWebDriver implements WebBrowserDriver {
    @Override
    public void initDriver() {
        Configuration.timeout = UITestsConfig.WAIT_TIMEOUT;
        Configuration.browserSize = UITestsConfig.BROWSER_SCREEN_SIZE;
        Configuration.browser = UITestsConfig.getBrowser();

        var headlessProperty = System.getProperty("selenide.headless", "false");
        Configuration.headless = Boolean.parseBoolean(headlessProperty);
    }

    @Override
    public void shutdownDriver() {
        Selenide.clearBrowserCookies();
        Selenide.closeWebDriver();
    }

    @Override
    public void open(String url) {
        Selenide.open(url);
    }

    @Override
    public WebElement findElement(String path) {
        var selenideElement = $x(path);
        return new SelenideWebElementAdapter(selenideElement);
    }

    @Override
    public List<WebElement> findElements(String xpath) {
        ElementsCollection elements = $$x(xpath);
        var webElements = new ArrayList<WebElement>();
        for (SelenideElement element : elements) {
            webElements.add(new SelenideWebElementAdapter(element));
        }
        return webElements;
    }

    @Override
    public Object executeScript(String script, WebElement... elements) {
        var args = Arrays.stream(elements).map(WebElement::getInternalElement).toArray();
        return ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript(script, args);
    }

    @Override
    public void waitPageToLoad() {
        Wait().until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    @Override
    public void waitUntil(Runnable runnable, Duration timeout) {
        Wait().withTimeout(timeout).until(webDriver -> {
            try {
                runnable.run();
                return true;
            } catch (Throwable e) {
                return false;
            }
        });
    }
}
