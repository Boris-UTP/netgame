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

    @SerializedName("address")
    private String address;

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

    public String getAddress() {
        return address;
    }

    public Event setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public Event setIdEvent(int idEvent) {
        this.idEvent = idEvent;
        return this;
    }
}
