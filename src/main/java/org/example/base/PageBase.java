package org.example.base;

import org.example.common.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class PageBase extends WebDriverBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public PageBase() {
        driver = getDriverInstance();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
        // 1. Initialize PageFactory elements for child classes
        PageFactory.initElements(driver, this);

        // 2. Initial synchronization
        waitForPageLoad();
    }
    /**
     * Wait for document to be ready (Universal for React/Standard sites)
     */
    protected void waitForPageLoad() {
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
    }



    // Use JavaScript click when standard click is intercepted (like by a cookie banner)
    protected void jsClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    /**
     * Selects all text in an input field using JavaScript.
     *
     */
    protected void jsSelectText(WebElement element) {
        try {

            JavascriptExecutor js = (JavascriptExecutor) getDriverInstance();

            js.executeScript("arguments[0].select();", element);

        } catch (Exception e) {
            throw new RuntimeException("JS Select failed for element: " + element.toString(), e);
        }
    }
    public boolean waitForElementToLoad(WebElement element) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriverInstance(),Duration.ofSeconds(Constants.PAGE_LOAD_TIME));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}