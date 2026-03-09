package org.example.reporting;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("STARTING TEST: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("PASSED: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Throwable throwable = new Throwable(result.getThrowable());
        ScreenshotUtility.embedExceptionDetailsInReport(throwable);
        Reporter.log("<b>Screenshot for failure: " + result.getName() + "</b>", true);
    }
}
