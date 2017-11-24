package com.netgame.netgame.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 23/11/17.
 */

public class UserGamer {
    @SerializedName("name")
    private String name;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("nickName")
    private String nickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
