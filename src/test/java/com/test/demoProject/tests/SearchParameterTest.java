package com.test.demoProject.tests;

import com.test.demoProject.steps.HomeStep;
import com.test.demoProject.steps.SearchResultsSteps;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom("$DATADIR/SearchTest.csv")
public class SearchParameterTest {

	@Managed()
	public WebDriver webdriver;

	@Steps
	public HomeStep homeStep;

	@Steps
	public SearchResultsSteps searchResultsSteps;

	String search;

	@Qualifier
	public String qualifier() {
		return " "+ search;
	}

	@Test
	public void SearchParameterTest_searchThis() {
		homeStep.openFirstPage();
		homeStep.typeSearch(search);

		searchResultsSteps.checkSearchResults();
	}

}
