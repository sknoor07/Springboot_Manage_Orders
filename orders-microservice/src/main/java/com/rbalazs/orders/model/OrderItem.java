package com.rbalazs.orders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import java.util.Objects;

/**
 * Represents a given Order Item, an OrderItem it´s associated to an specific {@link Order}.
 */
@Getter
@Setter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;
    private String productName;
    private int requestedQuantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    /* This JSON annotation avoids during GET API Calls this exception =>
    "org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Document nesting depth
    exceeds the maximum allowed" which is caused by a circular dependency between Order and OrderItem */
    @JsonIgnore
    private Order order;

    /** Empty Constructor required by JPA / Hibernate. */
    public OrderItem() {}

    /**
     * Creates a new Order Item.
     *
     * @param theProductName the product name.
     * @param theRequestedQuantity the requested quantity.
     */
    public OrderItem(final String theProductName, final int theRequestedQuantity) {
        Validate.notEmpty(theProductName, "The product name cannot be null nor empty");
        Validate.isTrue(theRequestedQuantity > 0, "The requested quantity must be greater to zero");
        productName = theProductName;
        requestedQuantity = theRequestedQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return requestedQuantity == orderItem.requestedQuantity && Objects.equals(orderItemId, orderItem.orderItemId)
                && Objects.equals(productName, orderItem.productName) && Objects.equals(order, orderItem.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, productName, requestedQuantity, order);
    }

    @Override
    public String toString() {
        return "OrderItem{" + "orderItemId=" + orderItemId + ", productName='" + productName + '\''
                + ", requestedQuantity=" + requestedQuantity + ", order=" + order + '}';
    }
}