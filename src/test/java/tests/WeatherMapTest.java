package tests;

import org.example.csv.CSVUtils;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import verifications.WeatherMapVerification;

import java.util.Iterator;
import java.util.List;

public class WeatherMapTest {

    WeatherMapVerification weatherMapVerification = new WeatherMapVerification("http://api.openweathermap.org");

    @DataProvider(name = "stationData")
    public Iterator<Object[]> stationDataProvider() {
        return CSVUtils.readCSV("src/test/resources/WeatherStations.csv");
    }

    @Test(groups = {
            "WeatherSuite"  }, enabled=true, priority = 1, description = "Verify API call Without API key")
    public void foreCastAPIValidationTest() {
        weatherMapVerification.forCastAPI();


    }

    @Test(groups = {
            "WeatherSuite"  }, enabled=true, priority = 2, description = "Register new weather stations using data from CSV",
            dataProvider = "stationData")
    public void registerWeatherStationTest(String external_id, String name, String latitude, String longitude, String altitude) {
        weatherMapVerification.registerStationAPI(external_id, name, latitude, longitude, altitude);
    }




    @Test(groups = {
            "WeatherSuite"  }, enabled=true, priority = 3, description = "Get the Registered station details ")
    public void getRegisterWatherStationTest() {
        List<String> registeredIDs= weatherMapVerification.getRegisterdStationDetails();
        Reporter.log("Registered IDs:"+registeredIDs,true);

    }


    @Test(groups = {
            "WeatherSuite"  }, enabled=true, priority = 4, description = "Deleting the existing  stations and repeating the same step. ")
    public void deleteStation() {
        weatherMapVerification.deleteAllStationDetails();

    }

}