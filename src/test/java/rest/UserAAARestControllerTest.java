package rest;

import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;
import static rest.RestControllerTestUtility.*;

public class UserAAARestControllerTest extends AbstractTest  {

    private String fakeJSON="{\"createdAt\":\"2019/11/24 00:00:00\",\"dateOfBirth\":\"1991/1/2\",\"username\":\"fake\",\"password\":\"fake\"}";
    private String brokenJSON="{\"createdAt\":\"broken\",\"dateOfBirth\":\"broken\",\"username\":\"broken\",\"password\":\"broken\"}";
    private String cameraJSON="{\"id\":\"\",\"name\":\"Kamera1\",\"videos\":[{\"id\":null,\"location\":\"Agram\"}]}";
    private String registerURL="/api/user/register";
    private String loginURL="/api/user/login";


    @BeforeMethod
    public void setUp() {
//        RestControllerTestUtility.setup();
//        Not needed for these tests
        System.out.println("Starting tests: UserAAARestControllerTest");
    }

    @AfterMethod
    public void teardown(){
        System.out.println("Starting Teardown");
        RestControllerTestUtility.teardown();
    }


    @Test
    public void registerTest() {
        int result = postRequest(registerURL, registerJSON);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void falseRegisterTest()  {
        int result = postRequest(registerURL, brokenJSON);
        assertTrue(result == statusFail);
    }

    @Test
    public void doubleRegisterTest(){
        postRequest(registerURL, registerJSON);
        int result = postRequest(registerURL, registerJSON);
        assertTrue(result == statusFail);
    }

    @Test
    public void bulkRegisterTest(){
        int bulk=1000;
        for(int i=0; i<bulk; i++){
            postRequest(registerURL, brokenJSON);
        }
        int result = postRequest(registerURL, registerJSON);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void loginTest()  {
        postRequest(registerURL, registerJSON);
        int result = postRequest(loginURL, defaultLoginJSON);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void fakeLoginTest()  {
        postRequest(registerURL, registerJSON);
        int result = postRequest(loginURL, fakeJSON);
        assertTrue(result == statusFail);
    }

    @Test
    public void loginTokenExistsTest()  {
        postRequest(registerURL, registerJSON);
        String result = postResponseBody(loginURL, defaultLoginJSON);
        assertTrue(!result.isEmpty());
    }

    @Test
    public void correctTokenFromLoginTest() {
        postRequest(registerURL, registerJSON);
        String localToken = getToken(loginURL, defaultLoginJSON);
        String result = getRequestWithToken("/admin/get/cameras", localToken).toString();
        assertTrue(result.equals("[]"));
    }

    @Test
    public void bulkLoginSingleUserTest(){
        int bulk=1000;
        postRequest(registerURL, registerJSON);
        for(int i=0; i<bulk; i++){
            postRequest(loginURL, fakeJSON);
        }
        int result = postRequest(loginURL, fakeJSON);
        assertTrue(result == statusFail);
    }

    @Test
    public void bulkLoginDoubleUserTest() {
        int bulk = 1000;
        postRequest(registerURL, registerJSON);
        for (int i = 0; i < bulk; i++) {
            postRequest(loginURL, fakeJSON);
        }
        int result = postRequest(loginURL, defaultLoginJSON);
        assertTrue(result == statusSuccess);
    }
    //TODO: provjere bez tokena!!!

}
