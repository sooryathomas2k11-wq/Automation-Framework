package org.example.pages;
import org.example.base.PageBase;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;



public class HomePage extends PageBase {
   public HomePage(){
       super();
   }



    @FindBy(css = "svg[data-testid='CloseIcon']")
    private WebElement closeIcon;
    @FindBy(css = "input[data-testid='search input']")
    private WebElement searchBox;

    @FindBy(css = "svg[data-testid='SearchIcon']")
    private WebElement searchButton;

    public WebElement getCloseIcon() {
        return closeIcon;
    }
    public WebElement getSearchBox() {
        return searchBox;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }


   public SearchPage performSearch(String term){
//      getCloseIcon().click();
     getSearchBox().clear();
      getSearchBox().sendKeys(term);
      getSearchButton().click();
      return new SearchPage();
   }

}
