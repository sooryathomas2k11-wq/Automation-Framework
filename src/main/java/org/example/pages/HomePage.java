package org.example.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private WebDriver driver;

    private By searchBox= By.cssSelector("div[data-testid='search input'] input");
    private By searchButton=By.cssSelector("div[data-testid='search button'] img");

   public HomePage(WebDriver driver){
       this.driver=driver;
   }

   public void enterSearchTerm(String term){
       driver.findElement(searchBox).clear();
       driver.findElement(searchBox).sendKeys(term);
   }

    public void clickSearchButton(){
        driver.findElement(searchButton).click();
    }
}
