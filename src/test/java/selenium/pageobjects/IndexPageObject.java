package selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static selenium.config.Constants.*;

public class IndexPageObject extends BasePageObject{

    //TODO: check IDs, these are preliminary!!!
    //Basic
    By slideshow = By.id("slideshow");
    By motivationText = By.id("propaganda");
    By videoGallery = By.id("promoVideoGallery");

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
        return compareText(motivationText, expectedText);
    }

    public boolean verifyVideoGallery(){
        //TODO: investigate picture/video comparison tools
        return false;
    }

    //TODO: finish this when Dejci is done
}
