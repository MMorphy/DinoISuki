package rest;
    import org.testng.annotations.*;

    import static org.testng.Assert.assertFalse;
    import static org.testng.Assert.assertTrue;
    import static org.testng.AssertJUnit.assertEquals;
    import static rest.RestControllerTestUtility.*;

public class ReservationRestControllerTest extends AbstractTest {
    private String dateURL="/reservations/field/reservations/byDate";
    private String sportURL="/reservations/field/reservations/byDate";
    private String date = "2020-03-23";
    private String fieldId = "3";
    private String isReserved = "false";
    private String reserveURL ="/reservations/reserve";
    private String deleteURL = "/reservations/delete";
    private String updateURL = "/reservations/update";
    private String reserveJson = "[{\"termId\":\"1\",\"userId\":\"1\",\"fieldId\":\"1\"}]";
    private String securityToken= null;

//    @BeforeTest
//    public void setUp() {
//        super.setUp();
    //TODO: create user and get securityToken
//        TODO: make sure DB is clean
//    }


//    @AfterTest
//    public void teardown(){
//        super.teardown();
    //TODO: drop the additional user
//    TODO: clean the extra entries in DB
//    }


    @Test
    public void getDateReservationsForFieldByDateTest() {
        String result = getRequest(dateURL, date, fieldId, isReserved);
        System.out.println("result: " + result);
        assertTrue(result.contains(date));
    }
    @Test

    public void getIdReservationsForFieldByDateTest() {
        String result = getRequest(dateURL, date, fieldId, isReserved);
        System.out.println("result: " + result);
        assertTrue(result.contains(fieldId));
    }

    @Test
    public void getAvailableReservationsForFieldByDateTest() {
        String result = getRequest(dateURL, date, fieldId, isReserved);
        System.out.println("result: " + result);
        assertTrue(result.contains(isReserved));
    }

    @Test
    public void getextraReservationsForFieldByDateTest() {
        String result = getRequest(dateURL, date, fieldId, isReserved, "thisShouldNotBeHere");
        System.out.println("result: " + result);
        assertTrue(result.contains("Not Found"));
    }

    @Test
    public void getfalseReservationsForFieldByDateTest() {
        String result = getRequest(dateURL, date, "thisShouldNotBeHere", isReserved);
        System.out.println("result: " + result);
        assertTrue(result.contains("Bad Request"));
    }

    @Test
    public void getMappingTest() throws  Exception{
        String result = getRequest("/");
        System.out.println("result: " + result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void getDateReservationsForFieldBySportTest() {
        String result = getRequest(sportURL, date, fieldId, isReserved);
        System.out.println("result: " + result);
        assertTrue(result.equals(date));
    }
    @Test

    public void getIdReservationsForFieldBySportTest() {
        String result = getRequest(sportURL, date, fieldId, isReserved);
        System.out.println("result: " + result);
        assertTrue(result.equals(fieldId));
    }

    @Test
    public void getAvailableReservationsForFieldBySportTest() {
        String result = getRequest(sportURL, date, fieldId, isReserved);
        System.out.println("result: " + result);
        assertTrue(result.equals(isReserved));
    }

    @Test
    public void getextraReservationsForFieldBySportTest() {
        String result = getRequest(sportURL, date, fieldId, isReserved, "thisShouldNotBeHere");
        System.out.println("result: " + result);
        assertTrue(result.contains("Not Found"));
    }

    @Test
    public void getfalseReservationsForFieldBySportTest() {
        String result = getRequest(sportURL, date, "thisShouldNotBeHere", isReserved);
        System.out.println("result: " + result);
        assertTrue(result.contains("Bad Request"));
    }

    @Test
    public void addReservationTest() {
        int result = postRequest(reserveURL, reserveJson);
        assertTrue(result == statusCreated);
    }

    @Test
    public void addSameReservationTest() {
        postRequest(reserveURL, reserveJson);
        int result = postRequest(reserveURL, reserveJson);
        assertTrue(result == statusFail);
    }

    @Test
    public void deleteReservationTest() {
        int result = postRequest(deleteURL, reserveJson);
        assertTrue(result == statusCreated);
    }

    @Test
    public void deleteSameReservationTest() {
        postRequest(deleteURL, reserveJson);
        int result = postRequest(reserveURL, reserveJson);
        assertTrue(result == statusFail);
    }

    @Test
    public void updateReservationTest() {
        int result = postRequest(updateURL, reserveJson);
        assertTrue(result == statusCreated);
    }

    @Test
    public void updateSameReservationTest() {
        postRequest(updateURL, reserveJson);
        int result = postRequest(updateURL, reserveJson);
        assertTrue(result == statusCreated);
    }

    //TODO: handle variables!
    //TODO: add more logging in asserts!
}
