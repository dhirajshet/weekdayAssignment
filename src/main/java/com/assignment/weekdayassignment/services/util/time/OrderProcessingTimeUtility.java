/*
This utility class calculates the process time of each order along with the current processing time at the restaurant
Initially the current processing time will be zero as there are no orders to be processed
When the orders get queued, their individual processing time gets added with the processing time of the previous orders at the resto
 */
package com.assignment.weekdayassignment.services.util.time;

import com.assignment.weekdayassignment.models.ProcessedOrder;
import com.assignment.weekdayassignment.configuration.RestaurantConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessingTimeUtility {

    @Autowired
    private RestaurantConfiguration restaurantConfiguration;

    public Float getOrderProcessingTime(ProcessedOrder processedOrder){

        //Initializing the meal prep time to A, since the maximum of (A, M) will be selected to calculate the preparation time
        Integer mealPreparationTime = restaurantConfiguration.PREPARATION_TIME_FOR_A;
        Character meals[] = processedOrder.getOrder().getMeals();

        if(meals.length < 1) return null;
        if(processedOrder.getOrder().getDistance() < 0) return null;

        //If M exists in the meals array, set meal prep time to M to calculate the preparation time as it has the max value
        for(Character m : meals){
            if(m == 'M'){
                mealPreparationTime = restaurantConfiguration.PREPARATION_TIME_FOR_B;
            }
        }

        Float totalTimeRequiredToTravel = (Float)(processedOrder.getOrder().getDistance() * restaurantConfiguration.TIME_TO_TRAVEL_ONE_KM);

        Float totalOrderProcessingTime = totalTimeRequiredToTravel + mealPreparationTime + processedOrder.getRemainingProcessingTime();

        return totalOrderProcessingTime;
    }
}
