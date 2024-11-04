package com.rbalazs.orders.controller;

import com.rbalazs.orders.controller.swagger.OrderControllerSwagger;
import com.rbalazs.orders.dto.QuoteDTO;
import com.rbalazs.orders.dto.QuoteItemDTO;
import com.rbalazs.orders.enums.OrderAppConstants;
import com.rbalazs.orders.enums.OrderAppValidations;
import com.rbalazs.orders.exception.OrderCustomException;
import com.rbalazs.orders.model.Order;
import com.rbalazs.orders.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * Order REST Controller.
 *
 * @author Rodrigo Balazs
 */
@RestController
@RequestMapping("/orders")
public class OrderController implements OrderControllerSwagger {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/get-orders")
    public ResponseEntity<List<Order>> getOrders() {
        LOGGER.info("starts to execute orderController.getOrders()");
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestBody QuoteDTO quoteDTO) {
        LOGGER.info("starts to execute orderController.placeOrder()");

        List<QuoteItemDTO> items = quoteDTO.getItems();
        for (QuoteItemDTO item : items) {
            if (StringUtils.isEmpty(item.getProductName())) {
                throw new OrderCustomException(OrderAppValidations.PRODUCT_NAME_EMPTY);
            }
        }

        long orderId = orderService.placeOrder(quoteDTO);
        return ResponseEntity.ok(OrderAppConstants.NEW_ORDER_NOTIFICATION + orderId);
    }
}

