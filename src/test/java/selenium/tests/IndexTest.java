package selenium.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import selenium.pageobjects.IndexPageObject;

import static org.testng.Assert.assertTrue;

public class IndexTest extends IndexPageObject {

    private By sliderNavLeft;
    private By sliderNavRight;
    private String motivational;
    private WebDriverWait wait;
    private WebDriver webDriver;
    private IndexPageObject index;

    @BeforeTest
    public void setup(){

        sliderNavLeft = By.cssSelector("div[data-type=prev]");
        sliderNavRight = By.cssSelector("div[data-type=next]");
        motivational = "I scored an incredibly beautiful goal yesterday!";

        //Chrome Webdriver
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe" ); //winodws exe file

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("--ignore-certificate-errors");
        webDriver = new ChromeDriver(options);
        wait = new WebDriverWait(webDriver, 10);

        //PageObject
        index = new IndexPageObject(webDriver, wait);

        index.openHome();
        index.pageCheck();
    }

    @BeforeMethod
    public void methodSetup(){
        webDriver.navigate().refresh();
        index.pageCheck();
    }

    @AfterTest
    public void teardown(){
        webDriver.quit();
    }

    @Test
    public void openSidebarTest(){
        assertTrue(index.openSidebarIndex());
    }

    @Test
    public void closeSidebarTest(){
        assertTrue(index.closeSidebarIndex());
    }

    @Test
    public void scrollLeftTest(){
        assertTrue(index.scrollGallery(sliderNavLeft));
    }

    @Test
    public void scrollRightTest(){
        assertTrue(index.scrollGallery(sliderNavRight));
    }

    @Test
    public void validateTextTest(){
        assertTrue(index.verifyMotivationText(motivational));
    }
}
