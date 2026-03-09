package tests;

import org.apache.hc.core5.reactor.Command;
import org.example.base.TestBase;
import org.example.csv.CSVUtils;
import org.example.pages.HomePage;
import org.example.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

public class RBAuctionTests extends TestBase {

    @DataProvider(name = "SearchData")
    public Iterator<Object[]> loginDataProvider() {
        return CSVUtils.readCSV("src/test/resources/SearchData.csv");
    }

    @Test(dataProvider = "SearchData",priority = 1)
    public void testSearchAutomation(String term){
        HomePage homePage=new HomePage(getDriverInstance());
        homePage.performSearch(term);
        SearchPage searchPage=new SearchPage(getDriverInstance());
        searchPage.getTotalResults(term);
        searchPage.verifyFirstResult(term);

    }

   @Test(priority=2)
    public void verifyYearFilterChangesResults(){
    HomePage homePage=new HomePage(getDriverInstance());
    homePage.performSearch("F-150");
    SearchPage searchPage=new SearchPage(getDriverInstance());
    int resultsBeforeFilter = searchPage.getTotalResults("F-150");
    System.out.println("Results before filter: " + resultsBeforeFilter);
    searchPage.applyYearFilter(2010);
    int resultsAfterFilter = searchPage.getTotalResults("F-150");

       Assert.assertTrue(resultsAfterFilter < resultsBeforeFilter,
               "Filtered results should be less than original results");
    }

}
