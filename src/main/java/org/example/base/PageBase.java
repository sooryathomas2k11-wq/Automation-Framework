package org.example.base;

import org.example.common.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class PageBase extends WebDriverBase {

    protected WebDriverWait wait;

    public PageBase() {
        // Use getDriverInstance() directly to avoid "not initialized" errors
        this.wait = new WebDriverWait(getDriverInstance(), Duration.ofSeconds(Constants.EXPLICIT_WAIT));

        // Initialize PageFactory elements using the thread-safe driver
        PageFactory.initElements(getDriverInstance(), this);

        waitForPageLoad();
    }

    protected void waitForPageLoad() {
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));
    }

    protected void jsSelectText(WebElement element) {
        try {
            // Cast getDriverInstance() directly to JavascriptExecutor
            ((JavascriptExecutor) getDriverInstance()).executeScript("arguments[0].select();", element);
        } catch (Exception e) {
            throw new RuntimeException("JS Select failed for element: " + element.toString(), e);
        }
    }

    protected void jsClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) getDriverInstance()).executeScript("arguments[0].click();", element);
    }

    public boolean waitForElementToLoad(WebElement element) {
        try {
            WebDriverWait customWait = new WebDriverWait(getDriverInstance(), Duration.ofSeconds(Constants.PAGE_LOAD_TIME));
            customWait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}