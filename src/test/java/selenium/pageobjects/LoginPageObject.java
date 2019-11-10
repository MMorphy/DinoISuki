package selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageObject extends BasePageObject{

    By userInput = By.id("username");
    By passInput = By.id("password");
    By loginBtn = By.id("login");

    public LoginPageObject(WebDriver driver, WebDriverWait waiter) {
        super(driver, waiter);
    }

    public LoginPageObject() {
        super();
    }

    public void enterUsr(String username){
        type(userInput, username);
    }

    public void enterPass(String password){
        type(passInput, password);
    }

    public void login(){
        click(loginBtn);
    }




}
