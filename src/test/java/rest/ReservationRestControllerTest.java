package rest;
    import org.testng.annotations.*;

    import static org.testng.Assert.assertFalse;
    import static org.testng.Assert.assertTrue;
    import static org.testng.AssertJUnit.assertEquals;
    import static rest.RestControllerTestUtility.*;

public class ReservationRestControllerTest extends AbstractTest {
    private String dateURL="/field/reservations/byDate";
    private String sportURL="/field/reservations/byDate";
    private String date = "2020-03-23";
    private String fieldId = "3";
    private String isReserved = "false";
    private String reserveURL ="/reserve";
    private String deleteURL = "/delete";
    private String updateURL = "/update";
    private String reserveJson = "[{\"termId\":\"1\",\"userId\":\"1\",\"fieldId\":\"1\"}]";
    private int statusSuccess = 201;
    private int statusFail = 400;
//    @BeforeTest
//    public void setUp() {
//        super.setUp();
//        TODO: make sure DB is clean
//    }


//    @AfterTest
//    public void teardown(){
//        super.teardown();
//    TODO: clean the extra entries in DB
//    }


    @Test
    public void getDateReservationsForFieldByDateTest() throws Exception {
        String result = getRequest(dateURL, date, fieldId, isReserved).getArray().getJSONObject(0).getString("date");
        System.out.println("result: " + result);
        assertEquals(result, date);
    }
    @Test

    public void getIdReservationsForFieldByDateTest() throws Exception {
        String result = getRequest(dateURL, date, fieldId, isReserved).getArray().getJSONObject(0).getString("id");
        System.out.println("result: " + result);
        assertEquals(result, fieldId);
    }

    @Test
    public void getAvailableReservationsForFieldByDateTest() throws Exception {
        String result = getRequest(dateURL, date, fieldId, isReserved).getArray().getJSONObject(0).getString("available");
        System.out.println("result: " + result);
        assertEquals(result, isReserved);
    }

    @Test
    public void getextraReservationsForFieldByDateTest() throws Exception {
        String result = getRequest(dateURL, date, fieldId, isReserved, "thisShouldNotBeHere").getArray().toString();
        System.out.println("result: " + result);
        assertTrue(result.contains("Not Found"));
    }

    @Test
    public void getfalseReservationsForFieldByDateTest() throws Exception {
        String result = getRequest(dateURL, date, "thisShouldNotBeHere", isReserved).getArray().toString();
        System.out.println("result: " + result);
        assertTrue(result.contains("Bad Request"));
    }

    @Test
    public void getMappingTest() throws  Exception{
        String result = getRequest("/").getArray().toString();
        System.out.println("result: " + result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void getDateReservationsForFieldBySportTest() throws Exception {
        String result = getRequest(sportURL, date, fieldId, isReserved).getArray().getJSONObject(0).getString("date");
        System.out.println("result: " + result);
        assertEquals(result, date);
    }
    @Test

    public void getIdReservationsForFieldBySportTest() throws Exception {
        String result = getRequest(sportURL, date, fieldId, isReserved).getArray().getJSONObject(0).getString("id");
        System.out.println("result: " + result);
        assertEquals(result, fieldId);
    }

    @Test
    public void getAvailableReservationsForFieldBySportTest() throws Exception {
        String result = getRequest(sportURL, date, fieldId, isReserved).getArray().getJSONObject(0).getString("available");
        System.out.println("result: " + result);
        assertEquals(result, isReserved);
    }

    @Test
    public void getextraReservationsForFieldBySportTest() throws Exception {
        String result = getRequest(sportURL, date, fieldId, isReserved, "thisShouldNotBeHere").getArray().toString();
        System.out.println("result: " + result);
        assertTrue(result.contains("Not Found"));
    }

    @Test
    public void getfalseReservationsForFieldBySportTest() throws Exception {
        String result = getRequest(sportURL, date, "thisShouldNotBeHere", isReserved).getArray().toString();
        System.out.println("result: " + result);
        assertTrue(result.contains("Bad Request"));
    }

    @Test
    public void addReservationTest() throws Exception {
        int result = postRequest(reserveURL, reserveJson);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void addSameReservationTest() throws Exception {
        postRequest(reserveURL, reserveJson);
        int result = postRequest(reserveURL, reserveJson);
        assertTrue(result == statusFail);
    }

    @Test
    public void deleteReservationTest() throws Exception {
        int result = postRequest(deleteURL, reserveJson);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void deleteSameReservationTest() throws Exception {
        postRequest(deleteURL, reserveJson);
        int result = postRequest(reserveURL, reserveJson);
        assertTrue(result == statusFail);
    }

    @Test
    public void updateReservationTest() throws Exception {
        int result = postRequest(updateURL, reserveJson);
        assertTrue(result == statusSuccess);
    }

    @Test
    public void updateSameReservationTest() throws Exception {
        postRequest(updateURL, reserveJson);
        int result = postRequest(updateURL, reserveJson);
        assertTrue(result == statusSuccess);
    }

    //TODO: handle variables!
    //TODO: add more logging in asserts!
}
