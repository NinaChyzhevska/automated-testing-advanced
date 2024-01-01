package com.nina.ui.steps;

import com.nina.ui.pages.LoginPage;
import com.nina.util.EnvironmentPropertyLoader;

import static com.codeborne.selenide.Selenide.open;
import static com.nina.ui.util.driver.Waiters.waitPageToLoad;

public class LoginSteps {

    private final LoginPage loginPage = new LoginPage();

    public LoginSteps() {
        open(EnvironmentPropertyLoader.getProperty("hostUrl"));
    }

    public void login(String userName, String password) {
        loginPage.getUserNameInput().sendKeys(userName);
        loginPage.getPasswordInput().sendKeys(password);
        loginPage.getLoginButton().click();
        waitPageToLoad();
    }
}
