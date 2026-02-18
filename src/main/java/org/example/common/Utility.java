package org.example.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Random;

public final class Utility {
    private Utility() {
    }

    /**
     * Select dropdown of element selectElement option at index
     * @param selectElement
     * @param index
     * @return WebElement with selected values
     */
    public static WebElement selectDropDown(WebElement selectElement, int index) {
        Select varIdDropDown = new Select(selectElement);
        varIdDropDown.selectByIndex(index);
        return varIdDropDown.getFirstSelectedOption();
    }

    /**
     * To generate random number fromm the given range.
     * @param min
     * @param max
     * @return
     */
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

}
