package com.nina.ui.pages.elements;

import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.StringUtils;

import static com.codeborne.selenide.Selenide.$x;
import static com.nina.ui.util.driver.Waiters.waitUntilElementToBeClickable;

public class Popup {
    private final SelenideElement nameInput = $x("//input[@*[contains(., 'input__input')]]");
    private final SelenideElement descriptionInput = $x("//textarea[@*[contains(., 'inputTextArea')]]");
    private final SelenideElement submitBtn;
    private final PopupType popupType;

    public Popup(PopupType popupType) {
        this.popupType = popupType;
        this.submitBtn = getSubmitBtn();
    }

    public Popup enterName(String name) {
        nameInput.setValue(name);
        return this;
    }

    public Popup enterDescription(String description) {
        descriptionInput.setValue(description);
        return this;
    }

    public Popup waitToLoad() {
        waitUntilElementToBeClickable(submitBtn);
        return this;
    }

    public void submit() {
        submitBtn.click();
    }

    private SelenideElement getSubmitBtn() {
        var btnText = StringUtils.capitalize(popupType.name().toLowerCase());
        return $x("//button[text()='%s']".formatted(btnText));
    }

    public enum PopupType {
        ADD, UPDATE, DELETE
    }
}
