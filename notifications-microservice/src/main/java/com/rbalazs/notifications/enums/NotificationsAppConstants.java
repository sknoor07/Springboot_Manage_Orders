package com.rbalazs.notifications.enums;

/**
 * Enum which contains some constants related to the Notifications app/microservice.
 */
public enum NotificationsAppConstants {;

    public static final String NEW_ORDER_EMAIL_SUBJECT = "New Order";

    // TODO(rodrigo.balazs) this const it´s also defined at OrderAppConstants.java, should be extracted in some shared project or similar to avoid code duplication
    public static final String NEW_ORDER_NOTIFICATION = "a new Order has been placed with order ID: ";
}