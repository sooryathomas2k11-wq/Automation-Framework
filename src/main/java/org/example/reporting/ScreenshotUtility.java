package org.example.reporting;

import org.apache.commons.io.FileUtils;
import org.example.base.TestBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;

final public class ScreenshotUtility extends TestBase {

    private static final String SCREENSHOT_EXTENSION = ".png";
    private static final String SCREENSHOT_PATH = "target/surefire-reports/html/screenshots/";
    private static final String SCREENSHOT_SRC = "screenshots/";
    private static final String TEST_CASE_STATUS = "TestCaseStatus";

    public static void embedExceptionDetailsInReport(Throwable throwable) {
        final ITestResult testResult = Reporter.getCurrentTestResult();
        testResult.setAttribute(TEST_CASE_STATUS, ITestResult.FAILURE);

        ScreenshotUtility screenshotUtility = new ScreenshotUtility();
        screenshotUtility.takeScreenshot(testResult);
    }

    public void takeScreenshot(final ITestResult result) {
        try {
            File screenshot = ((TakesScreenshot) getDriverInstance()).getScreenshotAs(OutputType.FILE);
            final String screenshotName = System.nanoTime() + SCREENSHOT_EXTENSION;
            String screenPath = SCREENSHOT_PATH;
            String imgSrc = SCREENSHOT_SRC;

            File newScreenshot = new File(screenPath + screenshotName);
            FileUtils.copyFile(screenshot, newScreenshot);
            Reporter.log("Screenshot: <br><a href=" + imgSrc + screenshotName + " target='_blank' >Screenshot:" + screenshotName + "</a><br>");
        } catch (Exception e) {
            Reporter.log(e.getMessage());
        }
    }

}
