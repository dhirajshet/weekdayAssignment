package com.assignment.weekdayassignment.configuration;

import org.springframework.stereotype.Component;

@Component
public class RestaurantConfiguration {

    public static final Integer RESTAURANT_CAPACITY = new Integer(7);
    public static final Integer COOKING_SLOT_FOR_A = new Integer(1);
    public static final Integer COOKING_SLOT_FOR_M = new Integer(2);
    public static final Integer PREPARATION_TIME_FOR_A = new Integer(17);
    public static final Integer PREPARATION_TIME_FOR_B = new Integer(29);
    public static final Float TIME_TO_TRAVEL_ONE_KM = new Float(8);

}
