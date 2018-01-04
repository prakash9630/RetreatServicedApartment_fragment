package com.ca.prakash.RetreatServicedApartment.Pojo;

/**
 * Created by prakash on 9/13/2017.
 */

public class Notification_data {
    String id;
    String notification;
    String time;

    public Notification_data(String id, String notification, String time) {
        this.id = id;
        this.notification = notification;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
