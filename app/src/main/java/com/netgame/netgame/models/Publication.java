package com.netgame.netgame.models;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 9/11/17.
 */

public class Publication {

    @SerializedName("idPublication")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("dateRegister")
    private String dateRegister;

    @SerializedName("mylike")
    private int like;

    @SerializedName("favorite")
    private int favorite;

    public Publication() {

    }

    public int getId() {
        return id;
    }

    public Publication setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Publication setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Publication setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public Publication setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
        return this;
    }

    public int getLike() {
        return like;
    }

    public Publication setLike(int like) {
        this.like = like;
        return this;
    }

    public int getFavorite() {
        return favorite;
    }

    public Publication setFavorite(int favorite) {
        this.favorite = favorite;
        return this;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("idPublication", id);
        bundle.putString("title", title);
        bundle.putString("description", description);
        return bundle;
    }

    public static Publication from(Bundle bundle){

        Publication publication = new Publication();
        publication.setTitle(bundle.getString("title"))
                .setId(bundle.getInt("idPublication"))
                .setDescription(bundle.getString("description"));
        return publication;
    }

}
