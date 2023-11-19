package com.nina.ui.testsuite.bdd.steps;

import com.nina.ui.util.driver.DriverManager;
import io.cucumber.java.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UiHooks {
    private static final Logger logger = LoggerFactory.getLogger(DashboardUiSteps.class);

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
}
