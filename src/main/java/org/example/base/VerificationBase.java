package org.example.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class VerificationBase extends PageBase {

    /**
     * This method used WebDriver's isDisplayed method to determine if an element is displayed on the page.
     * @param element
     * @return
     */

    public boolean isElementDisplayed(WebElement element) {
        try {
            element.isDisplayed();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
