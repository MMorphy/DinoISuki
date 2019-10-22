package selenium.pageobjects;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {

    private WebDriver webDriver;
    private WebDriverWait wait;

    public BasePageObject(WebDriver driver) {
        this.webDriver = driver;
    }

    public void visit(String url){
        if(url.contains("http")) {
            this.webDriver.get(url);
        }else{
            this.webDriver.get("https" + url);
        }
    }

    public WebElement find(By locator){
        return this.webDriver.findElement(locator);
    }

    public void click(By locator){
        find(locator).click();
    }

    public void type(By locator, String text){
        find(locator).sendKeys(text);
    }

    public boolean isDisplayed(By locator){
        try{
            return find(locator).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public boolean isDisplayed(By locator, int timeout){
        try {
            wait = new WebDriverWait(webDriver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (TimeoutException e){
            return false;
        }
        return true;
    }
}
