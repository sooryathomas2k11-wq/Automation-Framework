package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.common.BrowserTypes;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public abstract class WebDriverBase {
   private WebDriver webDriver;
   private BrowserTypes browserType;

   private static HashMap<Long, WebDriver> driverMap = new HashMap<Long, WebDriver>();
   private static HashMap<Long, BrowserTypes> browserMap = new HashMap<>();

   /**
    * Interacts with the driver map to return the webDriver specific to the current thread.
    *
    * @return the WebDriver instance specific to the currently running thread.
    */
   public static WebDriver getDriverInstance() {
      return driverMap.get(Thread.currentThread().getId());
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
   @Parameters({"browser","hubUrl"})
   protected WebDriver getWebDriver(BrowserTypes browser, String hubUrl) {
      System.out.println("Running tests on thread: " + Thread.currentThread().getId());
      boolean isRemote = hubUrl != null && !hubUrl.isEmpty();
      switch (browser) {
         case CHROME:
            browserType=BrowserTypes.CHROME;
            ChromeOptions chromeOptions = new ChromeOptions();
            if(isRemote){
             try{
                webDriver=new RemoteWebDriver(new URL(hubUrl),chromeOptions);
                Reporter.log("Running test on Selenium Grid in Chrome",true);
             }catch (MalformedURLException e){
                e.printStackTrace();
                throw new RuntimeException("Invalid Grid Url"+hubUrl);
             }
            }else {
               WebDriverManager.chromedriver().setup();
               webDriver = new ChromeDriver(chromeOptions);
               Reporter.log("Running test in browser \'CHROME\'", true);
            }
            break;
         case FIREFOX:
            browserType=BrowserTypes.FIREFOX;
            FirefoxOptions firefoxOptions=new FirefoxOptions();
            if(isRemote){
               try{
                  webDriver=new RemoteWebDriver(new URL(hubUrl),firefoxOptions);
               }catch (MalformedURLException e){
                  e.printStackTrace();
                  throw new RuntimeException("Invalid Grid Url"+hubUrl);
               }
            }else {
               WebDriverManager.firefoxdriver().setup();
               webDriver = new FirefoxDriver(firefoxOptions);
               Reporter.log("Running test in browser \'FIREFOX\'", true);
               Reporter.log("Running test in local Firefox", true);
            }
            break;
            // add another browser as needed


      }
      webDriver.manage().deleteAllCookies();
      webDriver.manage().window().maximize();
      driverMap.put(Thread.currentThread().getId(), webDriver);
     browserMap.put(Thread.currentThread().getId(), browserType);
      return webDriver;
   }

   /**
    * Helper method to launch RemoteWebDriver for Selenium Grid
    */
   private WebDriver launchGridDriver(WebDriver driver, URL hubUrl) {
      try {
         return new RemoteWebDriver(hubUrl, ((RemoteWebDriver) driver).getCapabilities());
      } catch (Exception e) {
         Reporter.log("Error launching RemoteWebDriver on Grid", true);
         e.printStackTrace();
         return null;
      }
   }

   protected void tearDown(){
      if(webDriver!=null){
         webDriver.quit();
      }
   }

}
