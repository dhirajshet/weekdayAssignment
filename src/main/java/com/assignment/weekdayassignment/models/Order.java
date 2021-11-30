/*
This is the model class for an Order which holds the basic details of an order along with getters and setters
 */
package com.assignment.weekdayassignment.models;

public class Order {

    private Integer orderId;
    private Character meals[];
    private Float distance;

    public Order(Integer orderId, Character[] meals, Float distance) {
        this.orderId = orderId;
        this.meals = meals;
        this.distance = distance;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Character[] getMeals() {
        return meals;
    }

    public void setMeals(Character[] meals) {
        this.meals = meals;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
}
