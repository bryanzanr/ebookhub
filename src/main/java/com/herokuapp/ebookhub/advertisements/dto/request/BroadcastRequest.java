package com.herokuapp.ebookhub.advertisements.dto.request;

public class BroadcastRequest {

    private String author;

    private String description;

    private String image;

    private String latitude;

    private String longitude;

    private String publishDate;

    private String prefsTag;

    private String title;

    public String getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImage() {
        return this.image;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getPublishDate() {
        return this.publishDate;
    }

    public String getPrefsTag() {
        return this.prefsTag;
    }

    public String getTitle() {
        return this.title;
    }
}