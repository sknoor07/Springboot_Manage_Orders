package com.rbalazs.orders.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import java.util.List;
import java.util.Objects;

/**
 * Represents a given Order, an Order contains a list of {@link OrderItem}.
 *
 * @author Rodrigo Balazs
 */
@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private String customerEmail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    /** Empty Constructor required by JPA / Hibernate. */
    public Order() {}

    /**
     * Creates a new Order.
     *
     * @param theCustomerEmail the customer email associated to the Order.
     * @param theItems the order´s items.
     */
    public Order(final String theCustomerEmail, final List<OrderItem> theItems) {
        Validate.notEmpty(theCustomerEmail, "The customer email cannot be null nor empty");
        Validate.notEmpty(theItems, "The order´s items cannot be empty");
        customerEmail = theCustomerEmail;
        items = theItems;
        addItems(theItems);
    }

    /* this method is needed to synchronize both sides of the bidirectional association between Order and OrderItem
    ref: https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate */
    public void addItems(List<OrderItem> items) {
        this.items = items;
        items.forEach(item -> item.setOrder(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) && Objects.equals(customerEmail, order.customerEmail)
                && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerEmail, items);
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", customerEmail='" + customerEmail + '\'' + ", items=" + items + '}';
    }
}
