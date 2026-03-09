package org.example.base;
import org.testng.annotations.BeforeMethod;

public abstract class TestBase extends WebDriverBase {

    @BeforeMethod(alwaysRun = true)
    public void initializeTest() {
        // Fetch URL from system properties or use default
        String baseUrl = System.getProperty("baseUrl", "https://www.rbauction.com");

        // getDriverInstance() safely retrieves the driver for the current thread
        if (getDriverInstance() != null) {
            getDriverInstance().get(baseUrl);
        } else {
            throw new RuntimeException("WebDriver was not initialized for this thread!");
        }
    }
}