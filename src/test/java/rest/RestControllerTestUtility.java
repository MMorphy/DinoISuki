package rest;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONException;

public class RestControllerTestUtility {
    private static String basicUrl = System.getProperty("ip");
    private static String psqlIP = System.getProperty("psqlIP");
    protected static int statusSuccess = 201;
    protected static int statusFail = 400;
    protected static int statusUnauthorized = 401;
    protected static String defaultLoginJSON="{\"username\":\"defaultUser\",\"password\":\"thisIsThePassword\"}";
    protected static String loginURL="/api/user/login";
    protected static String registerJSON="{\"createdAt\":\"2018-05-30T16:19:58.016Z\",\"dateOfBirth\":\"1994-10-10\",\"username\":\"defaultUser\",\"password\":\"thisIsThePassword\",\"enabled\":\"false\"}";
    protected static String registerURL="/api/user/createUser";
    protected static String token;


    public static String setup(){

        System.out.println("Starting with setup");
        try{
            postRequest(registerURL, registerJSON);
            token = getToken(loginURL, defaultLoginJSON);
        }catch (Exception e){
            System.out.println("Something went wrong :(");
            e.printStackTrace();
        }
        return token;
    }

    public static void teardown(){
        //TODO: handle logout when added
        deleteAllTableData();
    }

    public static String getRequest(String url) {
        JsonNode body;
        try{
            body = Unirest.get(basicUrl + url)
                    .asJson()
                    .getBody();
        }catch (UnirestException e){
            System.out.println("Exception occured :(");
            e.printStackTrace();
            body = new JsonNode("");
        }
        return body.toString();
    }

    public static String getRequest(String url, String ...filter){
        String tmpConcat = basicUrl + url;
        for(String tmpString: filter){
            tmpConcat=tmpConcat + '/' + tmpString;
        }
        System.out.println("Fetching: " +tmpConcat);

        JsonNode body;
        try{
            body = Unirest.get(tmpConcat)
                .asJson()
                .getBody();
        }catch (UnirestException e){
            System.out.println("Exception occured :(");
            e.printStackTrace();
            body = new JsonNode("");
        }
        return body.toString();
    }

    public static JsonNode getRequestWithToken(String url, String token){
        JsonNode body;
        try{
            body = Unirest.get(basicUrl + url)
                .header("Authorization", "Bearer " + token)
                .asJson()
                .getBody();
        }catch (UnirestException e){
            System.out.println("Exception occured :(");
            e.printStackTrace();
            body = new JsonNode("");
        }
        return body;
    }

    public static int postRequest(String tmpUrl,String postJson) {

        int response;
        try{
            response = Unirest.post(basicUrl +tmpUrl)
                .header("Content-Type", "application/json")
                .body(postJson)
                .asJson()
                .getStatus();
        }catch (UnirestException e){
            System.out.println("Exception occured :(");
            e.printStackTrace();
            response = statusFail;
        }
        return response;
    }
    public static String postResponseBody(String  tmpUrl,String postJson) {
        JsonNode response;
        try {
            response = Unirest.post(basicUrl + tmpUrl)
                .header("Content-Type", "application/json")
                .body(postJson)
                .asJson()
                .getBody();
        }catch (UnirestException e){
            System.out.println("Exception occured :(");
            e.printStackTrace();
            response = new JsonNode("Exception occured");
        }
        return response.toString();
    }

    public static int postWithToken(String tmpUrl, String postJson, String token) {

        int response;
        try{
        response = Unirest.post(basicUrl + tmpUrl)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(postJson)
                .asJson()
                .getStatus();
        }catch (UnirestException e){
            System.out.println("Exception occured :(");
            e.printStackTrace();
            response = 500;
        }
        return response;
    }

    public static String postWithTokenBody(String tmpUrl, String postJson, String token) {
        JsonNode response;
        try {
             response= Unirest.post(basicUrl + tmpUrl)
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(postJson)
                    .asJson()
                    .getBody();
        }catch (UnirestException e){
            System.out.println("Exception occured :(");
            e.printStackTrace();
            response = new JsonNode("");
        }
        return response.toString();
    }

    private static void deleteAllTableData(){
        try {
            TableHandler tableHandler = new TableHandler(psqlIP);
            tableHandler.clearTables();
        }catch(Exception e){
            System.out.println("Somthing broke :(");
            System.out.println("This is what broke: ");
            e.printStackTrace();
        }
    }

    public static String getToken(String  tmpUrl,String postJson) {
        JsonNode response;
        try {
            response = Unirest.post(basicUrl + tmpUrl)
                    .header("Content-Type", "application/json")
                    .body(postJson)
                    .asJson()
                    .getBody();
        }catch (UnirestException e){
            System.out.println("Exception occured :(");
            e.printStackTrace();
            response = new JsonNode("Exception occured");
        }
        try {
            System.out.println("Json array: " + response.toString()); //TODO: remove
            return response.getArray().getJSONObject(1).getString("token");
        }catch (JSONException e){
            System.out.println("Mishandled JSON");
            return "";
        }
    }

}
