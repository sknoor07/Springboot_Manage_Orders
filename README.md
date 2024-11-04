## Spring Boot Microservices
This repository contains an example of Microservices interaction using Spring Boot / Spring Cloud OpenFeing.<br/><br/>
-The app is an Store Application which contains internally 3 Apps/Microservices ( Orders, Stock and Notifications ).
The goal is to provide some REST APIs to manage Orders and Products.<br/><br/>
-The main entry-point is the POST operation http://localhost:8081/orders/place-order associated to the
````Orders Microservice```` ( the POST method triggers the creation/placement of new Orders ).<br/><br/>
-After call the previous POST operation, the ````Orders Microservice```` will call the ````Stock Microservice```` to
check and decrement the stock availability of the requested Items.<br/><br/>
-Finally, the ````Orders Microservice```` will persist into the database the new Order, and will call  the
````Notifications Microservice```` to dispatch  a 'New Order' notification email to the Customer (in case the SMTP email
server it´s configured)

<br/>

### 🔧 Technology Stack

```
Java 17
Spring Boot 3 ( REST API )
Spring Cloud OpenFeign
Spring Data ( MySQL )
Misc Libraries (  Maven  /  Docker  /  SpringDoc OpenAPI  /  Spring Email  /  Lombok  )
```

<br/>

### ⚒️ Getting Started

```bash
# clone the project
git clone https://github.com/rodrigobalazs/springboot-microservices.git;

# start a mysql docker container associated to the Stock Microservice
docker run --name stock_db -e MYSQL_DATABASE=stock_db -e MYSQL_USER=<db_user> \
    -e MYSQL_PASSWORD=<db_password> -e MYSQL_ROOT_PASSWORD=<db_password> \
    -p 3306:3306 -d mysql:latest;

# make sure to update stock-microservice/src/main/resources/application.properties with
# the <db_user> and <db_password> defined in the previous point
spring.datasource.username=<db_user>
spring.datasource.password=<db_password>


# start an additional mysql docker container associated to the Order Microservice
# note: the port has changed from 3306 to 3307 to avoid collisions between the docker DBs
docker run --name order_db -e MYSQL_DATABASE=order_db -e MYSQL_USER=<db_user> \
    -e MYSQL_PASSWORD=<db_password> -e MYSQL_ROOT_PASSWORD=<db_password> \
    -p 3307:3306 -d mysql:latest;

# make sure to update orders-microservice/src/main/resources/application.properties with
# the <db_user> and <db_password> defined in the previous point
spring.datasource.username=<db_user>
spring.datasource.password=<db_password>

# <optional step> configure a Gmail account to act as the New Order notification 
# email SMTP server =>
-the Gmail account needs to have 2-Step verification (2FA) enabled
https://myaccount.google.com > search for App Passwords > create a new app "store_app" 
> copy the value of the generated <app_password>
-update notifications-microservice/src/main/resources/application.properties with =>
spring.mail.username=<gmail_used_to_generate_the_app>
spring.mail.password=<app_password>

# compile all the apps/microservices ..
cd springboot-microservices;
mvn clean install;

# starts the Stock microservice ..
cd stock-microservice; mvn spring-boot:run;

# starts the Order microservice ..
<open a new terminal window>
cd ../orders-microservice; mvn spring-boot:run;

# starts the Notifications microservice ..
<open a new terminal window>
cd ../notifications-microservice; mvn spring-boot:run;
```
<br/>
<br/>

### 💡 Orders Microservice ( API Examples )

#### 1. Place a new Order =>

-Place a new Order based on the provided Quote details ( customer email + Items to purchase ).  This operation will also
decrement the quantity in stock of the purchased Item(s) and will send a New Order email notification ( if configured )
```
curl -X POST http://localhost:8081/orders/place-order  \
     -H 'accept: application/json'  \
     -H 'Content-Type: application/json' -d \
'{
    "customerEmail": "somecustomer@emailtest.com",
    "items": [
        {
            "productName": "Rustic Sofa",
            "requestedQuantity": 2
        },
        {
            "productName": "Office Chair Flexi Seat",
            "requestedQuantity": 1
        }
    ]
}';
```

#### 2. Retrieves all Orders =>
```
curl -X GET http://localhost:8081/orders/get-orders -H 'accept: application/json';
```

<br/>

### 🔍 Orders Microservice ( API Documentation / Swagger )

http://localhost:8081/swagger-ui/index.html

![](https://github.com/rodrigobalazs/springboot-microservices/blob/main/orders-microservice/src/main/resources/static/orders_app_swagger.png)

<br/>
<br/>

### 💡 Stock Microservice ( API Examples )

#### 1. Retrieves all Products =>
```
curl -X GET http://localhost:8082/stock/get-products -H 'accept: application/json';
```

#### 2. Retrieves whether the requested quantity of the Product given as parameter is in Stock or not =>
```
curl -X GET "http://localhost:8082/stock/is-in-stock?productName=Rustic+Sofa&requestedQuantity=100" \
     -H 'accept: application/json';
```

#### 3. Decrease the available quantity of the Product given as parameter =>
```
curl -X PUT "http://localhost:8082/stock/decrease-product-available-quantity?productName=Rustic+Sofa&quantityToDecrease=1" \
     -H 'accept: application/json';
```

<br/>

### 🔍 Stock Microservice ( API Documentation / Swagger )

http://localhost:8082/swagger-ui/index.html

![](https://github.com/rodrigobalazs/springboot-microservices/blob/main/stock-microservice/src/main/resources/static/stock_app_swagger.png)

<br/>
<br/>

### 💡 Notifications Microservice ( API Examples )

#### 1. Sends a new Order Notification Email =>
```
curl -X POST "http://localhost:8083/emailNotifications/send-new-order-notification" \
     -d "customerEmail=somecustomer@emailtest.com" \
     -d "orderId=100" \
     -H 'accept: application/json';
```
<br/>

### 🔍 Notifications Microservice ( API Documentation / Swagger )

http://localhost:8083/swagger-ui/index.html

![](https://github.com/rodrigobalazs/springboot-microservices/blob/main/notifications-microservice/src/main/resources/static/notifications_app_swagger.png)

