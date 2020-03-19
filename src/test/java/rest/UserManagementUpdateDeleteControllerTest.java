package rest;

import org.testng.annotations.*;

import javax.validation.constraints.AssertTrue;

import static org.testng.Assert.assertTrue;
import static rest.RestControllerTestUtility.*;

public class UserManagementUpdateDeleteControllerTest {

    //New
    private String updateUserJSON = "{\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"dateOfBirth\":\"1990-01-01\",\"username\":\"defaultUser\",\"password\":\"thisIsThePassword\",\"enabled\":\"true\"}";
    private String fakeUpdateJSON = "{\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"dateOfBirth\":\"1990-01-01\",\"username\":\"miljenko\",\"password\":\"miljenko\",\"enabled\":\"true\"}";
    private String changePassJson = "{\"username\":\"defaultUser\",\"oldPassword\":\"thisIsThePassword\",\"newPassword\":\"defaultPass\"}";
    private String updateUserURL = "/api/user/updateUser";
    private String deleteUserURL = "/api/user/deleteUser";
    private String getUserURL = "/api/user/getUser/defaultUser";
    private String changePassURL = "/api/user/changePassword";
    private String successUpdateMsg = "User updated!";
    private String nonExistantMsg = "User doesn't exist!";
    private String successDeleteMsg = "User deleted!";
    private String successPassMsg = "Password updated!";
    private String incorrectOldPassMsg = "Incorrect old password!";


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
        int result = postWithToken(updateUserURL, updateUserJSON, token);
        assertTrue(result == statusSuccess, "Failed to update user as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void updateUserWithBodyTest(){

        String result = postWithTokenBody(updateUserURL, updateUserJSON, token);
        assertTrue(result.contains(successUpdateMsg), "Failed to update user as expected, response message not correct: " + result);
    }

    @Test
    public void updateFalseUserTest(){
        int result = postWithToken(updateUserURL, fakeUpdateJSON, token);
        assertTrue(result == statusFail, "False user update failed with wrong code: " + result);
    }

    @Test
    public void updateFalseUserWithBodyTest(){
        String result = postWithTokenBody(updateUserURL, fakeUpdateJSON, token);
        assertTrue(result.contains(nonExistantMsg), "Succeded to update user , but should have failed. Response message not correct: " + result);
    }

    @Test
    public void noResponseUpdateUserTest(){
        int result = postWithToken(updateUserURL, "", token);
        assertTrue(result == statusFail, "Empty user update failed with wrong code: " + result);
    }

    @Test
    public void doubleUpdateUserTest(){
        postWithToken(updateUserURL, updateUserJSON, token);
        int result = postWithToken(updateUserURL, updateUserJSON, token);
        assertTrue(result == statusSuccess, "Double user update failed with wrong code: " + result); //TODO: change this when functionality changes
    }

    @Test
    public void bulkUpdateUserTest(){
        int bulk=1000;
        for(int i=0; i<bulk; i++){
            String result = postWithTokenBody(updateUserURL, updateUserJSON, token);
            assertTrue(result.contains(successUpdateMsg), "Failed in iteration: " + i + ", with message: " + result);
        }
    }

    @Test
    public void deleteUserTest(){
        int result = postWithToken(deleteUserURL, updateUserJSON, token);
        assertTrue(result == statusSuccess, "Failed to delete user as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void deleteUserWithBodyTest(){
        String result = postWithTokenBody(deleteUserURL, updateUserJSON, token);
        assertTrue(result.contains(successDeleteMsg), "Failed to delete user as expected, response message not correct: " + result);
    }

    @Test
    public void deleteNonExistentUserTest(){
        int result = postWithToken(deleteUserURL, fakeUpdateJSON, token);
        assertTrue(result == statusFail, "Failed to delete user as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void noResponseDeleteUserTest(){
        int result = postWithToken(deleteUserURL, "", token);
        assertTrue(result == statusFail, "Failed to delete user as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void doubleDeleteUserTest(){
        postWithToken(deleteUserURL, updateUserJSON, token);
        int result = postWithToken(deleteUserURL, updateUserJSON, token);
        assertTrue(result == statusFail, "Failed to delete user as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void getUserTest(){
        String result = getRequestWithToken(getUserURL, token).toString();
        assertTrue(result.contains("1994-10-10"));
    }

    @Test
    public void getNonExistingUserTest(){
        postWithTokenBody(deleteUserURL, updateUserJSON, token);
        String result = getRequestWithToken(getUserURL, token).toString();
        assertTrue(result.contains("No such user!")); //TODO: update when fixed
    }

    @Test
    public void changePassTest(){
        int result = postWithToken(changePassURL, changePassJson, token);
        assertTrue(result == statusSuccess, "Failed to change password as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void changePassWithBodyTest(){
        String result = postWithTokenBody(changePassURL, changePassJson, token);
        assertTrue(result.contains(successPassMsg), "Failed to change password as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void changePassEmptyTest(){
        int result = postWithToken(changePassURL, "", token);
        assertTrue(result == statusFail, "Failed to change password as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void doubleChangePassTest(){
        postWithToken(changePassURL, changePassJson, token);
        int result = postWithToken(changePassURL, changePassJson, token);
        assertTrue(result == statusSuccess, "Changes password unexpectedly, response code not correct! Response code: " + result);
    }

    @Test
    public void doubleChangePassWithBodyTest(){
        postWithToken(changePassURL, changePassJson, token);
        String result = postWithTokenBody(changePassURL, changePassJson, token);
        assertTrue(result.contains(incorrectOldPassMsg), "Changes password unexpectedly, response code not correct! Response code: " + result);
    }



}
