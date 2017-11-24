package com.netgame.netgame.models;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 18/11/17.
 */

public class UserCabin {

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("stateAttention")
    private int stateAttention;

    public String getName() {
        return name;
    }

    public UserCabin setName(String name) {
        this.name = name;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public UserCabin setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public UserCabin setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserCabin setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getStateAttention() {
        return stateAttention;
    }

    public UserCabin setStateAttention(int stateAttention) {
        this.stateAttention = stateAttention;
        return this;
    }

    public Bundle toBundle(){
        Bundle bundle = new Bundle();
        bundle.putString("address",address);
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        bundle.putInt("stateAttention", stateAttention);
        return bundle;
    }

    public static UserCabin from (Bundle bundle){

        UserCabin userCabin = new UserCabin();
        userCabin.setAddress(bundle.getString("address"))
                .setLatitude(bundle.getDouble("latitude"))
                .setLongitude(bundle.getDouble("longitude"))
                .setStateAttention(bundle.getInt("stateAttention"));

        return userCabin;
    }
}
