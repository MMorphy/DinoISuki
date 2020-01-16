package rest;

import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;
import static rest.RestControllerTestUtility.*;

public class MyUserRestControllerTest {

    private String fakeJson="{\"userDto\":{\"dateOfBirth\":\"2025/10/10\",\"username\":\"zdrafko\",\"password\":\"newPass\"},\"contactInfoDto\":{\"telephoneNumber\":\"666666\",\"email\":\"malformedgmail@com\"}}";
    private String brokenJson="{\"userDto\":{\"dateOfBirth\":\"\",\"username\":\"\",\"password\":\"\"},\"contactInfoDto\":{\"telephoneNumber\":\"\",\"email\":\"\"}}";
    private String myUserJson= "{\"userDto\":{\"dateOfBirth\":\"1995/10/10\",\"username\":\"defaultUser\",\"password\":\"newPass\"},\"contactInfoDto\":{\"telephoneNumber\":\"060222\",\"email\":\"newdefaultuser101@gmail.com\"}}";
    private String myUserURL="/api/myUser/update/user";
    private String successMsg = "User updated!";
    private String token;

    @BeforeMethod
    public void setup(){

        token = RestControllerTestUtility.setup();
        System.out.println("Token je: " + token);
    }

    @AfterMethod
    public void teardown(){
        RestControllerTestUtility.teardown();
    }

    @Test
    public void updateUserTest(){
        int result = postWithToken(myUserURL, myUserJson, token);
        assertTrue(result == statusSuccess, "Failed to update user as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void updateUserWithBodyTest(){

        String result = postWithTokenBody(myUserURL, myUserJson, token);
        assertTrue(result.contains(successMsg), "Failed to update user as expected, response message not correct: " + result);
    }

    @Test
    public void updateFalseUserTest(){
        int result = postWithToken(myUserURL, fakeJson, token);
        assertTrue(result == statusFail, "False user update failed with wrong code: " + result);
    }

    @Test
    public void invalidUpdateUserJsonTest(){
        int result = postWithToken(myUserURL, brokenJson, token);
        assertTrue(result == statusFail, "Invalid user update failed with wrong code: " + result);
    }

    @Test
    public void noResponseUpdateUserTest(){
        int result = postWithToken(myUserURL, "", token);
        assertTrue(result == statusFail, "Empty user update failed with wrong code: " + result);
    }

    @Test
    public void doubleUpdateUserTest(){
        postWithToken(myUserURL, myUserJson, token);
        int result = postWithToken(myUserURL, myUserJson, token);
        assertTrue(result == statusSuccess, "Double user update failed with wrong code: " + result); //TODO: change this when functionality changes
    }

    @Test
    public void bulkUpdateUserTest(){
        int bulk=1000;
        for(int i=0; i<bulk; i++){
            String result = postWithTokenBody(myUserURL, myUserJson, token);
            assertTrue(result.contains(successMsg), "Failed in iteration: " + i);
        }
    }


}
