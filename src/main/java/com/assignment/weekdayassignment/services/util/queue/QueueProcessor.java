package com.assignment.weekdayassignment.services.util.queue;

import com.assignment.weekdayassignment.models.ProcessedOrder;
import com.assignment.weekdayassignment.services.util.resto.RestaurantUtility;
import com.assignment.weekdayassignment.services.util.time.OrderProcessingTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QueueProcessor {

    @Autowired
    private OrderProcessingTimeUtility orderProcessingTimeUtility;

    @Autowired
    private RestaurantUtility restaurantUtility;

    //Using Tree set data structure to store the orders, sorted based on the processing time remaining
    private static TreeSet<ProcessedOrder> processedOrderSet = new TreeSet<>(new Comparator<ProcessedOrder>() {
        @Override
        public int compare(ProcessedOrder o1, ProcessedOrder o2) {
            int compareValue = Float.compare(o1.getRemainingProcessingTime(), o2.getRemainingProcessingTime());
            if(compareValue <= 0 ) return -1;
            else return 1;
        }
    });

    public void processQueues(Deque<ProcessedOrder> processedOrderDeque){

        //Initial processing of the queue
        processQueueUtility(processedOrderDeque);

        //Process the remaining orders in queue, by adding their remainingProcessTime to the orderProcessTime of the earliest finishing order
        //Subtract the remainingProcessTime of all the other orders in the tree set with the remainingProcessTime of the earliest finishing order
        //Repeat the process till all the orders in the tree set are removed and all orders in the queue are processed
        while(!processedOrderSet.isEmpty()){
            ProcessedOrder processedOrderWithSmallestProcessTime = processedOrderSet.pollFirst();
            restaurantUtility.releaseAccommodationForOrder(processedOrderWithSmallestProcessTime.getOrder());

            //Update the remaining time for the orders in the tree set, by reducing it
            for(ProcessedOrder processedOrder : processedOrderSet){
                Float remainingTime = processedOrder.getRemainingProcessingTime() - processedOrderWithSmallestProcessTime.getRemainingProcessingTime();
                processedOrder.setRemainingProcessingTime(remainingTime);
            }

            //Update the remaining time for the orders in the queue, by increasing it
            Integer queueSize = processedOrderDeque.size();
            while(queueSize >0 ){
                ProcessedOrder processedOrder = processedOrderDeque.pollFirst();
                Float remainingTime = processedOrder.getRemainingProcessingTime() + processedOrderWithSmallestProcessTime.getRemainingProcessingTime();
                processedOrder.setRemainingProcessingTime(remainingTime);
                processedOrderDeque.addLast(processedOrder);
                queueSize--;
            }

            //Processing the remaining orders in the queue which could be accommodated
            processQueueUtility(processedOrderDeque);
        }
    }

    //Utility method to process the queue every time an order is completed
    private void processQueueUtility(Deque<ProcessedOrder> processedOrderDeque){

        //Add all the orders to the tree set which could be accommodated by calculating their individual processing time
        //Un accommodated orders are put back in the queue
        Integer queueSize = processedOrderDeque.size();
        while (queueSize >0){

            ProcessedOrder processedOrder = processedOrderDeque.pollFirst();
            if(restaurantUtility.canRestaurantAccommodateOrder(processedOrder.getOrder())){
                Float orderProcessingTime = orderProcessingTimeUtility.getOrderProcessingTime(processedOrder);

                //Checking validity
                if(Objects.isNull(orderProcessingTime)){
                    System.out.println("Order " + processedOrder.getOrder().getOrderId() + " will not get delivered as it is invalid");
                }
                else{
                    processedOrder.setRemainingProcessingTime(orderProcessingTime);
                    processedOrderSet.add(processedOrder);
                    restaurantUtility.accommodateOrder(processedOrder.getOrder());
                    System.out.println("Order " + processedOrder.getOrder().getOrderId() +" will get delivered in " + orderProcessingTime + " minutes");
                }
            }
            else{
                processedOrderDeque.addLast(processedOrder);
            }
            queueSize--;
        }
    }
}
