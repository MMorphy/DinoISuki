package rest;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class RestControllerTestUtility {
    private static String basicUrl ="http://localhost:8080/api/reservations";



    public static void setup(){
        //TODO:
    }

    public static void teardown(){
        //TODO:
    }

    public static JsonNode getRequest(String url) throws UnirestException {
        JsonNode body = Unirest.get(basicUrl + url)
            .asJson()
            .getBody();
        return body;
    }

    public static JsonNode getRequest(String url, String ...filter)  throws UnirestException{
        String tmpConcat = basicUrl + url;
        for(String tmpString: filter){
            tmpConcat=tmpConcat + '/' + tmpString;
        }
        System.out.println("Fetching: " +tmpConcat);
        JsonNode body = Unirest.get(tmpConcat)
                .asJson()
                .getBody();
        return body;
    }

    public static int postRequest(String tmpUrl,String postJson) throws Exception{
        int response = Unirest.post(basicUrl +tmpUrl)
                .header("Content-Type", "application/json")
                .body(postJson)
                .asJson()
                .getStatus();
        return response;
    }
}
