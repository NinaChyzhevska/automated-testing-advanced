package com.nina.ui.pages.elements;

import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebElement;
import org.apache.commons.lang3.StringUtils;

public class Popup {
    private final WebBrowserDriver driver;
    private final PopupType popupType;

    public Popup(WebBrowserDriver driver, PopupType popupType) {
        this.driver = driver;
        this.popupType = popupType;
    }

    public Popup enterName(String name) {
        var nameInput = getNameInput();
        nameInput.waitForElementVisibility();
        nameInput.setValue(name);
        return this;
    }

    public Popup enterDescription(String description) {
        var descriptionInput = getDescriptionInput();
        descriptionInput.waitForElementVisibility();
        descriptionInput.setValue(description);
        return this;
    }

    public Popup waitToLoad() {
        getModalLayout().waitForElementVisibility();
        return this;
    }

    public void submit() {
        var submitBtn = getSubmitBtn();
        submitBtn.waitUntilElementToBeClickable();
        submitBtn.click();
    }

    private WebElement getSubmitBtn() {
        String btnText = StringUtils.capitalize(popupType.name().toLowerCase());
        return driver.findElement(".//button[text()='" + btnText + "']");
    }

    private WebElement getNameInput() {
        return driver.findElement("//input[@*[contains(., 'input__input')]]");
    }

    private WebElement getDescriptionInput() {
        return driver.findElement("//textarea[@*[contains(., 'inputTextArea')]]");
    }

    private WebElement getModalLayout() {
        return driver.findElement(".//div[@*[contains(., 'modalLayout__modal-layout')]]");
    }

    public enum PopupType {
        ADD, UPDATE, DELETE
    }
}
