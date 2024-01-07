package com.nina.ui.util.driver;

public interface WebElement {
    void click();

    WebElement findElement(String xpath);

    void sendKeys(CharSequence... keysToSend);

    void waitUntilElementToBeClickable();

    void waitForElementVisibility();

    void setValue(String text);

    String getAttribute(String name);

    String getText();

    Object getInternalElement();

    void dragAndDropTo(WebElement another);
    void moveTo();

    void shouldHaveAttribute(String attributeName, String attributeValue);
    void shouldHaveText(String text);
}
