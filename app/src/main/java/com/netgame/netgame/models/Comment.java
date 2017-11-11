package com.netgame.netgame.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 10/11/17.
 */

public class Comment {

    @SerializedName("username")
    private String userName;

    @SerializedName("description")
    private String description;

    @SerializedName("myLike")
    private Boolean like;

    @SerializedName("dateRegister")
    private String dateRegister;

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

    public Boolean getLike() {
        return like;
    }

    public Comment setLike(Boolean like) {
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
}
