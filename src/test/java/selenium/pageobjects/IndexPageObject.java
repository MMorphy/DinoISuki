package selenium.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static selenium.config.Constants.*;

public class IndexPageObject extends BasePageObject{


    By slideshow = By.className("active");
    By motivationText = By.className("about-text-align");


    public IndexPageObject(WebDriver webDriver, WebDriverWait waiter) {
        super(webDriver, waiter);
    }

    public IndexPageObject() {
        super();
    }

    public void pageCheck(){
        if(!isTitle(INDEX_TITLE)){
            openHome();
            isTitle(INDEX_TITLE, 5);
        }
    }

    public void openHome(){
        visit(INDEX_URL);
    }

    public boolean openSidebarIndex(){
        return openSidebar();
    }

    public boolean closeSidebarIndex(){
        return closeSidebar();
    }

    public boolean scrollGallery(By locator){
        String prevPic = findList(slideshow).get(0).getCssValue("data-index");
        click(locator);
        return isElementUpdated(locator, "data-index", prevPic);
    }

    public boolean verifyMotivationText(String expectedText){
        return compareText(motivationText, expectedText);
    }
}
