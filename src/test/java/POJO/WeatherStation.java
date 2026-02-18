package POJO;

public class WeatherStation {
    private String external_id;
    private String name;
    private double latitude;
    private double longitude;
    private double altitude;

    // Getters and Setters
    public String getExternal_id() { return external_id; }
    public void setId(String external_id) { this.external_id = external_id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getAltitude() { return altitude; }
    public void setAltitude(double altitude) { this.altitude = altitude; }
}