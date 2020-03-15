package selenium.pageobjects;

import org.openqa.selenium.By;

import java.io.File;

import static selenium.config.Constants.*;

public class SportsPageObject extends BasePageObject{

    //Logged in
    By footballCollapse = By.id("football");
    By tennisCollapse = By.id("tennis");
    By locationNameTextField = By.className("locationName");
    By locationInfoTextField = By.className("locationInfo");
    By locationPic = By.className("locationPic");
    By reserveTermBtn = By.className("reserveTerm");
    By reservationDiv = By.id("reservation");
    By datePickSelect = By.id("datePick");
    By teamSelect = By.id("team");
    By termTable = By.id("terms");
    By reserveBtn = By.id("reserve");


    public String getSportName(By locator) {
        return getText(locator);
    }

    public boolean compareLocationPicture(){
        //TODO
        return false;
    }
    public void openSidebarSports(){
        click(mainDropDown);
        isDisplayed(homeBtn); //home should always be in the dropdown
    }

    public void scrollGallery(){
        //TODO
    }

    public void getLocationPic(){
        //TODO
    }

    public boolean compareLogo(){
        File baselineScreenshot = getBaselineScreenshot(logoPic);
        File currentScreenShot = getElementScreenshot(logoPic);
        return compareImage(baselineScreenshot, currentScreenShot);
    }

    public boolean compareLocation(String expectedLocation){
        return compareText(locationNameTextField, expectedLocation);
    }

    public boolean compareInfo(String expectedInfo){
        return compareText(locationInfoTextField, expectedInfo);

    }

    public void reserveTerm(String date, String teamName){
        click(reserveTermBtn);
        //TODO: check oce li biti nova stranica ili popup
        isDisplayed(reservationDiv);
        dropDownByText(datePickSelect, date);
        dropDownByText(teamSelect, teamName);
        //TODO: tableHandling
        click(reserveBtn);
    }


}
