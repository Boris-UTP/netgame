package com.netgame.netgame.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 8/11/17.
 */

public class Authenticate {

    @SerializedName("tokenAccess")
    private String token;

    @SerializedName("expireIn")
    private String expireIn;

    @SerializedName("status")
    private String status;

    public String getToken() {
        return token;
    }

    public Authenticate setToken(String token) {
        this.token = token;
        return this;
    }

    public String getExpireIn() {
        return expireIn;
    }

    public Authenticate setExpireIn(String expireIn) {
        this.expireIn = expireIn;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Authenticate setStatus(String status) {
        this.status = status;
        return this;
    }
}
