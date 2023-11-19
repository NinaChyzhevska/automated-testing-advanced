package com.nina.rest.testsuite.bdd.steps;

import com.nina.rest.client.AuthClient;
import com.nina.rest.client.DashboardClient;
import com.nina.rest.model.UserSession;
import com.nina.rest.testsuite.bdd.context.DashboardContext;
import com.nina.util.EnvironmentPropertyLoader;
import io.cucumber.java.en.Given;

public class LoginStep {
    private final AuthClient authClient;
    private final DashboardContext context;

    public LoginStep(AuthClient authClient, DashboardContext context) {
        this.authClient = authClient;
        this.context = context;
    }

    @Given("I am logged in to the report portal")
    public void loginIntoReportPortal() {
        UserSession userSession = authClient.login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
        context.setDashboardClient(new DashboardClient(userSession));
    }
}
