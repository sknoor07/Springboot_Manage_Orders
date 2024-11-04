
package com.rbalazs.orders.enums;

/**
 * Enum which contains some constants related to the Orders app/microservice.
 */
public enum OrderAppConstants {;

    public static final String STOCK_MICROSERVICE = "stockMicroservice";
    public static final String STOCK_MICROSERVICE_URL = "http://localhost:8082/stock";

    public static final String NOTIFICATIONS_MICROSERVICE = "notificationsMicroservice";
    public static final String NOTIFICATIONS_MICROSERVICE_URL = "http://localhost:8083/emailNotifications";

    public static final String NEW_ORDER_NOTIFICATION = "a new Order has been placed with order ID: ";
}