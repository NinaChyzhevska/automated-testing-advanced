package com.nina.ui.testsuite.bdd.steps;

import com.nina.ui.steps.DashboardsSteps;
import com.nina.ui.steps.LoginSteps;
import com.nina.ui.util.driver.DriverManager;
import com.nina.util.EnvironmentPropertyLoader;
import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.open;

public class DashboardSteps {

    private static final Logger logger = LoggerFactory.getLogger(DashboardSteps.class);
    private static final String TEST_DASHBOARD_NAME = "demo";
    private final DashboardsSteps dashboardsSteps = new DashboardsSteps();

    @BeforeAll
    public static void init() {
        logger.info("Driver initialization");
        DriverManager.initDriver();
    }

    @AfterAll
    public static void shutDown() {
        logger.info("Browser shut down");
        DriverManager.shutDownDriver();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        logger.info("Starting scenario execution: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        logger.info("Finishing scenario execution: " + scenario.getName());
    }

    @BeforeStep
    public void beforeStep() {
        logger.info("[Example] This block of code is executed before step");
    }

    @AfterStep
    public void afterStep() {
        logger.info("[Example] This block of code is executed after step");
    }

    @When("I search the dashboard by name")
    public void searchTheDashboardByName() {
        dashboardsSteps.searchDashboard(TEST_DASHBOARD_NAME);
    }

    @And("I switch to dashboard page")
    public void switchToDashboardPage() {
        dashboardsSteps.switchToDefaultProject();
    }

    @Given("I am logged in to the report portal in the UI")
    public void loginToReportPortal() {
        open(EnvironmentPropertyLoader.getProperty("hostUrl"));
        new LoginSteps().login(EnvironmentPropertyLoader.getProperty("userName"),
                EnvironmentPropertyLoader.getProperty("userPassword"));
    }

    @Then("I can see that the result list contains name of my dashboard")
    public void verifyThatListContainsExpectedDashboard() {
        dashboardsSteps.assertDashboardsListContains(TEST_DASHBOARD_NAME);
    }

    @And("the row result is one")
    public void verifySingleDashboard() {
        dashboardsSteps.assertDashboardsSize(1);
    }
}
