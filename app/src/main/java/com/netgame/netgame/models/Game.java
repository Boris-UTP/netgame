package com.netgame.netgame.models;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by arkanay on 9/11/17.
 */

public class Game {

    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    public String getId() {
        return id;
    }

    public Game setId(String id) {
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
        game.setId(bundle.getString("idGame"))
                .setName(bundle.getString("name"))
                .setImage(bundle.getString("image"));
        return game;
    }

    public Bundle toBundle (){
        Bundle bundle = new Bundle();
        bundle.putString("idGame", id);
        bundle.putString("name", name);
        bundle.putString("image", image);
        return bundle;
    }

}
