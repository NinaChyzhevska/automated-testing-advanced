package com.nina.testsuite;

import com.nina.util.driver.DriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

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
