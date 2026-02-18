package verifications;

import POJO.WeatherStation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;

import java.util.List;

import static io.restassured.RestAssured.post;


public class WeatherMapVerification {

    String appId = "";
    String id = "524901";
    private static final String BASE_STATION_URI = "/data/3.0/stations?APPID=%s";
    private static final String DELETE_STATION_URI="/data/3.0/stations/%s?APPID=%s";


    public String forecastUrl = "/data/2.5/forecast?id=%s&appid=%s";

    public String  apiKey="5ab20332fe37c18e7b1b4f786e5c572b";
    public static RequestSpecification request = null;
    public static final String GENERIC_JSON_PATH = System.getProperty("user.dir") + "/src/test/resources/WeatherMap/";
    protected static final String APIKEY = "76a78216af765a6392fcf44d4712c363";

    public WeatherMapVerification(String uri) {
        RestAssured.baseURI = uri;
    }

    /**
     * Forcast API
     */
    public void forCastAPI() {

        Reporter.log("Forecast API request  :" + RestAssured.baseURI + String.format(forecastUrl, id, appId), true);
        Response response = post(String.format(forecastUrl, id, appId));
        Reporter.log(response.asString(), true);
        Assert.assertTrue(response.statusCode() == 401, "Response status code was not displayed as 401  || Actual:" + response.statusCode());
        String getMessage = JsonPath.read(response.asString(), "message");

        Assert.assertTrue(getMessage.contains("Invalid API key"), "Error message was not displayed.");

    }

    /**
     * Regsiration station API
     *
     * @param externalId
     * @param name
     * @param latitude
     * @param longitude
     * @param altitude
     */
    public  void registerStationAPI(String externalId,String name, String latitude,String longitude,String altitude) {
        Reporter.log("Registration request:" + RestAssured.baseURI +String.format(BASE_STATION_URI,apiKey),true);

        //Response object
        JSONObject requestParams= new JSONObject();
        requestParams.put("external_id",externalId);
        requestParams.put("name",name);
        requestParams.put("latitude",Double.parseDouble(latitude));
        requestParams.put("longitude",Double.parseDouble(longitude));
        requestParams.put("altitude",Double.parseDouble(altitude));


        RequestSpecification request = RestAssured.given().header("Content-Type", "application/json;charset=UTF-8");
        Response response = request.body(requestParams).post(String.format(BASE_STATION_URI,apiKey));
        Reporter.log("Request Body: " + requestParams, true);

        Reporter.log("Response status code:"+response.statusCode(),true);
        Assert.assertTrue(response.statusCode() == 201, "Response status code was not displayed as 201  || Actual:" + response.statusCode());
        Reporter.log("response:"+response.asString(),true);

    }

    /**
     *
     * Get Registered station details
     * @return
     */
    public List<String> getRegisterdStationDetails(){
        List<String> arrayOfIDs;  // Array to keep all IDs.
        Reporter.log("Registration request:" + RestAssured.baseURI +String.format(BASE_STATION_URI,apiKey),true);
        RequestSpecification request = RestAssured.given().header("Content-Type", "application/json");
        Response response = request.get(String.format(BASE_STATION_URI,apiKey));
        Reporter.log("Response status code:"+response.statusCode(),true);
        Assert.assertTrue(response.statusCode() == 200, "Response status code was not displayed as 200  || Actual:" + response.statusCode());
        Reporter.log("Response:"+response.asString(),true);
        arrayOfIDs=response.getBody().jsonPath().get("id");
        return arrayOfIDs;


    }

    /**
     *
     * Delete Stations
     */
    public void deleteAllStationDetails(){
        List<String> arrayOfIDs;  // Array to keep all IDs.
        // GET All IDs of stations associated with the APIKeyID
        RequestSpecification request = RestAssured.given().header("Content-Type", "application/json");
        Response getResponse = request.get(String.format(BASE_STATION_URI,apiKey));
        arrayOfIDs=getResponse.getBody().jsonPath().get("id");
        //Delete registered stations
        for (String id: arrayOfIDs){
            Reporter.log("Delete station details request:" + RestAssured.baseURI +(String.format(DELETE_STATION_URI,id,apiKey)),true);
            Reporter.log("Deleting the station details for "+id);
            Response response = request.delete(String.format(DELETE_STATION_URI,id,apiKey));
            Reporter.log("Response status code:"+response.statusCode(),true);
            Assert.assertTrue(response.statusCode() == 204, "Response status code was not displayed as 204  || Actual:" + response.statusCode());
            Reporter.log("Response:"+response.asString(),true);

            //Clean  up stations

            Response deleteRespoonse = request.delete(String.format(DELETE_STATION_URI,id,apiKey));
            Reporter.log("Response status code after cleaning up the  station:"+id+deleteRespoonse.statusCode(),true);
            Assert.assertTrue(deleteRespoonse.statusCode() == 404, "Response status code was not displayed as 404  || Actual:" + deleteRespoonse.statusCode());
            Reporter.log("Response:"+deleteRespoonse.asString(),true);
            String getMessage = JsonPath.read(deleteRespoonse.asString(), "message");
            Reporter.log("Error message :"+getMessage,true);

            Assert.assertTrue(getMessage.equalsIgnoreCase("Station not found"), "Error message was not displayed as Station not found.");

        }



    }



    /**
     * Register station API
     *
     * @param externalId
     * @param name
     * @param latitude
     * @param longitude
     * @param altitude
     */
    public void registerStationAPIWithPOJO(String externalId, String name, String latitude, String longitude, String altitude) {
        Reporter.log("Registration request:" + RestAssured.baseURI + String.format(BASE_STATION_URI, apiKey), true);

        // Create POJO for request
        WeatherStation weatherStation = new WeatherStation();
        weatherStation.setId(externalId);
        weatherStation.setName(name);
        weatherStation.setLatitude(Double.parseDouble(latitude));
        weatherStation.setLongitude(Double.parseDouble(longitude));
        weatherStation.setAltitude(Double.parseDouble(altitude));

        // Convert POJO to JSON
        ObjectMapper mapper = new ObjectMapper();
        String requestBody = null;
        try {
            requestBody = mapper.writeValueAsString(weatherStation);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Reporter.log("Request Body: " + requestBody, true);

        RequestSpecification request = RestAssured.given().header("Content-Type", "application/json;charset=UTF-8");
        Response response = request.body(requestBody).post(String.format(BASE_STATION_URI, apiKey));
        Reporter.log("Response status code:" + response.statusCode(), true);
        Assert.assertTrue(response.statusCode() == 201, "Response status code was not displayed as 201  || Actual:" + response.statusCode());
        Reporter.log("response:" + response.asString(), true);
    }




}
