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
| Retries, timeout, circuit breaker  | In_Progress  |
| Rate limits  | :heavy_check_mark:  |
| Distributed Tracing   | :heavy_check_mark:  |
| Metrics   | :heavy_check_mark:  |
| State management  | :heavy_check_mark:  |
| Secret management  | :heavy_check_mark:  |
| Multiple programing languages  | :heavy_check_mark:  |
| Service to service Access control  | In_Progress  |
| mTLS  | In_Progress   |
| Oauth2.0  | :heavy_check_mark:  |
| Central logging  | In_Progress  |
| Distributed locking  | In_Progress  |

> Note: This is not an exhaustive list but the top concerns that I had implemented in various applications I worked on. As you can see these have nothing to do with the business domain for which we are building the application. These are the concern due to micro-service architecture. 


## Start the application
Make sure ‘Dapr’ is installed on your machine. Run the following command to initialize Dapr (if not already initialized) 
```sh
dapr init 
```
### Order Service

```sh
dapr run --app-id order-service --components-path ../dapr-component --app-port 8080 --dapr-http-port 3500 mvn spring-boot:run
```
### Restaurant Service

```sh
dapr run --app-id restaurant-service --components-path ../dapr-component --app-port 8081 --dapr-http-port 3501 mvn spring-boot:run
```

### Rating Service

```sh
npm install
```

```sh
dapr run --app-id rating-service --components-path ../dapr-component --app-port 8083 --dapr-http-port 3503 node app.js
```

### Point Service

```sh
pip install -r requirements.txt
```

```sh
dapr run --app-id point-service --components-path ../dapr-component --app-port 8084 --dapr-http-port 3504 python app.py
```

## Pub-Sub & routing 

![Alt text](/images/pub_sub_routing.PNG?raw=true "Title")

Order service and Point service are communicating asynchronously with each other via the pub/sub model. When an order is created inside the order service, the order service raises an event ‘Order Created' and publishes it to a channel. Point service has subscribed to the channel and receives a notification via HTTP post method by side card and adds points accordingly. When the order is canceled by the user, the Order service raises an event ‘Order Cancelled’. Point service has also subscribed to this event but this event is routed differently from ‘Order created’ The following relevant code highlights how this is achieved by configuration in Dapr and the required code to write in the application. 

## Distributed Tracing

When a user places an order, the order is received by Order Service. Order service first coordinates with Restaurant Service via sync API call to verify if the restaurant is correct and if the restaurant is in working hours. Internally, restaurant service communicates with ‘Rating Service’ to get the rating of the restaurant. Once the order is persistent, the order service raises an event ‘OrderCreated’ to which Point service has subscribed and adds points according. Restaurant service has also subscribed to the same event 'OrderCreated' to place an order to an external system. The following screenshot shows how this whole interaction is captured by Dapr in ‘Jager’ without writing a single line of code. Notice '2' on the line from 'order-service' to 'restaurant-service' this represents 'order-service' calling 'restaurant-service' two times, one is a sync call to verify the restaurant, and the other is due to pub/sub subscription 

![Alt text](/images/distributed_tracing.PNG?raw=true "Title")

## State Management

![Alt text](/images/order_cancel_ttl.PNG?raw=true "Title")

To demonstrate state management capability, there is order cancellation functionality. The requirement is such that when a user places an order he/she is allowed to cancel the order only within 10 mins. To implement this we will store the orderId in a state store with a TTL of 10 mins. When a user initiates a cancellation we check the state store and see if the orderId exists or not if it exists we allow the user to cancel the order else we throw an exception. 

## Secret

When our application receives an order, we need to inform the restaurant about start preparing the food. There could be multiple types of integration but we have assumed an API call to Restaurant’s existing application. To make an external API call generally, we need some sort of API key or Basic Authentication (username/password). To demonstrate a secret component of Dapr we first obtain an API key from a secret store and then make an external API call.

## Oauth2.0 Authorization Code

Here, I have assumed there is a web application which is maintaining the session and dealing with web pages. When an unauthorized user tries to place an order it should be presented with a login page. Only upon valid login, user should be able to place an order. To achieve this I have used Keycloak as an authorization server and Oauth2.0 Authorization code flow to authenticate the user. First, you need to start 'order-service' with the additional command line parameter '-c' which tells dapr to use OAuth middleware as shown below

```sh
dapr run --app-id order-service --components-path ../dapr-component -c ../dapr-component/config-oauth.yml --app-port 8080 --dapr-http-port 3500 mvn spring-boot:run
```

Start the keycloak authorization server and import the 'realm-export.json' into keycloak

```sh
cd docker-compose
docker-compose up -d
```

Hit get order api using dapr method

```sh
http://localhost:3500/v1.0/invoke/order-service/method/v1/order
```

The user will be presented with a login page if a user is not already logged in. Once logged-in Dapr side card exchange 'code' with 'access token' and pass the access token in header 'authorization' which our application can use for authorization. In our application we are just printing the access token as shown below : 

```java 
  @GetMapping("/{orderId}")
  Order getOrder(@PathVariable("orderId") String orderId, @RequestHeader(value = "authorization", required = false) String accessToken) {
    log.info(String.format("access token is %s", accessToken));
    return orderService.getOrder(orderId);
  }
```

## Rate limit

Just start order service with configuration having rate limit defined as shown below. I have define 2 request per sec as a limit. If client tries to call order service with more that 2 rps it will get '429 too many request' error. This is a very basic way of enabling rating limits. If the requirement is to use a more sophisticated rate limit better use sentinel middle where you can configure the rate limit per URL basis.

```sh
dapr run --app-id order-service --components-path ../dapr-component -c ../dapr-component/config-ratelimit.yml --app-port 8080 --dapr-http-port 3500 mvn spring-boot:run
```
