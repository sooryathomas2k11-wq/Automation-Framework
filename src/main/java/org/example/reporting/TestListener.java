package org.example.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {
        // Initialize the report file
        ExtentSparkReporter spark = new ExtentSparkReporter("target/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Framework", "RB Automation");
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new entry in the report for each test
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Case: " + result.getName() + " PASSED");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // 1. Log the failure and the exception
        test.get().fail(result.getThrowable());

        // 2. Capture screenshot and get the path
        String screenshotPath = ScreenshotUtility.getScreenshot(result.getName());

        // 3. Attach the screenshot to the Extent Report
        try {
            test.get().addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
        } catch (Exception e) {
            test.get().info("Could not attach screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // This is CRITICAL: It writes everything to the HTML file
        if (extent != null) {
            extent.flush();
        }
    }
}