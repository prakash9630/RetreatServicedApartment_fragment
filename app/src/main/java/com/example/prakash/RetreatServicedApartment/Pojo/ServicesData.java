package com.example.prakash.RetreatServicedApartment.Pojo;

/**
 * Created by prakash on 8/14/2017.
 */

public class ServicesData {
    String nid;
    String title;
    String image;

    public ServicesData(String nid, String title, String image) {
        this.nid = nid;
        this.title = title;
        this.image = image;
    }

    public String getNid() {
        return nid;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
