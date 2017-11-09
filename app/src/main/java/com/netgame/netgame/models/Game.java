package com.netgame.netgame.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 9/11/17.
 */

public class Game {

    @SerializedName("idGame")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    public int getId() {
        return id;
    }

    public Game setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Game setName(String name) {
        this.name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public Game setImage(String image) {
        this.image = image;
        return this;
    }
}
