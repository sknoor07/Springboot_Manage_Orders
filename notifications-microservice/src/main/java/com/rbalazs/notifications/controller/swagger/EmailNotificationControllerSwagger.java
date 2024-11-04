package com.rbalazs.notifications.controller.swagger;

import com.rbalazs.notifications.controller.EmailNotificationController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * Swagger interface related to {@link EmailNotificationController}.
 * API Documentation/Swagger at => http://<notifications_app_url>/swagger-ui/index.html
 */
@Tag(name = "Email Notifications API", description = "API endpoints related to the Notifications App/Microservice")
public interface EmailNotificationControllerSwagger {

    @Operation(summary = "Sends a new Order Notification Email")
    public void sendNewOrderNotification(
            @Parameter(description = "customer email to send the notification", example = "somecustomer@email.com", required = true)
            @RequestParam(value = "customerEmail") String customerEmail,

            @Parameter(description = "new order identifier", example = "1", required = true)
            @RequestParam(value = "orderId") Long orderId);
}
