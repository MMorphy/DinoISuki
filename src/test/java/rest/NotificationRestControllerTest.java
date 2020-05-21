package rest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.StringReader;

import static org.testng.Assert.assertTrue;
import static rest.RestControllerTestUtility.*;

public class NotificationRestControllerTest {

    private String addNotificationJSON = "{\"id\":null,\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"destUser\":\"defaultUser\",\"srcUser\":\"defaultUser\",\"subject\":\"Subjectum\",\"message\":\"Lorem ipsum dolor sit amet!\",\"notificationType\":\"test\"}";
    private String addMiljenkoNotificationJSON = "{\"id\":null,\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"destUser\":\"Miljenko\",\"srcUser\":\"defaultUser\",\"subject\":\"For Miljenko's eyes only\",\"message\":\"Poruka za Miljenka\",\"notificationType\":\"test\"}";
    private String addSlavkoNotificationJSON = "{\"id\":null,\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"destUser\":\"slafko\",\"srcUser\":\"slafko\",\"subject\":\"For Slavko's eyes only\",\"message\":\"Poruka za Slavka\",\"notificationType\":\"test\"}";
    private String addEmptyNotificationJSON = "{\"id\":null,\"createdAt\":\"\",\"destUser\":\"\",\"srcUser\":\"\",\"subject\":\"\",\"message\":\"\",\"notificationType\":\"\"}";
    private String updateNotificationJSON = "{\"id\":1,\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"destUser\":\"defaultUser\",\"srcUser\":\"defaultUser\",\"subject\":\"Wind of changes\",\"message\":\"Take me to the magic of the moment!\",\"notificationType\":\"test\"}";
    private String updateFakeNotificationJSON = "{\"id\":999,\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"destUser\":\"Miljenko\",\"srcUser\":\"defaultUser\",\"subject\":\"Wind of changes\",\"message\":\"Take me to the magic of the moment!\",\"notificationType\":\"test\"}";
    private String updateSlavkoNotificationJSON = "{\"id\":1,\"createdAt\":\"2019-05-30T16:19:58.016Z\",\"destUser\":\"slafko\",\"srcUser\":\"slafko\",\"subject\":\"For Slavko's eyes only\",\"message\":\"Poruka za Slavka\",\"notificationType\":\"test\"}";
    private String getNotificationsURL = "/api/notifications/getNotifications";
    private String addNotificationURL = "/api/notifications/addNotification";
    private String updateNotificationURL = "/api/notifications/updateNotification";
    private String deleteNotificationURL = "/api/notifications/deleteNotifications?notificationIdList=1";
    private String updateSuccessfulmsg = "Notification updated";
    private String invalidUsermsg = "Invalid dest user provided";
    private String notificationAddedmsg = "Notification added";

    @BeforeMethod
    public void setUp() {
        RestControllerTestUtility.setup();
        System.out.println("Starting tests: UserManagementRestControllerTest");
    }

    @AfterMethod
    public void teardown(){
        System.out.println("Starting Teardown");
        RestControllerTestUtility.teardown();
    }


    @Test
    public void addNotificationTest(){
        int result = postWithToken(addNotificationURL,addNotificationJSON, token);
        assertTrue(result == statusSuccesful, "Result is:" + result);
    }

    @Test
    public void setAddNotificationResponseTest(){
        String result = postWithTokenBody(addNotificationURL,addNotificationJSON, token);
        assertTrue(result.contains(notificationAddedmsg), "Value is:" + result);
    }

    @Test
    public void addEmptyNotificationTest() {
        int result = postWithToken(addNotificationURL, addEmptyNotificationJSON, token);
        assertTrue(result == statusFail, "Result is:" + result);
    }

    @Test
    public void getNotificationsTest() {
        postWithToken(addNotificationURL,addNotificationJSON, token);
        String result = getRequestWithToken(getNotificationsURL, token).toString();
        assertTrue(result.contains("Lorem ipsum"), "Result is:" + result);
    }

    @Test
    public void getFakeUserNotificationTest(){
        String result = postWithTokenBody(addNotificationURL, addMiljenkoNotificationJSON, token);
        assertTrue(result.contains(invalidUsermsg), "Result is:" + result);
    }

    @Test
    public void getOtherUserNotificationTest(){
        postRequest(registerURL, registerSlavkoJSON);
        postWithTokenBody(addNotificationURL, addSlavkoNotificationJSON, token);
        String result = getRequestWithToken(getNotificationsURL, token).toString();
        assertTrue(!result.contains("For Slavko's eyes only"), "Result is:" + result);
    }

    @Test
    public void updateNotificationTest(){
        postWithToken(addNotificationURL, addNotificationJSON, token);
        int result1 = postWithToken(updateNotificationURL, updateNotificationJSON, token);
        String result2 = getRequestWithToken(getNotificationsURL, token).toString();
        assertTrue(result1 == statusSuccesful && result2.contains("Wind of changes"), "Result is:" + result1);
    }

    @Test
    public void updateNotificationResponseTest(){
        postWithToken(addNotificationURL, addNotificationJSON, token);
        String result = postWithTokenBody(updateNotificationURL, updateNotificationJSON, token);
        assertTrue(result.contains(updateSuccessfulmsg), "Result is:" + result);
    }

    @Test
    public void updatefakeNotificationTest(){
        postWithToken(updateNotificationURL, updateNotificationJSON, token);
        String result = postWithTokenBody(updateNotificationURL, updateFakeNotificationJSON, token);
        assertTrue(result.contains(invalidUsermsg), "Result is:" + result);
    }

    @Test
    public void updateNotOwnedNotificationTest(){
        postRequest(registerURL, registerSlavkoJSON);
        postWithToken(addNotificationURL, addSlavkoNotificationJSON, token);
        String result = postWithTokenBody(updateNotificationURL, updateSlavkoNotificationJSON, token);
        assertTrue(!result.contains("For Slavko's eyes only"), "Result is:" + result);
    }

    //TODO: include when fixed
    @Test
    public void deleteNotificationTest(){
        postWithToken(addNotificationURL,addNotificationJSON, token);
        getRequest(deleteNotificationURL, token);
        String result = getRequestWithToken(getNotificationsURL, token).toString();
        assertTrue(!result.contains("\"id\": 1"), "Result is:" + result);
    }
}
