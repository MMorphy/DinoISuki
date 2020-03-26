package rest;

import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;
import static rest.RestControllerTestUtility.*;

public class UserManagementUpdateDeleteControllerTest {

    private String updateUserJSON = "{\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"dateOfBirth\":\"1990-01-01\",\"username\":\"defaultUser\",\"password\":\"thisIsThePassword\",\"enabled\":\"true\"}";
    private String fakeUpdateJSON = "{\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"dateOfBirth\":\"1990-01-01\",\"username\":\"miljenko\",\"password\":\"miljenko\",\"enabled\":\"true\"}";
    private String changePassJson = "{\"username\":\"defaultUser\",\"oldPassword\":\"thisIsThePassword\",\"newPassword\":\"defaultPass\"}";
    private String contactInfoJSON = "{\"username\":\"defaultUser\",\"telephoneNumber\":\"6666666662\",\"email\":\"mile.ljubitelj@diska.com\"}";
    private String addUserRoleJSON = "{\"username\":\"defaultUser\",\"role\":\"role_user\"}";
    private String addAdminRoleJSON = "{\"username\":\"defaultUser\",\"role\":\"role_admin\"}";
    private String updateUserURL = "/api/user/updateUser";
    private String deleteUserURL = "/api/user/deleteUser";
    private String getUserURL = "/api/user/getUser/defaultUser";
    private String changePassURL = "/api/user/changePassword";
    private String contactInfoURL= "/api/user/saveContactInfo";
    private String deleteContactInfoURL = "/api/user/deleteContactInfo";
    private String getUserInfoURL = "/api/user/getContactInfo/";
    private String addUserRoleURL = "/api/user/addUserRole";
    private String revokeUserRoleURL = "/api/user/revokeUserRole";
    private String getUserRolesURL = "/api/user/getUserRoles?username=";
    private String successUpdateMsg = "User updated!";
    private String nonExistantMsg = "User doesn't exist!";
    private String successDeleteMsg = "User deleted!";
    private String successPassMsg = "Password updated!";
    private String incorrectOldPassMsg = "Incorrect old password!";
    private String contactInfoPassMsg = "Contact Information saved successfully";
    private String contactInfoDeleteMsg = "Contact Information deleted successfully";
    private String findUserFailMsg = "Unable to find user";
    private String userRoleUpdatedMsg = "User roles updated!";
    private String userRoleExistsMsg = "User already has provided role";




    @BeforeMethod
    public void setup(){
        token = RestControllerTestUtility.setup();
        postRequest(loginURL, defaultLoginJSON);
        System.out.println("Token je: " + token);
    }

