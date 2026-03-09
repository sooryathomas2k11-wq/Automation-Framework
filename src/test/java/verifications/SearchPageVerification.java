package verifications;

import org.example.base.VerificationBase;
import org.example.pages.SearchPage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.time.Year;

public class SearchPageVerification extends VerificationBase {

    private SearchPage searchPage;
    public SearchPageVerification (SearchPage searchPage){
        this.searchPage=searchPage;

    }

    public int getTotalResults(String term) {
        waitForElementToLoad(searchPage.getResultsHeader());
        String headerText = searchPage.getResultsHeader().getText(); // "Showing 1–60 of 510 results..."
        System.out.println("Header text:"+headerText);
        // Logic: Split by "of" and take the next part, or use regex
        // Split: ["Showing 1-60 ", " 510 results for..."]
        String parts = headerText.split("of")[1].trim();

        // Extract the first number found
        String count = parts.split(" ")[0].replace(",", "");
        int totalResults = Integer.parseInt(count);
        logReport("Total results for " + term + ": " +totalResults);
        Assert.assertTrue(totalResults >0, "Expected at least one result!");

        return totalResults;
    }



    public String getFirstResultTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Wait for the list of titles to actually have items
        wait.until(ExpectedConditions.visibilityOfAllElements(searchPage.getItemCartTitles()));

        // 2. Now that we know the list has size > 0, we can safely get(0)
        WebElement firstTitle = searchPage.getItemCartTitles().get(0);
        return firstTitle.getText();
    }

    public void verifyFirstResult(String term){
        String firstTitle= getFirstResultTitle();
        logReport("First result tile: "+firstTitle);
        Assert.assertTrue(firstTitle.contains(term),term+ " is not displayed in the title:"+firstTitle);
    }

    public void applyYearFilter(int resultsBefore, String startYear) {

        // 1. Safe Cookie Click
      if(isElementDisplayed( searchPage.getTrusteConsentButton())) {

          searchPage.getTrusteConsentButton().click();
      }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", searchPage.getManufactureYearRangeHeader());
        jsClick(searchPage.getManufactureYearRangeHeader());

        wait.until(ExpectedConditions.elementToBeClickable(searchPage.getManufactureYearRangeMin()));
        js.executeScript("arguments[0].select();", searchPage.getManufactureYearRangeMin());
        searchPage.getManufactureYearRangeMin().sendKeys(startYear);

        wait.until(ExpectedConditions.elementToBeClickable(searchPage.getManufactureYearRangeMax()));
        js.executeScript("arguments[0].select();", searchPage.getManufactureYearRangeMax());
        int currentYear = Year.now().getValue();
        searchPage.getManufactureYearRangeMax().sendKeys(String.valueOf(currentYear));
        searchPage.getManufactureYearRangeMax().sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElement(searchPage.getResultsHeader(), String.valueOf(resultsBefore))
        ));

    }
}
