package com.rbalazs.orders.service;

import com.rbalazs.orders.dto.QuoteDTO;
import com.rbalazs.orders.dto.QuoteItemDTO;
import com.rbalazs.orders.enums.OrderAppValidations;
import com.rbalazs.orders.exception.OrderCustomException;
import com.rbalazs.orders.feign.NotificationsClient;
import com.rbalazs.orders.feign.StockClient;
import com.rbalazs.orders.model.Order;
import com.rbalazs.orders.model.OrderItem;
import com.rbalazs.orders.repository.OrderRepository;
import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Order Service.
 *
 * @author Rodrigo Balazs
 */
@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final StockClient stockClient;
    private final NotificationsClient notificationsClient;

    @Autowired
    public OrderService(final OrderRepository orderRepository, final StockClient stockClient,
                        final NotificationsClient notificationsClient) {
        this.orderRepository = orderRepository;
        this.stockClient = stockClient;
        this.notificationsClient = notificationsClient;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }


    /**
     * Place(creates) a new Order based on the {@link QuoteDTO} given as parameter.
     *
     * This method is declared as Transactional because the Order´s creation + stock decrement of the purchased Item(s)
     * + email notification should execute atomically.
     *
     * This service communicates against the Notifications and Stock Apps/Microservices via {@link NotificationsClient}
     * and {@link StockClient}
     *
     * @param quoteDTO the quote.
     * @return the new Order identifier.
     */
    @Transactional
    public long placeOrder(final QuoteDTO quoteDTO) {

        String customerEmail = quoteDTO.getCustomerEmail();
        if (StringUtils.isEmpty(customerEmail)){
            throw new OrderCustomException(OrderAppValidations.CUSTOMER_NOT_FOUND);
        }

        List<QuoteItemDTO> quoteItems = quoteDTO.getItems();
        if (CollectionUtils.isEmpty(quoteItems)){
            throw new OrderCustomException(OrderAppValidations.EMPTY_ORDER_ITEMS);
        }

        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        for (QuoteItemDTO quoteItem : quoteItems) {
            String productName = quoteItem.getProductName();
            int requestedQuantity = quoteItem.getRequestedQuantity();

            if (stockClient.getProductByName(productName).getBody() == null) {
                throw new OrderCustomException(OrderAppValidations.PRODUCT_NOT_FOUND);
            }

            if (!stockClient.isInStock(productName, requestedQuantity)){
                throw new OrderCustomException(OrderAppValidations.OUT_OF_STOCK_PRODUCTS);
            }
            OrderItem orderItem = new OrderItem(productName, requestedQuantity);
            orderItems.add(orderItem);
        }

        Order order = new Order(customerEmail, orderItems);
        Long orderId = orderRepository.save(order).getOrderId();

        for (OrderItem orderItem : orderItems) {
            stockClient.decreaseProductAvailableQuantity(orderItem.getProductName(), orderItem.getRequestedQuantity());
        }

        notificationsClient.sendNewOrderNotification(customerEmail, orderId);
        return orderId;
    }
}