    @AfterMethod
    public void teardown(){
        System.out.println("Starting Teardown");
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

    //TODO: include when fixed
    @Test
    public void doubleDeleteUserTest(){
        postWithToken(deleteUserURL, updateUserJSON, token);
        int result = postWithToken(deleteUserURL, updateUserJSON, token);
        assertTrue(result == statusFail, "Failed to delete user as expected, response code not correct! Response code: " + result);
    }

    @Test
    public void getUserTest(){
        String result = getRequestWithToken(getUserURL, token).toString();
        assertTrue(result.contains("1994-10-10"), "Result is: " + result );
    }

    //TODO: double check this
    @Test
    public void getNonExistingUserTest(){
        postWithTokenBody(deleteUserURL, updateUserJSON, token);
        String result = getRequestWithToken(getUserURL, token).toString();
        assertTrue(result.contains("No such user!"), "Result is: " + result); //TODO: update when fixed
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
        assertTrue(result == statusFail, "Changes password unexpectedly, response code not correct! Response code: " + result);
    }

    @Test
    public void doubleChangePassWithBodyTest(){
        postWithToken(changePassURL, changePassJson, token);
        String result = postWithTokenBody(changePassURL, changePassJson, token);
        assertTrue(result.contains(incorrectOldPassMsg), "Changes password unexpectedly, response code not correct! Response code: " + result);
    }

    @Test
    public void saveContactInfoTest(){
        int  result = postWithToken(contactInfoURL, contactInfoJSON, token);
        assertTrue(result== statusSuccess, "Result is: " + result);
    }

    @Test
    public void saveContactInfoWithBodyTest(){
        String result = postWithTokenBody(contactInfoURL, contactInfoJSON, token);
        assertTrue(result.contains(contactInfoPassMsg), "Result is: " + result);
    }

    @Test
    public void bulkContactInfoTest(){
        int bulk = 1000;
        System.out.println("Running bulk of 1000 contact info update requests...");
        for (int i = 0; i < bulk; i++) {
            postWithTokenBody(contactInfoURL, contactInfoJSON, token);
        }
        System.out.println("...done!");
        int result = postRequest(contactInfoURL, contactInfoJSON);
        assertTrue(result == statusSuccess, "Result is: " + result);
    }

    @Test
    public void deleteContactInfoTest(){
        postWithToken(contactInfoURL, contactInfoJSON, token);
        int  result = postWithToken(deleteContactInfoURL, contactInfoJSON, token);
        assertTrue(result== statusSuccess, "Result is: " + result);
    }

    @Test
    public void deleteContactInfoWithBodyTest(){
        postWithToken(contactInfoURL, contactInfoJSON, token);
        String result = postWithTokenBody(deleteContactInfoURL, contactInfoJSON, token);
        assertTrue(result.contains(contactInfoDeleteMsg), "Result is: " + result);
    }

    @Test
    public void bulkDeleteContactInfoTest(){
        int bulk = 1000;
        System.out.println("Running bulk of 1000 contact info delete requests...");
        for (int i = 0; i < bulk; i++) {
            postWithToken(contactInfoURL, contactInfoJSON, token);
            int r = postWithToken(deleteContactInfoURL, contactInfoJSON, token);
            if (r!=statusSuccess)
                System.out.println("Failed in iteration # " + i);
        }
        System.out.println("...done!");
        postWithToken(contactInfoURL, contactInfoJSON, token);
        int  result = postWithToken(deleteContactInfoURL, contactInfoJSON, token);
        assertTrue(result== statusSuccess, "Result is: " + result);
    }

    @Test
    public void getContactTest() {
        postWithToken(contactInfoURL, contactInfoJSON, token);
        String result = getRequestWithToken(getUserInfoURL+"defaultUser", token).toString();
        assertTrue(result.contains("defaultUser"), "Result is: " + result);
    }

    @Test
    public void getNonExistingContactTest(){
        String result = getRequestWithToken(getUserInfoURL+"miljenko", token).toString();
        assertTrue(result.contains(findUserFailMsg), "Result is: " + result);
    }

//    /uploadProfilePhoto - not tested automatically
//    /getProfilePhoto - not tested automatically

    @Test
    public void addNewUserRoleTest(){
        postWithToken(revokeUserRoleURL, addUserRoleJSON, token);
        String result = postWithTokenBody(addUserRoleURL, addUserRoleJSON, token );
        assertTrue(result.contains(userRoleUpdatedMsg), "Result is: " + result);
    }

    //TODO: add admin role via sql query
//    @Test
//    public void addNewAdminRoleTest(){
//        String result = postWithTokenBody(addUserRoleURL, addAdminRoleJSON, token );
//        assertTrue(result.contains(userRoleUpdatedMsg));
//    }

    @Test
    public void addExistingUserRoleTest(){
        String result = postWithTokenBody(addUserRoleURL, addUserRoleJSON, token );
        assertTrue(result.contains(userRoleExistsMsg), "Result is: " + result);
    }

    //TODO: add admin role via sql query
//    @Test
//    public void addExistingAdminRoleTest(){
//        postWithTokenBody(updateUserURL, addAdminRoleJSON, token );
//        String result = postWithTokenBody(updateUserURL, addAdminRoleJSON, token );
//        assertTrue(result.contains(userRoleExistsMsg));
//    }

    @Test
    public void revokeUserRoleTest(){
        String result = postWithTokenBody(revokeUserRoleURL, addUserRoleJSON, token );
        assertTrue(result.contains(userRoleUpdatedMsg), "Result is: " + result);
    }

    //TODO: add admin role via sql query
//    @Test
//    public void revokeAdminRoleTest(){
//        postWithTokenBody(updateUserURL, addAdminRoleJSON, token );
//        String result = postWithTokenBody(revokeUserRoleURL, addAdminRoleJSON, token );
//        assertTrue(result.contains(userRoleUpdatedMsg));
//    }

    @Test
    public void getUserRolesTest(){
        String result = getRequestWithToken(getUserRolesURL+"defaultUser", token).toString();
        assertTrue(result.contains("role_user"), "Result is: " + result);
    }

    @Test
    public void getNonExistingUserRolesTest(){
        String result = getRequestWithToken(getUserRolesURL+"miljenko", token).toString();
        assertTrue(result.contains(findUserFailMsg), "Result is: " + result);
    }
    //TODO: provjere bez tokena!!!
}
