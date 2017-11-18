package com.netgame.netgame.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by arkanay on 18/11/17.
 */

public class PublicationComments {

    @SerializedName("publication")
    private Publication publication;

    @SerializedName("comments")
    private List<Comment> comments;

    public Publication getPublication() {
        return publication;
    }

    public PublicationComments setPublication(Publication publication) {
        this.publication = publication;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public PublicationComments setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }
}
