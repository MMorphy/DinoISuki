package selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static selenium.config.Constants.*;

public class RegisterPageObject extends BasePageObject {

    private String url = "https://localhost:8443"; //tmp, will be changed to testng argument variable
    private By usernameInput = By.id("username");
    private By passInput = By.id("password");
    private By passConfigmInput = By.id("confirmedPassword");
    private By mailInput = By.id("email");
    private By dobInput = By.id("dateOfBirth");
    private By registerBtn = By.className("login-registration-button-color");
    private By successMsg = By.linkText("You have successfully registered!");


    public RegisterPageObject(WebDriver webDriver, WebDriverWait wait){ super(webDriver,wait);}

    public RegisterPageObject() {
        super();
    }

    public void pageCheck(){
        if(!isTitle(REGISTER_TITLE)){
            openRegister();
            isTitle(INDEX_TITLE, 5);
        }
    }

    public void openRegister(){
        visit(url+REGISTER_URL);
    }

    public boolean register(String user, String pass, String mail, String date){
        type(usernameInput, user);
        type(passInput, pass);
        type(passConfigmInput, pass);
        type(mailInput, mail);
        type(dobInput, date);
        click(registerBtn);
        return isDisplayed(successMsg, 5);
    }

    public boolean openSidebarRegister(){
        return openSidebar();
    }

    public boolean closeSidebarRegister(){
        return closeSidebar();
    }



}
