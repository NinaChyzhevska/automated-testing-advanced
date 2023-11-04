package com.nina.rest.testsuite.testng;

import com.nina.util.TestDataProvider;
import org.testng.annotations.DataProvider;


public class TestNgDataProviderAdapter {

    @DataProvider(name = "dashboardDataForSearchTest")
    public static Object[][] getDashboardDataForSearchTest() {
        return TestDataProvider.getDashboardDataForSearchTest();
    }

    @DataProvider(name = "dashboardDataForPositiveTest")
    public static Object[][] getDashboardDataForPositiveTest() {
        return TestDataProvider.getDashboardDataForPositiveTest();
    }

    @DataProvider(name = "dashboardDataForNegativeTest")
    public static Object[][] getDashboardDataForNegativeTest() {
        return TestDataProvider.getDashboardDataForNegativeTest();
    }
}
