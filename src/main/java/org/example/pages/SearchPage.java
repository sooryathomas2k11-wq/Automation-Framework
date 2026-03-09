package org.example.pages;
import net.bytebuddy.asm.Advice;
import org.example.base.PageBase;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.time.Year;
import java.util.List;

public class SearchPage extends PageBase {
    private static final Logger logger = LoggerFactory.getLogger(PageBase.class);
    private WebDriver driver;
public SearchPage(WebDriver driver){
    this.driver=driver;
    PageFactory.initElements(driver,this);
}
    @FindBy(css = "h2[data-testid='non-cat-header']")
    private WebElement resultsHeader;
    @FindBy(css="a[data-testid='item-card-title-link']")
    private List<WebElement> itemCartTitles;

    @FindBy(id="truste-consent-button")
    private WebElement  trusteConsentButton;

    @FindBy(id="manufactureYearRange-header")
    private WebElement  manufactureYearRangeHeader;
    @FindBy(id="manufactureYearRange_min")
    private WebElement manufactureYearRangeMin;


    @FindBy(id="manufactureYearRange_max")
    private WebElement manufactureYearRangeMax;
    public WebElement getResultsHeader() {
        return resultsHeader;
    }

    public List<WebElement> getItemCartTitles() {
        return itemCartTitles;
    }
    public WebElement getTrusteConsentButton() {
        return trusteConsentButton;
    }
    public WebElement getManufactureYearRangeHeader() {
        return manufactureYearRangeHeader;
    }
    public WebElement getManufactureYearRangeMin() {
        return manufactureYearRangeMin;
    }

    public WebElement getManufactureYearRangeMax() {
        return manufactureYearRangeMax;
    }

    public String getFirstResultTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1. Wait for the list of titles to actually have items
        wait.until(ExpectedConditions.visibilityOfAllElements(getItemCartTitles()));

        // 2. Now that we know the list has size > 0, we can safely get(0)
        WebElement firstTitle = getItemCartTitles().get(0);
        return firstTitle.getText();
    }
    public int getTotalResults(String term) {
        waitForElementToLoad(getResultsHeader());
        String headerText = getResultsHeader().getText(); // "Showing 1–60 of 510 results..."
        System.out.println("Header text:"+headerText);
        // Logic: Split by "of" and take the next part, or use regex
        // Split: ["Showing 1-60 ", " 510 results for..."]
        String parts = headerText.split("of")[1].trim();

        // Extract the first number found (510)
        String count = parts.split(" ")[0].replace(",", "");
        int totalResults = Integer.parseInt(count);
        System.out.println("Total results for " + term + ": " +totalResults);
        Assert.assertTrue(totalResults >0, "Expected at least one result!");

        return totalResults;
    }

    public void verifyFirstResult(String term){
        String firstTitle= getFirstResultTitle();
        System.out.println("First result tile: "+firstTitle);
        Assert.assertTrue(firstTitle.contains(term),term+ " is not displayed in the title:"+firstTitle);
    }

    public void applyYearFilter(int startYear) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // 1. Safe Cookie Click
        try {
            getTrusteConsentButton().click();
        } catch (Exception e) {
            // Continue if banner isn't there
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOf(getManufactureYearRangeHeader()));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", getManufactureYearRangeHeader());
        js.executeScript("arguments[0].click();", getManufactureYearRangeHeader());

        wait.until(ExpectedConditions.elementToBeClickable(getManufactureYearRangeMin()));
        getManufactureYearRangeMin().click();
        js.executeScript("arguments[0].value = ''; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", getManufactureYearRangeMin());
        getManufactureYearRangeMin().sendKeys(String.valueOf(startYear));

        wait.until(ExpectedConditions.visibilityOf(getManufactureYearRangeMin()));

        getManufactureYearRangeMin().click();
        js.executeScript("arguments[0].value = ''; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", getManufactureYearRangeMax());

        int currentYear = Year.now().getValue();
        getManufactureYearRangeMax().sendKeys(String.valueOf(currentYear));
        getManufactureYearRangeMax().sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.not(
                ExpectedConditions.textToBePresentInElement(getResultsHeader(), String.valueOf(getResultsHeader()))
        ));

    }


}
