/*
This class holds the attributes of a restaurant and performs operations on it.
 */

package com.assignment.weekdayassignment.services.util.resto;

import com.assignment.weekdayassignment.models.Order;
import com.assignment.weekdayassignment.configuration.RestaurantConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantUtility {

    @Autowired
    private static RestaurantConfiguration restaurantConfiguration;

    private static Integer currentRestaurantCapacity = restaurantConfiguration.RESTAURANT_CAPACITY;

    public static boolean doesRequiredSlotsExceedTheRestaurantCapacity(Order order){
        Integer numOfSlots = getNumberOfSlotsForOrder(order);
        return numOfSlots > restaurantConfiguration.RESTAURANT_CAPACITY;
    }

    public static boolean canRestaurantAccommodateOrder(Order order){
        Integer numOfSlots = getNumberOfSlotsForOrder(order);
        return currentRestaurantCapacity >= numOfSlots;
    }

    public static void accommodateOrder(Order order){
        Integer numOfSlots = getNumberOfSlotsForOrder(order);
        if(numOfSlots <= currentRestaurantCapacity){
            currentRestaurantCapacity -= numOfSlots;
        }
        else{
            System.out.println("Cannot accommodate order : " + order.getOrderId());
        }
    }

    public static void releaseAccommodationForOrder(Order order){
        Integer numOfSlots = getNumberOfSlotsForOrder(order);
        if(numOfSlots + currentRestaurantCapacity <= restaurantConfiguration.RESTAURANT_CAPACITY) {
            currentRestaurantCapacity += numOfSlots;
        }
    }

    //Gets the number of slots needed for an order
    private static Integer getNumberOfSlotsForOrder(Order order){
        Integer numOfSlots = 0;
        Character meals[] = order.getMeals();
        for(Character meal : meals){
            if(meal == 'M'){
                numOfSlots += restaurantConfiguration.COOKING_SLOT_FOR_M;
            }
            else if(meal == 'A'){
                numOfSlots += restaurantConfiguration.COOKING_SLOT_FOR_A;
            }
        }
        return numOfSlots;
    }

}
