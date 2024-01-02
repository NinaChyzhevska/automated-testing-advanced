package com.nina.ui.util.driver.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.nina.ui.util.driver.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.Wait;

public class SelenideWebElementAdapter implements WebElement {
    private final SelenideElement delegate;

    public SelenideWebElementAdapter(SelenideElement delegate) {
        this.delegate = delegate;
    }

    @Override
    public void click() {
        delegate.click();
    }

    @Override
    public WebElement findElement(String xpath) {
        var webElement = delegate.$x(xpath);
        return new SelenideWebElementAdapter(webElement);
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
        delegate.sendKeys(keysToSend);
    }

    @Override
    public void waitUntilElementToBeClickable() {
        Wait().until(ExpectedConditions.elementToBeClickable(delegate.toWebElement()));
    }

    @Override
    public void waitForElementVisibility() {
        delegate.shouldBe(Condition.visible);
    }

    @Override
    public void setValue(String text) {
        delegate.setValue(text);
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
        new Actions(WebDriverRunner.getWebDriver())
                .clickAndHold(delegate)
                .moveToElement((SelenideElement) another.getInternalElement())
                .release()
                .perform();
    }

    @Override
    public void moveTo() {
        new Actions(WebDriverRunner.getWebDriver())
                .moveToElement(delegate)
                .perform();
    }

    @Override
    public void shouldHaveAttribute(String attributeName, String attributeValue) {
        delegate.shouldHave(Condition.attribute(attributeName, attributeValue));
    }

    @Override
    public void shouldHaveText(String text) {
        delegate.shouldHave(Condition.text(text));
    }
}
