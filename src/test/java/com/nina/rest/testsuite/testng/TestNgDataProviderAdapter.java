package com.nina.rest.testsuite.testng;

import com.nina.util.TestDataProvider;
import org.testng.annotations.DataProvider;


public class TestNgDataProviderAdapter {

    @DataProvider(name = "dashboardDataForSearchTest", parallel = true)
    public static Object[][] getDashboardDataForSearchTest() {
        return TestDataProvider.getDashboardDataForSearchTest();
    }

    @DataProvider(name = "dashboardDataForPositiveTest", parallel = true)
    public static Object[][] getDashboardDataForPositiveTest() {
        return TestDataProvider.getDashboardDataForPositiveTest();
    }

    @DataProvider(name = "dashboardDataForNegativeTest", parallel = true)
    public static Object[][] getDashboardDataForNegativeTest() {
        return TestDataProvider.getDashboardDataForNegativeTest();
    }
}
