package com.rbalazs.orders.dto;

import com.rbalazs.orders.model.Order;
import lombok.Data;

import java.util.List;

/**
 * Represents a given Quote ( represents the Cart´s Items a given Customer wants to purchase ).
 * In case all the required business rules executes as expected, the Quote will be converted and persisted into a {@link Order}.
 *
 * @author Rodrigo Balazs
 */
@Data
public class QuoteDTO {

    private String customerEmail;
    private List<QuoteItemDTO> items;
}
