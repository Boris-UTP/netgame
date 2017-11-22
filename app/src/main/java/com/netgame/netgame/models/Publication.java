package com.netgame.netgame.models;

import android.os.Bundle;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 9/11/17.
 */

public class Publication {

    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("dateRegister")
    private String dateRegister;

    @SerializedName("like")
    private int like;

    @SerializedName("favorite")
    private int favorite;

    public Publication() {
        this.like = 0;
        this.favorite = 0;
    }

    public String getId() {
        return id;
    }

    public Publication setId(String id) {
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
        bundle.putString("idPublication", id);
        bundle.putString("title", title);
        bundle.putString("description", description);
        bundle.putString("dateRegister", dateRegister);
        bundle.putInt("like", like);
        bundle.putInt("favorite", favorite);
        return bundle;
    }

    public static Publication from(Bundle bundle){
        Publication publication = new Publication();
        publication.setTitle(bundle.getString("title"))
                .setId(bundle.getString("idPublication"))
                .setDescription(bundle.getString("description"))
                .setDateRegister(bundle.getString("dateRegister"))
                .setLike(bundle.getInt("like"))
                .setFavorite(bundle.getInt("favorite"));
        return publication;
    }

}
