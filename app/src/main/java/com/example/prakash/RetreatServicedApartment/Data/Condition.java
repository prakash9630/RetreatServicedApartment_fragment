package com.example.prakash.RetreatServicedApartment.Data;

import org.json.JSONObject;

/**
 * Created by prakash on 4/27/2016.
 */
public class Condition implements JSONPopulator {
    private int code;
    private int temperature;
    private String description;

    public int getCode() {
        return code;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void populate(JSONObject jsonObject) {
code=jsonObject.optInt("code");
        temperature=jsonObject.optInt("temp");
        description=jsonObject.optString("text");
    }
}
