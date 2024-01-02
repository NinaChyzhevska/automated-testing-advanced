package com.nina.ui.util.driver.selenium;

import com.nina.ui.util.driver.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWebElementAdapter implements WebElement {

    private final org.openqa.selenium.WebElement delegate;
    private final WebDriver internalWebDriver;
    private final WebDriverWait waiter;

    public SeleniumWebElementAdapter(org.openqa.selenium.WebElement delegate,
                                     WebDriver webDriver,
                                     WebDriverWait waiter) {
        this.delegate = delegate;
        this.waiter = waiter;
        this.internalWebDriver = webDriver;
    }

    @Override
    public void click() {
        delegate.click();
    }

    @Override
    public WebElement findElement(String xpath) {
        var element = delegate.findElement(By.xpath(xpath));
        return new SeleniumWebElementAdapter(element, internalWebDriver, waiter);
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        delegate.sendKeys(keysToSend);
    }

    @Override
    public void waitUntilElementToBeClickable() {
        waiter.until(ExpectedConditions.elementToBeClickable(delegate));
    }

    @Override
    public void waitForElementVisibility() {
        waiter.until(ExpectedConditions.visibilityOf(delegate));
    }

    @Override
    public void setValue(String text) {
        delegate.clear();
        delegate.sendKeys(text);
    }

    @Override
    public String getAttribute(String name) {
        return delegate.getAttribute(name);
    }

    @Override
    public String getText() {
        return delegate.getText();
    }

    @Override
    public Object getInternalElement() {
        return delegate;
    }

    @Override
    public void dragAndDropTo(WebElement another) {
        new Actions(internalWebDriver)
                .clickAndHold(delegate)
                .moveToElement((org.openqa.selenium.WebElement) another.getInternalElement())
                .release()
                .perform();
    }

    @Override
    public void moveTo() {
        new Actions(internalWebDriver)
                .moveToElement(delegate)
                .perform();
    }

    @Override
    public void shouldHaveAttribute(String attributeName, String attributeValue) {
        waiter.until(ExpectedConditions.attributeToBe(delegate, attributeName, attributeValue));
    }

    @Override
    public void shouldHaveText(String text) {
        waiter.until(webDriver -> delegate.getText().equalsIgnoreCase(text));
    }
}
