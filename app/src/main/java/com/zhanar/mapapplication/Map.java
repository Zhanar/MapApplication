package com.zhanar.mapapplication;

/**
 * Created by Жанар on 25.05.2016.
 */
public class Map {

    private Integer Id;
    private String name;
    private Double latitude;
    private Double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public Integer getId() {
        return Id;
    }
}
