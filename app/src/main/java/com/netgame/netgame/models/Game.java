package com.netgame.netgame.models;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by arkanay on 9/11/17.
 */

public class Game {

    @SerializedName("idGame")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("imagen")
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

    public static Game from (Bundle bundle){
        Game game = new Game();
        game.setId(bundle.getInt("idGame"))
                .setName(bundle.getString("name"))
                .setImage(bundle.getString("image"));
        return game;
    }

    public Bundle toBundle (){
        Bundle bundle = new Bundle();
        bundle.putInt("idGame", id);
        bundle.putString("name", name);
        bundle.putString("image", image);
        return bundle;
    }

}
