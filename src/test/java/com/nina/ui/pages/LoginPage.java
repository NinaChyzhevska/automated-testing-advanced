package com.nina.ui.pages;

import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.ui.util.driver.WebElement;

public class LoginPage {
    private final WebBrowserDriver driver;

    public LoginPage(WebBrowserDriver webDriver) {
        this.driver = webDriver;
    }

    public WebElement getLoginForm() {
        return driver.findElement("//form[@*[contains(., 'loginForm')]]");
    }

    public WebElement getUserNameInput() {
        return getLoginForm().findElement(".//input[@name='login']");
    }

    public WebElement getPasswordInput() {
        return getLoginForm().findElement(".//input[@name='password']");
    }

    public WebElement getLoginButton() {
        return getLoginForm().findElement(".//button[@type='submit']");
    }
}
