package org.example.base;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class TestBase extends WebDriverBase {

   @BeforeMethod
    public void initializeTest(){
       getDriverInstance().get("https://www.upgrade.com"); //replace with actual url

   }

   @AfterMethod
    public void cleanUp(){
       tearDown();
   }
}
