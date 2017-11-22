package com.netgame.netgame.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 10/11/17.
 */

public class Comment {

    @SerializedName("userName")
    private String userName;

    @SerializedName("description")
    private String description;

    @SerializedName("like")
    private int like;

    @SerializedName("dateRegister")
    private String dateRegister;

    @SerializedName("flag")
    private int flagUser;

    public Comment(){
        this.flagUser = 0;
    }

    public String getUserName() {
        return userName;
    }

    public Comment setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Comment setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getLike() {
        return like;
    }

    public Comment setLike(int like) {
        this.like = like;
        return this;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public Comment setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public int getFlagUser() {
        return flagUser;
    }

    public Comment setFlagUser(int flagUser) {
        this.flagUser = flagUser;
        return this;
    }
}
