package selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static selenium.config.Dictionary.*;

public class IndexPageObject extends BasePageObject{

    //TODO: check IDs, these are preliminary!!!
    //Basic
    By homeBtn = By.id("home");
    By logoPic = By.id("logo");
    By mainDropDown = By.id("dropdown");
    By slideshow = By.id("slideshow");
    By motivationText = By.id("propaganda");
    By videoGallery = By.id("promoVideoGallery");

    //Logged in
    By footballCollapse = By.id("football");
    By tennisCollapse = By.id("tennis");
    By locationNameTextField = By.className("locationName");
    By locationInfoTextField = By.className("locationInfo");
    By locationPic = By.className("locationPic");
    By reserveTerm = By.className("reserveTerm");
    By reservationDiv = By.id("reservation");
    By datePickBtn = By.id("datePick");
    

    public IndexPageObject(WebDriver webDriver, WebDriverWait waiter) {
        super(webDriver, waiter);
    }

    public IndexPageObject() {
        super();
    }

    public void pageCheck(){
        if(!webDriver.getTitle().contains(INDEX_TITLE)){
            visit(INDEX_URL);
            isTitle(INDEX_TITLE); //probably should make another func for wait or rename this one
        }
    }

    public void openSidebar(){
        click(mainDropDown);
        isDisplayed(homeBtn); //home should always be in the dropdown
    }

    public void scrollGallery(){
    //TODO
    }

    public boolean verifyMotivationText(String expectedText){
        return getText(motivationText).equals(expectedText);
    }

    public boolean verifyVideoGallery(){
        //TODO: investigate picture/video comparison tools
        return false;
    }

    public String getSportName(By locator) {
        return getText(locator);
    }

    public boolean compareLocationPicture(){
        //TODO
        return false;
    }

    public void reserveTerm(){
        click(reserveTerm);
        //TODO: check oce li biti nova stranica ili popup
        isDisplayed(reservationDiv);

    }

    //TODO: finish this when Dejci is done
}
