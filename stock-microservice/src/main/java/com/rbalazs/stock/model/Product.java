package com.rbalazs.stock.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;
import java.util.Objects;

/**
 * Represents a Product with a given available stock quantity.
 */
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;
    private int availableQuantity;

    /** Empty Constructor required by JPA / Hibernate. */
    public Product() {}

    /**
     * Creates a new Product.
     *
     * @param theName the product name
     * @param theAvailableQuantity the product available quantity
     */
    public Product(final String theName, final int theAvailableQuantity) {
        Validate.notEmpty(theName, "The product name cannot be null nor empty");
        Validate.isTrue(theAvailableQuantity >= 0, "The available quantity must be greater or equal to zero");
        name = theName;
        availableQuantity = theAvailableQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return availableQuantity == product.availableQuantity && Objects.equals(productId, product.productId)
                && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, availableQuantity);
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", name='" + name + '\'' + ", availableQuantity="
                + availableQuantity + '}';
    }
}