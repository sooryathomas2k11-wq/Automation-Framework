package pages;

import org.example.base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class LoginPage extends PageBase {

    public LoginPage(WebDriver webDriver) {
        super();
        this.verifyPage();
    }

    @FindBy(css = "input[data-auto='username']")
    private WebElement username;

    @FindBy(css = "input[data-auto='password']")
    private WebElement password;

    @FindBy(css = "button[data-auto='login']")
    private WebElement login;

    public WebElement getUsername() {
        return username;
    }

    public WebElement getPassword() {
        return password;
    }
    public WebElement getLogin(){
        return login;
    }


    public void login(String username, String password) {
        isElementPresent(getUsername());
        getUsername().sendKeys(username);
        getPassword().sendKeys(password);
        getLogin().click();
    }

    public void verifyPage() {
        Assert.assertTrue( getUsername().isDisplayed(),"Login pages are not coming up");
    }

}



