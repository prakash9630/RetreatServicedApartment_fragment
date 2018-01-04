package com.ca.prakash.RetreatServicedApartment.Data;

import org.json.JSONObject;

/**
 * Created by prakash on 4/27/2016.
 */
public class Item implements JSONPopulator
{
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void populate(JSONObject jsonObject) {
        condition=new Condition();
        condition.populate(jsonObject.optJSONObject("condition"));

    }
}
