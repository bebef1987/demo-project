package com.test.demoProject.tests;

import com.test.demoProject.steps.HomeStep;
import com.test.demoProject.steps.SearchResultsSteps;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.openqa.selenium.WebDriver;

@RunWith(SerenityRunner.class)
public class SearchTest {

	@Managed()
	public WebDriver webdriver;

	@Steps
	public HomeStep homeStep;

	@Steps
	public SearchResultsSteps searchResultsSteps;

	@Test
	public void SearchTest_searchThis() {
		homeStep.openFirstPage();
		homeStep.typeSearch("GPS");

		searchResultsSteps.checkSearchResults();
	}

	@Test
	public void SearchTest_searchThis1() {
		homeStep.openFirstPage();
		homeStep.typeSearch("GPS");

		searchResultsSteps.checkSearchResults();
	}
	@Test
	public void SearchTest_searchThis2() {
		homeStep.openFirstPage();
		homeStep.typeSearch("GPS");

		searchResultsSteps.checkSearchResults();
	}
	@Test
	public void SearchTest_searchThis3() {
		homeStep.openFirstPage();
		homeStep.typeSearch("GPS");

		searchResultsSteps.checkSearchResults();
	}
	@Test
	public void SearchTest_searchThis4() {
		homeStep.openFirstPage();
		homeStep.typeSearch("GPS");

		searchResultsSteps.checkSearchResults();
	}
	@Test
	public void SearchTest_searchThis5() {
		homeStep.openFirstPage();
		homeStep.typeSearch("GPS");

		searchResultsSteps.checkSearchResults();
	}
	@Test
	public void SearchTest_searchThis6() {
		homeStep.openFirstPage();
		homeStep.typeSearch("GPS");

		searchResultsSteps.checkSearchResults();
	}
	@Test
	public void SearchTest_searchThis7() {
		homeStep.openFirstPage();
		homeStep.typeSearch("GPS");

		searchResultsSteps.checkSearchResults();
	}
	@Test
	public void SearchTest_searchThis8() {
		homeStep.openFirstPage();
		homeStep.typeSearch("GPS");

		searchResultsSteps.checkSearchResults();
	}
}
