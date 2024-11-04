package com.rbalazs.notifications.controller;

import com.rbalazs.notifications.controller.swagger.EmailNotificationControllerSwagger;
import com.rbalazs.notifications.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Email Notifications REST Controller.
 * API Documentation/Swagger at => http://<notifications_app_url>/swagger-ui/index.html
 */
@RestController
@RequestMapping("/emailNotifications")
public class EmailNotificationController implements EmailNotificationControllerSwagger {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationController.class);
    private final EmailNotificationService emailNotificationService;

    @Autowired
    public EmailNotificationController(final EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @PostMapping("/send-new-order-notification")
    public void sendNewOrderNotification(@RequestParam(value = "customerEmail") String customerEmail,
            @RequestParam(value = "orderId") Long orderId) {
        LOGGER.info("starts to execute emailNotificationController.sendNewOrderNotification() for customer email:{}" ,
            customerEmail);
        emailNotificationService.sendNewOrderNotification(customerEmail, orderId);
    }
}