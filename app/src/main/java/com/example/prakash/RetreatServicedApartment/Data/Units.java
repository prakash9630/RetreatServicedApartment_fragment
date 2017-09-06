package com.example.prakash.RetreatServicedApartment.Data;

import org.json.JSONObject;

/**
 * Created by prakash on 4/27/2016.
 */
public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void populate(JSONObject jsonObject) {
        temperature=jsonObject.optString("temperature");

    }
}
