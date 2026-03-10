package tests;


import org.example.base.TestBase;
import org.example.utils.CSVUtils;
import org.example.pages.HomePage;
import org.example.pages.SearchPage;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import verifications.SearchPageVerification;

import java.util.Iterator;

public class RBAuctionTests extends TestBase {

    @DataProvider(name = "SearchData")
    public Iterator<Object[]> loginDataProvider() {
        return CSVUtils.readCSV("src/test/resources/SearchData.csv");
    }

    @Test(dataProvider = "SearchData",priority = 1)
    public void testSearchDataResults(String term){
        HomePage homePage=new HomePage();
        homePage.performSearch(term);
        SearchPage searchPage=new SearchPage();
        SearchPageVerification searchPageVerification=new SearchPageVerification(searchPage);
        searchPageVerification.getTotalResults(term);
        searchPageVerification.verifyFirstResult(term);

    }
    @DataProvider(name = "filterSearchData")
    public Iterator<Object[]> filterSearchData() {
        return CSVUtils.readCSV("src/test/resources/FilterData.csv");
    }
   @Test(dataProvider = "filterSearchData",priority=2)
    public void verifyYearFilterChangesResults(String filterTerm, String beforeYear){
    HomePage homePage=new HomePage();
    homePage.performSearch(filterTerm);
    SearchPage searchPage=new SearchPage();
    SearchPageVerification searchPageVerification=new SearchPageVerification(searchPage);
    int resultsBeforeFilter = searchPageVerification.getTotalResults(filterTerm);
    searchPageVerification.applyYearFilter(resultsBeforeFilter,beforeYear);
    int resultsAfterFilter = searchPageVerification.getTotalResults(filterTerm);

       Assert.assertTrue(resultsAfterFilter < resultsBeforeFilter,
               "Filtered results should be less than original results");
    }

}
