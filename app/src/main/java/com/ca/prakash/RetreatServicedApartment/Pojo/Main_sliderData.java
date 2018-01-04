package com.ca.prakash.RetreatServicedApartment.Pojo;

import java.io.Serializable;

/**
 * Created by prakash on 1/25/2017.
 */
public class Main_sliderData implements Serializable {

    String image;
    String roomname;
    String machinename;
    String nid;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getMachinename() {
        return machinename;
    }

    public void setMachinename(String machinename) {
        this.machinename = machinename;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }
}
