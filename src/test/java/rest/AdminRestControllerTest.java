package rest;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static rest.RestControllerTestUtility.*;

public class AdminRestControllerTest extends AbstractTest {

    private String updateCameraJSON = "{\"id\":1,\"name\":\"camUp\",\"videos\":[{\"id\":1,\"location\":\"/path1\"}]}";
    private String nonExistingCameraJSON = "{\"id\":56789,\"name\":\"nonExistant\",\"videos\":[{\"id\":1,\"location\":\"/path1\"}]}";
    private String createCameraJSON = "{\"id\":\"\",\"name\":\"camName\",\"videos\":[{\"id\":null,\"location\":\"\"}]}";
    private String createInvalidCameraJSON = "{\"id\":\"\",\"name\":\"\",\"videos\":[{\"id\":,\"location\":\"\"}]}";
    private String deleteCameraUrl = "/admin/delete/camera";
    private String createLocationUrl = "/admin/create/location";
    private String createCameraUrl = "/admin/create/camera";
    private String getAllCamerasUrl = "/admin/get/cameras";
    private String updateCameraUrl = "/admin/update/camera";


    @BeforeTest
    public void setup(){

        token = RestControllerTestUtility.setup();
        System.out.println("Token je: " + token);
    }

    @AfterTest
    public void teardown(){
        RestControllerTestUtility.teardown();
    }


    @Test
    public void createCameraTest() {
        int result = postWithToken(createCameraUrl, createCameraJSON, token);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void createInvalidCameraTest() {
        int result = postWithToken(createCameraUrl, createInvalidCameraJSON, token);
        assertTrue(result == statusFail);
    }

    @Test
    public void doubleCreateCameraTest() {
        postWithToken(createCameraUrl, createCameraJSON, token);
        int result = postWithToken(createCameraUrl, createCameraJSON, token);
        assertTrue(result == statusSuccess); // should this fail?
    }

    @Test
    public void getAllCameras() {

        String result = getRequest(getAllCamerasUrl);
        assertTrue(result.equals("")); //may be null
    }

    @Test
    public void updateCamera() {
        int result = postWithToken(updateCameraUrl, updateCameraJSON, token);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void updateSameCamera() {
        postWithToken(updateCameraUrl, updateCameraJSON, token);
        int result = postWithToken(updateCameraUrl, updateCameraJSON, token);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void updateNonExistingCamera() {
        int result = postWithToken(updateCameraUrl, nonExistingCameraJSON, token);
        assertTrue(result == statusSuccess); //Should this fail?
    }

    @Test
    public void deleteCameraUrl() {
        int result = postWithToken(deleteCameraUrl, "1", token);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void deleteNonExistingCameraUrl() {
        int result = postWithToken(deleteCameraUrl, "0", token);
        assertTrue(result == statusFail); //if frontend breaks this should return false
    }

    @Test
    public void doubleDeleteCamera() {
        postWithToken(deleteCameraUrl, "1", token);
        int result = postWithToken(deleteCameraUrl, "1", token);
        assertTrue(result == statusFail);
    }

    @Test
    public void deleteNothing() {
        int result = postWithToken(deleteCameraUrl, "", token);
        assertTrue(result == statusFail);
    }

    @Test
    public void createFalseLocation() {
        int result = postWithToken(createLocationUrl, "", token);
        assertTrue(result == statusFail);
    }

    @Test
    public void createLocation() {
        int result = postWithToken(createLocationUrl, "camera", token);
        assertTrue(result == statusSuccess);
    }

}
