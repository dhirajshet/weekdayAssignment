/*
An interface which holds the method for processing the orders which will be implemented by the ProcessOrder class
 */

package com.assignment.weekdayassignment.services;

import com.assignment.weekdayassignment.models.Order;

import java.util.List;

public interface ProcessOrderFactory {

    public void processOrders(List<Order> orderList);
}
