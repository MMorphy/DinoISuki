package selenium.pageobjects;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject {

    protected WebDriver webDriver;
    protected WebDriverWait wait;


    public BasePageObject(WebDriver webDriver, WebDriverWait wait) {
        this.webDriver = webDriver;
        this.wait = wait;
    }

    public BasePageObject(){}

    /**
     * Go to URL
     *
     * @author Leo Jakus-Mejarec
     * @param url URL to visit
     */
    public void visit(String url){
        if(url.contains("http")) {
            this.webDriver.get(url);
        }else{
            this.webDriver.get("https" + url);
        }
    }

    private WebElement find(By locator){
        return this.webDriver.findElement(locator);
    }

    public void click(By locator){
        find(locator).click();
    }

    public void type(By locator, String text){
        find(locator).sendKeys(text);
    }

    /**
     * Checks if certain element is displayed, using default WebDriverWait
     *
     * @author Leo Jakus-Mejarec
     * @param locator By locator of the element
     * @return True if element is found, flase if not
     */
    public boolean isDisplayed(By locator){
        try{
            return find(locator).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }
    }

    /**
     * Checks if certain element is displayed
     *
     * @author Leo Jakus-Mejarec
     * @param locator By locator of the element
     * @param timeout Timeout for the search
     * @return True if element is found, flase if not
     */
    public boolean isDisplayed(By locator, int timeout){
        try {
            wait = new WebDriverWait(webDriver, timeout);
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (TimeoutException e){
            return false;
        }
        return true;
    }

    /**
     * Compare current and expected title
     *
     * @author Leo Jakus-Mejarec
     * @param expectedTitle Expected title
     * @return Returns true if expected title matches current title, false if not
     */
    public boolean isTitle(String expectedTitle){
        try {
            wait.until(ExpectedConditions.titleIs(expectedTitle));
        }catch (TimeoutException e){
            return false;
        }
        return true;
    }

    public void click(WebElement element){
        element.click();
    }

    /**
     * Type wanted text into text input element
     *
     * @author Leo Jakus-Mejarec
     * @param element TextInput element
     * @param text Text that will entered
     */
    public void inputText(WebElement element, String text){
        element.sendKeys(text);
    }

    /**
     * Compare expected string and string from Web Element
     *
     * @author Leo Jakus-Mejarec
     * @param locator Text element By locator
     * @param expectedText Expected text that locator will be compared to
     * @return True if text matches expected
     */
    public boolean compareText(By locator, String expectedText){
        String currentText;
        currentText = find(locator).getText();
        if(currentText.equals(expectedText)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Get text in certain element
     *
     * @author Leo Jakus-Mejarec
     * @param locator Wanted element locator
     * @return Text within wanted element
     */
    public String getText(By locator){
        return find(locator).getText();
    }

}
