package com.nina.pages.example;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class SearchResultsPage {
    private final ElementsCollection results = $$(".react-results--main li");

    public SelenideElement getResult(int index) {
        return results.get(index);
    }

    public ElementsCollection getResults() {
        return results;
    }
}
