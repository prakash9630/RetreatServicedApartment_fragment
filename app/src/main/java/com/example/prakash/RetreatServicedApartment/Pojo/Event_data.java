package com.example.prakash.RetreatServicedApartment.Pojo;

/**
 * Created by prakash on 5/30/2017.
 */

public class Event_data {
    String when;
    String title;
    String image;
    String nid;

    public String getWhen() {
        return when;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getNid() {
        return nid;
    }

    public Event_data(String when, String title, String image, String nid) {

        this.when = when;
        this.title = title;
        this.image = image;
        this.nid = nid;


    }
}
