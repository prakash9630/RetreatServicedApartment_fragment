package com.example.prakash.RetreatServicedApartment.Pojo;

/**
 * Created by prakash on 6/12/2017.
 */

public class VideoData {
    String title;
    String link;
    String body;

    public VideoData(String title, String link, String body) {
        this.title = title;
        this.link = link;
        this.body=body;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getBody() {
        return body;
    }
}
