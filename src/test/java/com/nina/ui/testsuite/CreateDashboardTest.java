package com.nina.ui.testsuite;

import com.nina.ui.steps.DashboardsSteps;
import com.nina.ui.steps.LoginSteps;
import com.nina.util.EnvironmentPropertyLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selenide.open;

public class CreateDashboardTest extends BaseTest {

    @BeforeEach
    void login() {
        open(EnvironmentPropertyLoader.getProperty("hostUrl"));
        new LoginSteps().login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"demo", "1333"})
    public void searchDashboard(String searchValue) {
        new DashboardsSteps()
                .switchToDefaultProject()
                .searchDashboard(searchValue)
                .assertDashboardsSize(1)
                .assertDashboardsListContains(searchValue);
    }
}
