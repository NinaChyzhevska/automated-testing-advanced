package com.nina.ui.steps;

import com.nina.ui.pages.LoginPage;
import com.nina.ui.util.driver.WebBrowserDriver;
import com.nina.util.EnvironmentPropertyLoader;

public class LoginSteps {

    private final LoginPage loginPage;
    private final WebBrowserDriver webDriver;

    public LoginSteps(WebBrowserDriver webDriver) {
        this.loginPage = new LoginPage(webDriver);
        this.webDriver = webDriver;
        webDriver.open(EnvironmentPropertyLoader.getProperty("hostUrl"));
    }

    public void login(String userName, String password) {
        loginPage.getUserNameInput().sendKeys(userName);
        loginPage.getPasswordInput().sendKeys(password);
        loginPage.getLoginButton().click();
        webDriver.waitPageToLoad();
    }
}
