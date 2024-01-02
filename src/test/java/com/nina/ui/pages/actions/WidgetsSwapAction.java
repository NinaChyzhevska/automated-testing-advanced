package com.nina.ui.pages.actions;

import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebElement;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class WidgetsSwapAction {
    private final WebElement firstWidget;
    private final WebElement secondWidget;
    private final String firstWidgetInitialPosition;
    private final String secondWidgetInitialPosition;

    public WidgetsSwapAction(WebBrowserDriver webBrowserDriver, String firstWidgetName, String secondWidgetName) {
        List<WebElement> allWidgets = webBrowserDriver.findElements("//*[contains(@class, 'react-grid-item')]");
        this.firstWidget = requireNonNull(findWidgetByName(allWidgets, firstWidgetName));
        this.secondWidget = requireNonNull(findWidgetByName(allWidgets, secondWidgetName));
        this.firstWidgetInitialPosition = requireNonNull(firstWidget.getAttribute("style"));
        this.secondWidgetInitialPosition = requireNonNull(secondWidget.getAttribute("style"));
    }

    public WidgetsSwapAction swapWidgets() {
        WebElement firstWidgetDraggablePart = firstWidget.findElement(".//*[contains(@class, 'draggable-field')]");
        WebElement secondWidgetDraggablePart = secondWidget.findElement(".//*[contains(@class, 'draggable-field')]");
        firstWidgetDraggablePart.dragAndDropTo(secondWidgetDraggablePart);
        return this;
    }

    public WidgetsSwapAction verifyThatWidgetsSwapped() {
        firstWidget.shouldHaveAttribute("style", secondWidgetInitialPosition);
        secondWidget.shouldHaveAttribute("style", firstWidgetInitialPosition);
        return this;
    }

    private WebElement findWidgetByName(List<WebElement> allWidgets, String name) {
        for (WebElement element : allWidgets) {
            WebElement header = element.findElement(".//div[contains(@class, 'widgetHeader__widget-name-block')]");
            if (name.equals(header.getText())) {
                return element;
            }
        }
        return null;
    }
}
