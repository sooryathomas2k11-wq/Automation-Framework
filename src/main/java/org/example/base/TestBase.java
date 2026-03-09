package org.example.base;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class TestBase extends WebDriverBase {
   @BeforeMethod
    public void initializeTest(){
       String baseUrl = System.getProperty("baseUrl", "https://www.rbauction.com");
       getDriverInstance().get(baseUrl);

   }

   @AfterMethod
   public void cleanUp() {
       try {
           tearDown();
       } catch (Exception e) {
           Reporter.log("Error during teardown: " + e.getMessage(),true);
       }
   }
}
