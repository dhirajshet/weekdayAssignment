/*
This model class stores individual order along with the processing time left at each instance of processing
 */

package com.assignment.weekdayassignment.models;

public class ProcessedOrder {

    private Order order;
    private Float remainingProcessingTime;

    public ProcessedOrder(Order order, Float remainingProcessingTime) {
        this.order = order;
        this.remainingProcessingTime = remainingProcessingTime;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Float getRemainingProcessingTime() {
        return remainingProcessingTime;
    }

    public void setRemainingProcessingTime(Float remainingProcessingTime) {
        this.remainingProcessingTime = remainingProcessingTime;
    }
}
