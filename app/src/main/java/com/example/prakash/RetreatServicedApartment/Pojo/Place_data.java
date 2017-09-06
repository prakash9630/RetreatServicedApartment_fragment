package com.example.prakash.RetreatServicedApartment.Pojo;

/**
 * Created by prakash on 4/18/2017.
 */

public class Place_data {

    String nid;
    String Placename;
    String image;
    String attricaintype;


    public Place_data(String nid, String placename, String image, String attricaintype) {
        this.nid = nid;
        Placename = placename;
        this.image = image;
        this.attricaintype = attricaintype;
    }

    public String getNid() {
        return nid;
    }

    public String getPlacename() {
        return Placename;
    }

    public String getImage() {
        return image;
    }

    public String getAttricaintype() {
        return attricaintype;
    }
}
