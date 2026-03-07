package org.example.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TestBase extends WebDriverBase {
    private static final Logger logger = LoggerFactory.getLogger(TestBase.class);
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
           logger.error("Error during teardown: " + e.getMessage());
       }
   }
}
