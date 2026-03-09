package org.example.pages;
import org.example.base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



public class HomePage extends PageBase {
   public HomePage(){
       super();
   }

    @FindBy(css = "div[data-testid='search input'] input")
    private WebElement searchBox;

    @FindBy(css = "img[alt='Search']")
    private WebElement searchButton;
    public WebElement getSearchBox() {
        return searchBox;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }


   public SearchPage performSearch(String term){
     getSearchBox().clear();
      getSearchBox().sendKeys(term);
      getSearchButton().click();
      return new SearchPage();
   }

}
