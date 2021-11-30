/*
This is the root service class which processes the orders
 */
package com.assignment.weekdayassignment.services;

import com.assignment.weekdayassignment.models.Order;
import com.assignment.weekdayassignment.models.ProcessedOrder;
import com.assignment.weekdayassignment.services.util.queue.QueueProcessor;
import com.assignment.weekdayassignment.services.util.resto.RestaurantUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProcessOrders implements ProcessOrderFactory{

    @Autowired
    private QueueProcessor queueProcessor;

    @Autowired
    private RestaurantUtility restaurantUtility;

    @Override
    public void processOrders(List<Order> orderList) {

        //Initially push all the orders that could be accommodated into the queue
        Deque<ProcessedOrder> processedOrderDeque = new ArrayDeque<>();

        for(Order order : orderList){
            //Check for the accommodation, i.e., if the number of slots required to process is more than the resto capacity
            if(restaurantUtility.doesRequiredSlotsExceedTheRestaurantCapacity(order)){
                System.out.println("Order " + order.getOrderId() + " is denied because the restaurant cannot accommodate it.");
            }
            else{
                processedOrderDeque.addLast(new ProcessedOrder(order, new Float(0.0)));
            }
        }

        //Process all the orders in the queue
        queueProcessor.processQueues(processedOrderDeque);
    }
}
