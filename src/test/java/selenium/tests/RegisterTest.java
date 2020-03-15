package selenium.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import selenium.pageobjects.RegisterPageObject;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;
import static selenium.config.Constants.*;

public class RegisterTest extends RegisterPageObject {

    private String user;
    private String pass;
    private String dob;
    private String mail;
    private String miljenko;
    private WebDriverWait wait;
    private WebDriver webDriver;
    private RegisterPageObject register;

    @BeforeTest
    public void setup(){
        //Chrome Webdriver
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe" ); //winodws exe file

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--ignore-certificate-errors");
        webDriver = new ChromeDriver(options);
        wait = new WebDriverWait(webDriver, 10);

        //PageObject
        register = new RegisterPageObject(webDriver, wait);

        dob = "12Dec1980";
        pass = "defaultPass";
        user = "defaultUser";
        mail = "defaultman@gmail.com";
        miljenko = "miljenko";

        register.openRegister();
        register.pageCheck();
    }

    @BeforeMethod
    public void methodSetup(){
        webDriver.navigate().refresh();
        register.pageCheck();
    }

    @AfterTest
    public void teardown(){
        webDriver.quit();
    }

    @Test
    public void openSidebarTest(){
        assertTrue(register.openSidebarRegister());
    }

    @Test
    public void closeSidebarTest(){
        assertTrue(register.closeSidebarRegister());
    }

    @Test
    public void registerTest(){
        assertTrue(register.register(user, pass, mail, dob));
    }

    @Test
    public void doubleRegisterTest(){
        register.register(miljenko, miljenko, miljenko, miljenko);
        assertFalse(register.register(miljenko, miljenko, miljenko, miljenko));
    }

    @Test
    public void fakeRegisterTest(){
        assertFalse(register.register(EMTPY_STRING, EMTPY_STRING, EMTPY_STRING, EMTPY_STRING));
    }


}
