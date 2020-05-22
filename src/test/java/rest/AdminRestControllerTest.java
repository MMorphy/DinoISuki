package rest;

import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;
import static rest.RestControllerTestUtility.*;

public class AdminRestControllerTest extends AbstractTest {

    private String updateCameraJSON = "{\"id\":1,\"name\":\"camUp\",\"videos\":[]}";
    private String nonExistingCameraJSON = "{\"id\":56789,\"name\":\"nonExistant\",\"videos\":[{\"id\":1,\"location\":\"/path1\"}]}";
    private String createCameraJSON = "{\"id\":\"\",\"name\":\"camName\",\"videos\":[{\"id\":null,\"location\":\"\"}]}";
    private String createInvalidCameraJSON = "{\"id\":\"\",\"name\":\"\",\"videos\":[{\"id\":,\"location\":\"\"}]}";
    private String deleteCameraUrl = "/api/admin/deleteCamera";
    private String createLocationUrl = "/api/admin/create/location";
    private String createCameraUrl = "/api/admin/addCamera";
    private String getAllCamerasUrl = "/api/admin/getCameras";
    private String updateCameraUrl = "/api/admin/updateCamera";


    @BeforeMethod
    public void setup(){

        token = RestControllerTestUtility.setup();
        System.out.println("Token je: " + token);
    }

    @AfterMethod
    public void teardown(){
        System.out.println("DEBUG:U teardownu sam!"); //TODO: remove
        RestControllerTestUtility.teardown();
    }


    @Test
    public void createCameraTest() {
        int result = postWithToken(createCameraUrl, createCameraJSON, token);
        assertTrue(result == statusCreated, "Result is: " + result);
    }

    @Test
    public void createInvalidCameraTest() {
        int result = postWithToken(createCameraUrl, createInvalidCameraJSON, token);
        assertTrue(result == statusFail, "Result is: " + result);
    }

    @Test
    public void doubleCreateCameraTest() {
        postWithToken(createCameraUrl, createCameraJSON, token);
        int result = postWithToken(createCameraUrl, createCameraJSON, token);
        assertTrue(result == statusCreated, "Result is: " + result); // should this fail?
    }

    @Test
    public void getAllCamerasTest() {
        postWithToken(createCameraUrl, createCameraJSON, token);
        String result = getRequestWithToken(getAllCamerasUrl, token).toString();
        assertTrue(result.contains("camName"), "Result is: " + result); //may be null
    }

    @Test
    public void updateCameraTest() {
        postWithToken(createCameraUrl, createCameraJSON, token);
        int result = postWithToken(updateCameraUrl, updateCameraJSON, token);
        assertTrue(result == statusCreated, "Result is: " + result);
    }

    @Test
    public void updateSameCameraTest() {
        postWithToken(createCameraUrl, createCameraJSON, token);
        postWithToken(updateCameraUrl, updateCameraJSON, token);
        int result = postWithToken(updateCameraUrl, updateCameraJSON, token);
        assertTrue(result == statusCreated, "Result is: " + result);
    }

    @Test
    public void updateNonExistingCameraTest() {
        int result = postWithToken(updateCameraUrl, nonExistingCameraJSON, token);
        assertTrue(result == statusCreated, "Result is: " + result); //Should this fail?
    }

    @Test
    public void deleteCameraTest() {
        postWithToken(createCameraUrl, createCameraJSON, token);
        int result = postWithToken(deleteCameraUrl, "1", token);
        assertTrue(result == statusCreated, "Result is: " + result);
    }

    @Test
    public void deleteNonExistingCameraUrlTest() {
        int result = postWithToken(deleteCameraUrl, "0", token);
        assertTrue(result == statusFail, "Result is: " + result); //if frontend breaks this should return false
    }

    @Test
    public void doubleDeleteCameraTest() {
        postWithToken(createCameraUrl, createCameraJSON, token);
        postWithToken(deleteCameraUrl, "1", token);
        int result = postWithToken(deleteCameraUrl, "1", token);
        assertTrue(result == statusFail, "Result is: " + result);
    }

    @Test
    public void deleteNothingTest() {
        int result = postWithToken(deleteCameraUrl, "", token);
        assertTrue(result == statusFail, "Result is: " + result);
    }

    @Test
    public void createLocationTest() {
        int result = postWithToken(createLocationUrl, "camera", token);
         assertTrue(result == statusCreated, "Result is: " + result);
    }

    //TODO
//    /getAdminStatistics
//    /addApplicationProperty
//    /getApplicationProperty
//    /addNewQuiz
//    /getQuiz
//    /updateQuiz
//    /getNewQuizesForUser
//    /getQuizesTakenByUser
//    /addQuizAnswers
//    /getAllAnswersForQuiz
//    /getLocationWithWorkingHours
//    /saveLocationWorkingHours
    // VIDEO TEST HANDLED MANUALLY

}
