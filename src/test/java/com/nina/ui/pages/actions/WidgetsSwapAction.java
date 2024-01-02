package com.nina.ui.pages.actions;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.interactions.Actions;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$$;
import static java.util.Objects.requireNonNull;

public class WidgetsSwapAction {

    private final SelenideElement firstWidget;
    private final SelenideElement secondWidget;
    private final String firstWidgetInitialPosition;
    private final String secondWidgetInitialPosition;

    public WidgetsSwapAction(String firstWidgetName, String secondWidgetName) {
        ElementsCollection allWidgets = $$(".react-grid-item");
        this.firstWidget = requireNonNull(findWidgetByName(allWidgets, firstWidgetName));
        this.secondWidget = requireNonNull(findWidgetByName(allWidgets, secondWidgetName));
        this.firstWidgetInitialPosition = requireNonNull(firstWidget.getAttribute("style"));
        this.secondWidgetInitialPosition = requireNonNull(secondWidget.getAttribute("style"));
    }

    public WidgetsSwapAction swapWidgets() {
        SelenideElement firstWidgetDraggablePart = firstWidget.$(".draggable-field");
        SelenideElement secondWidgetDraggablePart = secondWidget.$(".draggable-field");
        new Actions(WebDriverRunner.getWebDriver())
                .clickAndHold(firstWidgetDraggablePart)
                .moveToElement(secondWidgetDraggablePart)
                .release()
                .perform();
        return this;
    }

    public WidgetsSwapAction verifyThatWidgetsSwapped() {
        firstWidget.shouldHave(attribute("style", secondWidgetInitialPosition));
        secondWidget.shouldHave(attribute("style", firstWidgetInitialPosition));
        return this;
    }

    private SelenideElement findWidgetByName(ElementsCollection allWidgets, String name) {
        for (var element : allWidgets) {
            var header = element.$x(".//div[contains(@class, 'widgetHeader__widget-name-block')]");
            if (name.equals(header.text())) {
                return element;
            }
        }
        return null;
    }
}
