package tests;

import org.example.base.TestBase;
import org.example.csv.CSVUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.util.Iterator;

public class LoginTest extends TestBase {
    @DataProvider(name = "loginData")
    public Iterator<Object[]> loginDataProvider() {
        return CSVUtils.readCSV("src/test/resources/LoginData.csv");
    }

   @Test(dataProvider = "loginData")
    public void testLogin(String username, String password){
       HomePage homePage=new HomePage(getDriverInstance());
       homePage.navigateToLoginPage();
       LoginPage loginPage=new LoginPage(getDriverInstance());
       loginPage.login(username, password);

   }


}
