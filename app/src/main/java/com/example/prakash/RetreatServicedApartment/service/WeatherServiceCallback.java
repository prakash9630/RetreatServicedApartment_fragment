package com.example.prakash.RetreatServicedApartment.service;


import com.example.prakash.RetreatServicedApartment.Data.Channel;

/**
 * Created by akash on 4/27/2016.
 */
public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFalure(Exception exception);
}
