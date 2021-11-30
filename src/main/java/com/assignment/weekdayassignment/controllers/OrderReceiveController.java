/*
This is the controller class which implements POST method to accept and process the orders
 */
package com.assignment.weekdayassignment.controllers;

import com.assignment.weekdayassignment.models.Order;
import com.assignment.weekdayassignment.services.ProcessOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class OrderReceiveController {

    @Autowired
    private ProcessOrders processOrders;

    @PostMapping("/processOrders")
    public void receiveOrders(@RequestBody List<Order> orderList){
        processOrders.processOrders(orderList);
    }
}
