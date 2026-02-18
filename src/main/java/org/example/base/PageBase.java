package org.example.base;

import org.example.common.Constants;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class PageBase extends WebDriverBase {

    public PageBase(){
        PageFactory.initElements(getDriverInstance(),this);

    }

    /**
     * Clicks and element after the element has become visible.
     * @param element the element to click.
     */
    public void clickElement(WebElement element) {
        WebElement elementToClick = new WebDriverWait(getDriverInstance(), Duration.ofMillis(Constants.PAGE_LOAD_TIME)).until(ExpectedConditions.visibilityOf(element));
        elementToClick.click();
        waitForPageToLoad(Constants.PAGE_ITEM_LOAD_TIME);
    }

    protected  void sendkeys(WebElement element,String text){
        element.sendKeys(text);
    }

    protected String getText(WebElement element){
        return element.getText();
    }
    /**
     * A one-time setting of how long to wait for a page to load before throwing an exception.
     * @param timeOut The timeout value in milliseconds.
     */
    public void waitForPageToLoad(int timeOut) {
        if (timeOut > 0)
            getDriverInstance().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeOut));
    }

    /**
     * Checks the visibility of the element on the UI
     * @param element the element to check visibility of
     * @return true if the element is present on the UI
     */
    public boolean isElementPresent(WebElement element) {
        try {
            element.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }



    //add common page base actions here

}
