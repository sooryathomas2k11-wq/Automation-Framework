package org.example.base;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import java.text.MessageFormat;

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

    /**
     * Report formatter method.
     */
    public void logReport(String message, Object... args) {
        StringBuilder stringBuilder = new StringBuilder();
        String output;
        if (args != null && args.length > 0) {
            output = MessageFormat.format(message, args);
            stringBuilder.append("<br/>");
            stringBuilder.append(output);
        }
        else{
            stringBuilder.append("<br/>Verified that : ");
            stringBuilder.append(message);
        }

        Reporter.log(stringBuilder.toString(), true);
    }
}
