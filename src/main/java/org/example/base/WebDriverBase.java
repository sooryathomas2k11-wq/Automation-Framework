package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;


import org.example.common.BrowserTypes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.util.HashMap;

public abstract class WebDriverBase {
   private WebDriver webDriver;
   private BrowserTypes browserType;

   private static HashMap<Long, WebDriver> map = new HashMap<Long, WebDriver>();
   private static HashMap<Long, BrowserTypes> browserMap = new HashMap<>();

   /**
    * Interacts with the driver map to return the webDriver specific to the current thread.
    *
    * @return the WebDriver instance specific to the currently running thread.
    */
   public static WebDriver getDriverInstance() {
      return map.get(Thread.currentThread().getId());
   }

   /**
    * Interacts with the browser map to return the browser type specific to the current thread.
    *
    * @return the browser enum specific to the currently running thread.
    */
   public static BrowserTypes getBrowserType() {
      return browserMap.get(Thread.currentThread().getId());
   }



   /**
    * Builds profiles for the specific browser that is sent in. Builds the corresponding profile and launches
    * that browser from the WebDriverBase WebDriver.
    *
    * @return The WebDriver with the appropriate browser is returned.
    */
   @BeforeMethod
   @Parameters({"browser"})
   protected WebDriver getWebDriver(BrowserTypes browser) {
      System.out.println("Running tests on thread: " + Thread.currentThread().getId());

      switch (browser) {
         case CHROME:
            browserType=BrowserTypes.CHROME;
            WebDriverManager.chromedriver().setup();
            webDriver=new ChromeDriver();
            Reporter.log("Running test in browser \'CHROME\'", true);
            break;
         case FIREFOX:
            browserType=BrowserTypes.FIREFOX;
            WebDriverManager.firefoxdriver().setup();
            webDriver=new FirefoxDriver();
            Reporter.log("Running test in browser \'FIREFOX\'", true);
            break;
            // add another browser as needed


      }
      webDriver.manage().deleteAllCookies();
      webDriver.manage().window().maximize();
      map.put(Thread.currentThread().getId(), webDriver);
     browserMap.put(Thread.currentThread().getId(), browserType);
      return webDriver;
   }



   protected void tearDown(){
      if(webDriver!=null){
         webDriver.quit();
      }
   }

}
