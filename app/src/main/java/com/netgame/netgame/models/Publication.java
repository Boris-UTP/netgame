package com.netgame.netgame.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arkanay on 9/11/17.
 */

public class Publication {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("dateRegister")
    private String dateRegister;

    @SerializedName("like")
    private Boolean like;

    @SerializedName("favorite")
    private Boolean favorite;

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

    public Boolean getLike() {
        return like;
    }

    public Publication setLike(Boolean like) {
        this.like = like;
        return this;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public Publication setFavorite(Boolean favorite) {
        this.favorite = favorite;
        return this;
    }
}
