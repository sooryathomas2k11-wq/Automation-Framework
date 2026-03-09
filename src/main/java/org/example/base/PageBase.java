package org.example.base;

import org.example.common.Constants;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class PageBase extends WebDriverBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public PageBase() {
        driver = getDriverInstance();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Constants.EXPLICIT_WAIT));
    }

    /**
     * Click element after it becomes clickable
     */
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    /**
     * Type text into input field
     */
    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element
     */
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    /**
     * Check if element is visible
     */
    protected boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for element
     */
    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
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