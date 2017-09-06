package com.example.prakash.RetreatServicedApartment.Pojo;

/**
 * Created by prakash on 4/12/2017.
 */

public class Order_unit_data {

    String Unitname;
    String UnitQuantity;

    public Order_unit_data(String unitname, String unitQuantity) {
        Unitname = unitname;
        UnitQuantity = unitQuantity;
    }

    public String getUnitname() {
        return Unitname;
    }

    public String getUnitQuantity() {
        return UnitQuantity;
    }
}
