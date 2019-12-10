package rest;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static rest.RestControllerTestUtility.*;

public class MyUserRestControllerTest {

    private String fakeJson="{\"userDto\":{\"id\":\"9999999\",\"createdAt\":\"2019/11/19 00:00:00\",\"dateOfBirth\":\"1995/10/10\",\"username\":\"newUser\",\"password\":\"newPass\"},\"contactInfoDto\":{\t\"id\":\"1\",\"telephoneNumber\":\"0800091091\",\"email\":\"newdefaultuser101@gmail.com\"}}";
    private String brokenJson="{\"createdAt\":\"broken\",\"dateOfBirth\":\"broken\",\"username\":\"broken\",\"password\":\"broken\"}";
    private String myUserJson= "{\"userDto\":{\"id\":\"1\",\"createdAt\":\"2019/11/19 00:00:00\",\"dateOfBirth\":\"1995/10/10\",\"username\":\"newUser\",\"password\":\"newPass\"},\"contactInfoDto\":{\t\"id\":\"1\",\"telephoneNumber\":\"0800091091\",\"email\":\"newdefaultuser101@gmail.com\"}}";
    private String myUserURL="/api/myUser/update/user";
    private String successMsg = "User updated!";
    private String token;

    @BeforeTest
    public void setup(){
        token = RestControllerTestUtility.setup();
    }

    @AfterTest
    public void teardown(){
        RestControllerTestUtility.teardown();
    }

    @Test
    public void uopdateUserTest(){
        int result = postRequest(myUserURL, myUserJson);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void uopdateUserWithBodyTest(){

        String result = postWithTokenBody(myUserURL, myUserJson, token);
        assertTrue(result.contains(successMsg));
    }

    @Test
    public void uopdateFalseUserTest(){
        int result = postRequest(myUserURL, fakeJson);
        assertTrue(result == statusSuccess); //TODO: change
    }

    @Test
    public void invalidUpdateUserJsonTest(){
        int result = postRequest(myUserURL, brokenJson);
        assertTrue(result == statusSuccess);
    }


}
