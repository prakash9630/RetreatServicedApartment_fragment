package com.ca.prakash.RetreatServicedApartment.Pojo;

/**
 * Created by prakash on 8/21/2017.
 */

public class Testimonial_data {
    String name;
    String reviewtext;
    String ratings;
    String image;

    public Testimonial_data(String name, String reviewtext, String ratings, String image) {
        this.name = name;
        this.reviewtext = reviewtext;
        this.ratings = ratings;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReviewtext() {
        return reviewtext;
    }

    public void setReviewtext(String reviewtext) {
        this.reviewtext = reviewtext;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
