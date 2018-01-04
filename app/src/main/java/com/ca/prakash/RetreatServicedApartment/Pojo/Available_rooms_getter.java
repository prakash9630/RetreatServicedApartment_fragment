package com.ca.prakash.RetreatServicedApartment.Pojo;

import java.io.Serializable;

/**
 * Created by prakash on 5/15/2017.
 */

public class Available_rooms_getter implements Serializable {

    String ApartmentName;
    Integer BookingPrice;
    String Body;
    String Image;
    int UnitNo;
    String Unit_tyep_Machinename;


    public Available_rooms_getter(String Unit_tyep_Machinename,String apartmentName, Integer bookingPrice, String body, String image, int unitNo) {
        ApartmentName = apartmentName;
        BookingPrice = bookingPrice;
        this.Unit_tyep_Machinename=Unit_tyep_Machinename;
        Body = body;
        Image = image;
        UnitNo = unitNo;
    }

    public String getUnit_tyep_Machinename() {
        return Unit_tyep_Machinename;
    }

    public void setUnit_tyep_Machinename(String unit_tyep_Machinename) {
        Unit_tyep_Machinename = unit_tyep_Machinename;
    }

    public String getApartmentName() {
        return ApartmentName;
    }

    public void setApartmentName(String apartmentName) {
        ApartmentName = apartmentName;
    }

    public Integer getBookingPrice() {
        return BookingPrice;
    }

    public void setBookingPrice(Integer bookingPrice) {
        BookingPrice = bookingPrice;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(int unitNo) {
        UnitNo = unitNo;
    }
}
