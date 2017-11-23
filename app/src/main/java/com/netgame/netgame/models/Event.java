package com.netgame.netgame.models;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 11/11/17.
 */

public class Event {

    @SerializedName("_id")
    private String idEvent;

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

    @SerializedName("assistance")
    private int assistance;

    // Indica si es tu evento
    @SerializedName("flag")
    private int flag;

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

    public Event setDateStart(String dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public Event setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public String getIdEvent() {
        return idEvent;
    }

    public Event setIdEvent(String idEvent) {
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

    public int getAssistance() {
        return assistance;
    }

    public Event setAssistance(int assistance) {
        this.assistance = assistance;
        return this;
    }

    public int getFlag() {
        return flag;
    }

    public Event setFlag(int flag) {
        this.flag = flag;
        return this;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("idEvent", idEvent);
        bundle.putString("title", title);
        bundle.putString("description", description);
        bundle.putString("dateEvent", dateEvent);
        bundle.putString("dateStart", dateStart);
        bundle.putString("dateEnd", dateEnd);
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putString("name", name);
        bundle.putInt("assistance", assistance);
        bundle.putInt("flag", flag);
        return bundle;
    }

    public static Event from(Bundle bundle){
        Event event = new Event();
        event.setIdEvent(bundle.getString("idEvent"))
                .setTitle(bundle.getString("title"))
                .setDescription(bundle.getString("description"))
                .setDateEvent(bundle.getString("dateEvent"))
                .setDateStart(bundle.getString("dateStart"))
                .setDateEnd(bundle.getString("dateEnd"))
                .setLatitude(bundle.getDouble("latitude"))
                .setLongitude(bundle.getDouble("longitude"))
                .setName(bundle.getString("name"))
                .setAssistance(bundle.getInt("assistance"))
                .setFlag(bundle.getInt("flag"));
        return event;
    }

}
