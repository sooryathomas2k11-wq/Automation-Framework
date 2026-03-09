package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.common.BrowserTypes;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class WebDriverBase {

   // ThreadLocal containers to isolate driver and browser per thread
   private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
   private static ThreadLocal<BrowserTypes> browserType = new ThreadLocal<>();

   /**
    * Returns the WebDriver instance for the current thread
    */
   public static WebDriver getDriverInstance() {
      return driver.get();
   }

   /**
    * Returns the browser type for the current thread
    */
   public static BrowserTypes getBrowserType() {
      return browserType.get();
   }

   /**
    * Initializes WebDriver before each test method
    */
   @BeforeMethod(alwaysRun = true)
   @Parameters({"browser", "hubUrl"})
   protected void setUp(BrowserTypes browser, String hubUrl) {

      Reporter.log("Starting test on Thread ID: " + Thread.currentThread().getId(), true);

      WebDriver localDriver;
      boolean isRemote = hubUrl != null && !hubUrl.isEmpty();

      switch (browser) {
         case CHROME:
            ChromeOptions chromeOptions = new ChromeOptions();
            if (isRemote) {
               localDriver = launchGridDriver(chromeOptions, hubUrl);
               Reporter.log("Running on Selenium Grid: " + hubUrl, true);
            } else {
               WebDriverManager.chromedriver().setup();
               localDriver = new ChromeDriver(chromeOptions);
               Reporter.log("Running locally in CHROME", true);
            }
            break;

         default:
            throw new RuntimeException("Browser not supported: " + browser);
      }

      localDriver.manage().deleteAllCookies();
      localDriver.manage().window().maximize();

      // Set the driver and browser type into ThreadLocal
      driver.set(localDriver);
      browserType.set(browser);
   }

   private WebDriver launchGridDriver(Capabilities capabilities, String hubUrl) {
      try {
         return new RemoteWebDriver(new URL(hubUrl), capabilities);
      } catch (MalformedURLException e) {
         throw new RuntimeException("Invalid Selenium Grid Hub URL: " + hubUrl, e);
      } catch (Exception e) {
         throw new RuntimeException("Failed to start remote driver", e);
      }
   }

   /**
    * Close browser and clean up ThreadLocal memory
    */
   @AfterMethod(alwaysRun = true)
   protected void tearDown() {
      if (getDriverInstance() != null) {
         getDriverInstance().quit();
      }
      // Critical: Remove data from ThreadLocal to prevent memory leaks
      driver.remove();
      browserType.remove();
      Reporter.log("Driver closed and ThreadLocal cleaned for Thread: " + Thread.currentThread().getId(), true);
   }
}