package com.example.prakash.RetreatServicedApartment.Data;

import org.json.JSONObject;

/**
 * Created by prakash on 4/27/2016.
 */
public class Channel implements JSONPopulator {
    private Item item;
    private Units units;

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public void populate(JSONObject jsonObject) {

        units=new Units();
        units.populate(jsonObject.optJSONObject("units"));

        item=new Item();
        item.populate(jsonObject.optJSONObject("item"));

    }
}
