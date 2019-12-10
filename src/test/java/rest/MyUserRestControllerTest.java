package rest;

import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;
import static rest.RestControllerTestUtility.*;

public class MyUserRestControllerTest {

    private String fakeJson="{\"userDto\":{\"id\":\"9999999\",\"createdAt\":\"2019/11/19 00:00:00\",\"dateOfBirth\":\"1995/10/10\",\"username\":\"defaultUser\",\"password\":\"newPass\"},\"contactInfoDto\":{\"id\":\"1\",\"telephoneNumber\":\"0800091091\",\"email\":\"newdefaultuser101@gmail.com\"}}";
    private String brokenJson="{\"createdAt\":\"broken\",\"dateOfBirth\":\"broken\",\"username\":\"broken\",\"password\":\"broken\"}";
    private String myUserJson= "{\"userDto\":{\"id\":\"1\",\"createdAt\":\"2019/11/19 00:00:00\",\"dateOfBirth\":\"1995/10/10\",\"username\":\"defaultUser\",\"password\":\"newPass\"},\"contactInfoDto\":{\"id\":\"1\",\"telephoneNumber\":\"0800091091\",\"email\":\"newdefaultuser101@gmail.com\"}}";
    private String myUserURL="/api/myUser/update/user";
    private String successMsg = "User updated!";
    private String token;

    @BeforeMethod
    public void setup(){
        token = RestControllerTestUtility.setup();
    }

    @AfterMethod
    public void teardown(){
        RestControllerTestUtility.teardown();
    }

    @Test
    public void updateUserTest(){
//        postRequest("/api/user/register", registerJSON);
        int result = postWithToken(myUserURL, myUserJson, token);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void updateUserWithBodyTest(){

        String result = postWithTokenBody(myUserURL, myUserJson, token);
        assertTrue(result.contains(successMsg));
    }

    @Test
    public void updateFalseUserTest(){
        int result = postWithToken(myUserURL, fakeJson, token);
        assertTrue(result == statusSuccess); //TODO: change
    }

    @Test
    public void invalidUpdateUserJsonTest(){
        int result = postWithToken(myUserURL, brokenJson, token);
        assertTrue(result == statusSuccess);
    }


}
