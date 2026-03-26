package org.example.reporting;

import org.apache.commons.io.FileUtils;
import org.example.base.TestBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility extends TestBase {

    // Simplified path for Extent Reports
    private static final String SCREENSHOT_FOLDER = System.getProperty("user.dir") + "/target/screenshots/";

    /**
     * Captures a screenshot and returns the absolute path.
     * This path is then used by the ExtentListener to attach the image.
     */
    public static String getScreenshot(String screenshotName) {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) getDriverInstance();
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Define destination
        String destination = SCREENSHOT_FOLDER + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);

        try {
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot: " + e.getMessage());
        }

        return destination;
    }
}