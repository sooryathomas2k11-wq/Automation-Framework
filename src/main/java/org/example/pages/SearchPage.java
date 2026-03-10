package org.example.pages;
import org.example.base.PageBase;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class SearchPage extends PageBase {

public SearchPage(){
    super();
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







}
