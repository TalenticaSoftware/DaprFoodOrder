# Microservice Food Order Application On Dapr

This repository contains a working micro-service application to demonstrate various features of the __Dapr__.

The application is about Food Ordering, where the end-user search for a restaurant, add a food item to the cart, and place an order. This application is similar to swiggy. I have tried to build only those functionalities which are required to explore various aspects of Dapr. Following is the list of services and their responsibilities :

- ___Order Service (Java)___: This service takes the order. It verifies the restaurant provided in order with Restaurant service. 
- ___Restaurant Service (Java)___: This service takes care of the restaurant and its menu item and prices. Admin is responsible for adding/updating new restaurant and its menu item & price.
- ___Rating service (NodeJs)___: This service maintains the rating of restaurants. Rating is provided by a user. 
- ___Point Service (Python)___: This service maintains the points gained by a customer by placing orders. 

### Dapr features demostarted in this application

| Feature | Available |
| ------- | ----------|
| pub/sub ( async communication) | :heavy_check_mark:  |
| pub/sub routing  | :heavy_check_mark:  |
