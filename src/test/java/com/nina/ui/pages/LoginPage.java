package com.nina.ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private final SelenideElement loginForm = $x("//form[@*[contains(., 'loginForm')]]");
    private final SelenideElement userNameInput = loginForm.$x(".//input[@name='login']");
    private final SelenideElement passwordInput = loginForm.$x(".//input[@name='password']");
    private final SelenideElement loginButton = loginForm.$x(".//button[@type='submit']");

    public SelenideElement getLoginForm() {
        return loginForm;
    }

    public SelenideElement getUserNameInput() {
        return userNameInput;
    }

    public SelenideElement getPasswordInput() {
        return passwordInput;
    }

    public SelenideElement getLoginButton() {
        return loginButton;
    }
}
