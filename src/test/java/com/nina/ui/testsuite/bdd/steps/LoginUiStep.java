package com.nina.ui.testsuite.bdd.steps;

import com.nina.ui.steps.LoginSteps;
import com.nina.ui.util.driver.WebDriverFactory;
import com.nina.util.EnvironmentPropertyLoader;
import io.cucumber.java.en.Given;

import static com.codeborne.selenide.Selenide.open;

public class LoginUiStep {

    private final LoginSteps loginSteps;

    public LoginUiStep() {
        this.loginSteps = new LoginSteps(WebDriverFactory.getWebDriver());
    }

    @Given("I am logged in to the report portal in the UI")
    public void loginToReportPortal() {
        open(EnvironmentPropertyLoader.getProperty("hostUrl"));
        loginSteps.login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
    }
}
