package com.nina.testsuite.example;

import com.nina.pages.example.GooglePage;
import com.nina.pages.example.SearchResultsPage;
import com.nina.testsuite.BaseTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;

public class GoogleTest extends BaseTest {

    private final Logger logger = LoggerFactory.getLogger(GoogleTest.class);

    @Test
    public void userCanSearch() {
        logger.info("This is a test method to verify that Selenide integration works.");
        logger.warn("This test will be removed soon");

        open("https://duckduckgo.com");
        new GooglePage().searchFor("selenide java");

        SearchResultsPage results = new SearchResultsPage();
        results.getResults().shouldHave(sizeGreaterThan(1));
        results.getResult(0).shouldHave(text("Selenide: concise UI tests in Java"));
    }
}
