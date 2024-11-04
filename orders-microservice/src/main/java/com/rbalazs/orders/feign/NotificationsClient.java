package com.rbalazs.orders.feign;

import com.rbalazs.orders.enums.OrderAppConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Client used to communicate against the Notifications app/microservice via Spring Cloud OpenFeign.
 */
@FeignClient(name = OrderAppConstants.NOTIFICATIONS_MICROSERVICE, url = OrderAppConstants.NOTIFICATIONS_MICROSERVICE_URL)
public interface NotificationsClient {

    @PostMapping("/send-new-order-notification")
    public void sendNewOrderNotification(@RequestParam(value = "customerEmail") String customerEmail,
                                         @RequestParam(value = "orderId") Long orderId);
}
