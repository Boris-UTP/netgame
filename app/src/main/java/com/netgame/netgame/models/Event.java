package com.netgame.netgame.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 11/11/17.
 */

public class Event {

    @SerializedName("idEvent")
    private int idEvent;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("dateEvent")
    private String dateEvent;

    @SerializedName("dateStart")
    private String dateStart;

    @SerializedName("dateEnd")
    private String dateEnd;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("name")
    private String name;

    public String getTitle() {
        return title;
    }

    public Event setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Event setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDateEvent() {
        return dateEvent;
    }

    public Event setDateEvent(String dateEvent) {
        this.dateEvent = dateEvent;
        return this;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public Event setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public Event setIdEvent(int idEvent) {
        this.idEvent = idEvent;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Event setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Event setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getName() {
        return name;
    }

    public Event setName(String name) {
        this.name = name;
        return this;
    }
}
