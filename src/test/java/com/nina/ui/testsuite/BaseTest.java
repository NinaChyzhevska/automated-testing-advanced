package com.nina.ui.testsuite;

import com.epam.reportportal.junit5.ReportPortalExtension;
import com.nina.ui.util.driver.DriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ReportPortalExtension.class)
public abstract class BaseTest {
    @BeforeAll
    static void init() {
        DriverManager.initDriver();
    }

    @AfterAll
    static void shutDown() {
        DriverManager.shutDownDriver();
    }
}
