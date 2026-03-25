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

    public SearchPageVerification(SearchPage searchPage) {
        this.searchPage = searchPage;
    }

    public int getTotalResults(String term) {
        waitForElementToLoad(searchPage.getResultsHeader());
        String headerText = searchPage.getResultsHeader().getText();
        System.out.println("Header text:" + headerText);

        String parts = headerText.split("of")[1].trim();
        String count = parts.split(" ")[0].replace(",", "");
        int totalResults = Integer.parseInt(count);

        logReport("Total results for " + term + ": " + totalResults);
        Assert.assertTrue(totalResults > 0, "Expected at least one result!");
        return totalResults;
    }

    public String getFirstResultTitle() {
        // FIX: Use getDriverInstance() instead of the null 'driver' variable
        WebDriverWait wait = new WebDriverWait(getDriverInstance(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions.visibilityOfAllElements(searchPage.getItemCartTitles()));
        WebElement firstTitle = searchPage.getItemCartTitles().get(0);
        return firstTitle.getText();
    }

    public void verifyFirstResult(String term) {
        String firstTitle = getFirstResultTitle();
        logReport("First result tile: " + firstTitle);
        Assert.assertTrue(firstTitle.contains(term), term + " is not displayed in the title:" + firstTitle);
    }

    public void applyYearFilter(int resultsBefore, String startYear) {
        // 1. Safe Cookie Click
        if (isElementDisplayed(searchPage.getTrusteConsentButton())) {
            searchPage.getTrusteConsentButton().click();
        }

        // FIX: Use getDriverInstance() for JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) getDriverInstance();
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", searchPage.getManufactureYearRangeHeader());

        // These methods (jsClick, jsSelectText) are inherited from PageBase/WebDriverBase
        // and already use getDriverInstance() internally.
        jsClick(searchPage.getManufactureYearRangeHeader());

        // FIX: Ensure 'wait' is initialized from the parent or use a local one
        WebDriverWait wait = new WebDriverWait(getDriverInstance(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(searchPage.getManufactureYearRangeMin()));
        jsSelectText(searchPage.getManufactureYearRangeMin());
        searchPage.getManufactureYearRangeMin().sendKeys(startYear);

        wait.until(ExpectedConditions.elementToBeClickable(searchPage.getManufactureYearRangeMax()));
        jsSelectText(searchPage.getManufactureYearRangeMax());

        int currentYear = Year.now().getValue();
        searchPage.getManufactureYearRangeMax().sendKeys(String.valueOf(currentYear));
        searchPage.getManufactureYearRangeMax().sendKeys(Keys.ENTER);

        // Wait for the header text to change from the previous count
        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElement(searchPage.getResultsHeader(), String.valueOf(resultsBefore))
        ));
    }
}