package org.example.base;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.example.utils.ConfigReader;
import org.example.common.EnvironmentTypes;

public abstract class TestBase extends WebDriverBase {

    @BeforeMethod(alwaysRun = true)
    public void initializeTest() {
        // 1. Get environment from -Denv flag, default to QA
        String envInput = System.getProperty("env", "PROD").toUpperCase();
        EnvironmentTypes currentEnv = EnvironmentTypes.valueOf(envInput);

        String targetUrl;

        // 2. Select URL based on your Enum
        if (currentEnv == EnvironmentTypes.PROD) {
            targetUrl = ConfigReader.getProperty("prodUrl");
        } else {
            targetUrl = ConfigReader.getProperty("qaUrl");
        }

        // 3. Optional: Allow a total override via -DbaseUrl
        String finalUrl = System.getProperty("baseUrl", targetUrl);

        System.out.println("Running in " + currentEnv + " mode: " + finalUrl);

        if (getDriverInstance() != null) {
            getDriverInstance().get(finalUrl);
        } else {
            throw new RuntimeException("WebDriver was not initialized for this thread!");
        }
    }



}