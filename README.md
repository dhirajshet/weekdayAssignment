#Order Process API

This API processes the orders and gives the food delivery time.
I have used spring boot to create a standalone web app having a single POST API called /processOrders

##Code sections
There 4 main code sections separated by logic
* Configuration - Which holds the constants used in the restaurant
* Controllers - which has a single REST controller which has a single POST API called /processOrders
* Models - which has 2 model classes, one for the order and second for the processed order(which holds an additional parameter of how much time is left for processing that order along with the order details)
* Services - which holds the business logic

##Logic
####Data Structures
I have used 2 data structures
1. Deque - For storing the orders which are waiting for the current orders that are getting processed to be delivered.
2. Tree Set - For storing the orders that are currently getting processed. I have chosen Tree Set specifically inorder to store the orders in the increasing order of their delivery time.

####Business Logic
* Every time an order gets delivered(i.e., the order which sits at the top of the tree set which has the least processing time) will be popped from the tree set. 
* The delivery time of this popped order gets added to all the orders that are waiting in the queue and gets subtracted from all the orders that are getting processed in the tree set which has greater processing time than this one.
* If there are any orders in the deque which could be accommodated, will be added to the queue. Otherwise they will be added back to the deque.
* This process continues till all the orders in the tree set are processed.
* All the orders which needs slots greater than the resto capacity will be discarded(the output will be shown in the console) before adding them to the initial deque.

####Utilities
1. QueueProcessor - which performs the operation on the deque as well as the tree set. This is where the core logic is written to add and remove orders to and from the deque and the tree set.
2. RestaurantUtility - This utility provides different methods such as checking if the order slots are exceeding the resto capacity, if the orders could be accommodated at given time with the current capacity, to accommodate an order (by reducing the current capacity), and releasing the order (by increasing the current capacity), and a utility method which calculates the number if slots needed for an order.
3. OrderProcessingTimeUtility - This method calculates the total processing time of an order. It adds the time duration of a slot(M or A), time duration to travel, and the time duration of waiting for other orders to get delivered(processed) which was spent in the queue.

##Technologies Used
1. Java (JDK 1.8)
2. Spring Boot 

##How to run
Just run the spring boot app and send a post request to localhost:8080/processOrders.
This serves as the starting point of the order processing journey.
All the output is printed in the console(used System.out.println() to print the output).

