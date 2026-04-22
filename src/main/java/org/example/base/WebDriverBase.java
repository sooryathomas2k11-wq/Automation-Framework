package org.example.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.example.common.BrowserTypes;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class WebDriverBase {
   // ThreadLocal containers to isolate driver and browser per thread for parallel execution
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
    * Initializes WebDriver before each test method.
    * @Optional allows running individual tests from IDE without testng.xml parameters.
    */
   @BeforeMethod(alwaysRun = true)
   @Parameters({"browser", "hubUrl"})
   protected void setUp(@Optional("CHROME") BrowserTypes browser, @Optional("") String hubUrl) {
      Reporter.log("Starting test on Thread ID: " + Thread.currentThread().getId(), true);

      WebDriver localDriver;
      boolean isRemote = hubUrl != null && !hubUrl.isEmpty();

      switch (browser) {
         case CHROME:
            ChromeOptions chromeOptions = new ChromeOptions();

            if (isRemote) {
               chromeOptions.addArguments("--no-sandbox");
               chromeOptions.addArguments("--disable-dev-shm-usage"); // Prevents page crashes in Docker
               chromeOptions.addArguments("--disable-gpu");
               chromeOptions.addArguments("--disable-software-rasterizer");

               // Ensures the browser waits properly for the heavy RB site to load
               chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

               localDriver = launchGridDriver(chromeOptions, hubUrl);
               Reporter.log("Running on M1 Selenium Grid (Headed): " + hubUrl, true);
            } else {
               // Local execution logic
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

   /**
    * Helper to launch RemoteWebDriver for Docker Grid
    */
   private WebDriver launchGridDriver(Capabilities capabilities, String hubUrl) {
      try {
         return new RemoteWebDriver(new URL(hubUrl), capabilities);
      } catch (MalformedURLException e) {
         throw new RuntimeException("Invalid Selenium Grid Hub URL: " + hubUrl, e);
      } catch (Exception e) {
         throw new RuntimeException("Failed to start remote driver on Docker", e);
      }
   }

   /**
    * Close browser and clean up ThreadLocal memory after each test
    */
   @AfterMethod(alwaysRun = true)
   protected void tearDown() {
      if (getDriverInstance() != null) {
         try {
            getDriverInstance().quit(); // This kills the session on the Grid
            Reporter.log("SUCCESS: Driver quit successfully.", true);
         } catch (Exception e) {
            Reporter.log("ERROR: Could not quit driver: " + e.getMessage(), true);
         } finally {
            // ALWAYS remove from ThreadLocal to prevent memory leaks
            driver.remove();
            browserType.remove();
         }
      }
   }
}