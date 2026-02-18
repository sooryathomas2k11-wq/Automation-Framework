package pages;

import org.example.base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends PageBase {
    public HomePage(WebDriver webDriver) {
        super();
        verifyPage();
    }
    @FindBy(css="a[data-auto='desktop-sign-in']")
    private WebElement desktopSignin;

    public WebElement getDesktopSignin(){
        return desktopSignin;
    }

    public LoginPage navigateToLoginPage(){
        clickElement(getDesktopSignin());
        return new LoginPage(getDriverInstance());

    }
    public void verifyPage() {
        Assert.assertTrue( getDesktopSignin().isDisplayed(),"Home pages are not coming up");
    }

}
