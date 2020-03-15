package selenium.pageobjects;


import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static selenium.config.Constants.*;

public class BasePageObject {

    private WebDriver webDriver;
    private WebDriverWait wait;


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
            this.webDriver.get(url);
    }

    private WebElement find(By locator){
        return this.webDriver.findElement(locator);
    }

    /**
     * Returns a list of elements defined by the locator
     *
     * @author Leo Jakus-Mejarec
     * @param locator By locator
     * @return
     */
    public List<WebElement> findList(By locator) {
        return this.webDriver.findElements(locator);
    }

    /**
     * Click a web element
     *
     * @param locator By locator
     */
    public void click(By locator){
        find(locator).click();
    }

    /**
     * Type wanted text into text input element
     *
     * @author Leo Jakus-Mejarec
     * @param locator TextInput element By locator
     * @param text Text that will entered
     */
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

    public boolean isNotDisplayed(By locator, int timeout){
        try {
            wait = new WebDriverWait(webDriver, timeout);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
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

        System.out.println("Title is: " + webDriver.getTitle());
        try {
            wait.until(ExpectedConditions.titleIs(expectedTitle));
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
    public boolean isTitle(String expectedTitle, int timeout){
        try {
            wait = new WebDriverWait(webDriver, timeout);
            wait.until(ExpectedConditions.titleIs(expectedTitle));
        }catch (TimeoutException e){
            return false;
        }
        return true;
    }

    /**
     * Click a web element
     *
     * @param element Element
     */
    public void click(WebElement element){
        element.click();
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

        if(!isDisplayed(locator, 5)){
            System.out.println("Could not find wanted element: " + locator.toString());
            return false;
        }
        String currentText;
        currentText = find(locator).getText();
        if(currentText.contains(expectedText)){
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

    /**
     * Select a certain item from dropdown menu by its value
     *
     * @author Leo Jakus-Mejarec
     * @param locator Dropdown By locator
     * @param visibleText Dropdown value
     */
    public void dropDownByText(By locator, String visibleText){
        Select tmpSelect = new Select(find(locator));
        tmpSelect.selectByVisibleText(visibleText);
    }

    /**
     * Compare baseline screenshot with current screenshot
     *
     * @param baselinePic Baseline element screenshot
     * @param currentPic Current element screenshot
     * @return True if screenshots are matching, false if not
     */
    public boolean compareImage(File baselinePic, File currentPic){
        try {
            // take buffer data from botm image files //
            BufferedImage biA = ImageIO.read(baselinePic);
            DataBuffer dbA = biA.getData().getDataBuffer();
            int sizeA = dbA.getSize();
            BufferedImage biB = ImageIO.read(currentPic);
            DataBuffer dbB = biB.getData().getDataBuffer();
            int sizeB = dbB.getSize();
            // compare data-buffer objects //
            if(sizeA == sizeB) {
                for(int i=0; i<sizeA; i++) {
                    if(dbA.getElem(i) != dbB.getElem(i)) {
                        return false;
                    }
                }
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            System.out.println("Failed to compare image files ...");
            return  false;
        }
    }

    /**
     * Take screenshot of wanted element using By locator
     *
     * @param location By locator of element
     * @return File - screenshot of element
     */
    public File getElementScreenshot(By location){
        BufferedImage fullImg = null;
        WebElement ele = webDriver.findElement(location);

        // Get entire page screenshot
        File screenshot = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
        try {
            fullImg = ImageIO.read(screenshot);
        }catch (IOException e){
            System.out.println("Failed to take screenshot ...");
        }
        // Get the location of element on the page
        Point point = ele.getLocation();

        // Get width and height of the element
        int eleWidth = ele.getSize().getWidth();
        int eleHeight = ele.getSize().getHeight();

        // Crop the entire page screenshot to get only element screenshot
        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
                eleWidth, eleHeight);
        try {
            ImageIO.write(eleScreenshot, "png", screenshot);
        }catch (IOException e){
            System.out.println("Failed to take screenshot ...");
        }
        return screenshot;
    }

    /**
     * Get baseline screenshot for element
     * @param locator By locator for element
     * @return File - baseline screenshot of wanted element
     */
    public File getBaselineScreenshot(By locator){
        return new File("customLocation" + locator); //TODO: change to specific path
    }

    /**
     *
     */
    public boolean isElementUpdated(By locator, String attribute, String prevValue){
        wait.pollingEvery(Duration.ofSeconds(1));
        try {
            wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(locator, attribute, prevValue)));
        }catch (Exception e){
            return false;
        }
        return true;
    }


    public boolean openSidebar(){
        click(mainDropDown);
        return isDisplayed(homeBtn, 5); //home should always be in the dropdown
    }

    public boolean closeSidebar(){
        openSidebar();
        isDisplayed(closeSidebarBtn);

        click(closeSidebarBtn);
        return isNotDisplayed(homeBtn, 10); //home should always be in the dropdown
    }

}
