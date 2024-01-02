package com.nina.ui.util.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoggingWebDriver implements WebBrowserDriver {
    private static final Logger logger = LoggerFactory.getLogger(LoggingWebDriver.class);

    private final WebBrowserDriver delegate;

    public LoggingWebDriver(WebBrowserDriver delegate) {
        this.delegate = delegate;
    }

    @Override
    public void initDriver() {
        logger.info("Driver initialization");
        delegate.initDriver();
    }

    @Override
    public void shutdownDriver() {
        logger.info("Browser shut down");
        delegate.shutdownDriver();
    }

    @Override
    public void open(String url) {
        logger.info("Opening url: " + url);
        delegate.open(url);
    }

    @Override
    public WebElement findElement(String path) {
        logger.trace("Looking for element by xpath: " + path);
        return delegate.findElement(path);
    }

    @Override
    public List<WebElement> findElements(String xpath) {
        logger.trace("Looking for elements by xpath: " + xpath);
        return delegate.findElements(xpath);
    }

    @Override
    public Object executeScript(String script, WebElement... elements) {
        logger.trace("Executing JS script: " + script);
        return delegate.executeScript(script, elements);
    }

    @Override
    public void waitPageToLoad() {
        logger.trace("Waiting page to be loaded");
        delegate.waitPageToLoad();
    }

    @Override
    public void waitUntil(Runnable runnable) {
        logger.trace("Waiting for runnable to execute");
        delegate.waitUntil(runnable);
    }
}
