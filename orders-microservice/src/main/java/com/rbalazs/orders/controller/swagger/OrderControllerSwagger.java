package com.rbalazs.orders.controller.swagger;

import com.rbalazs.orders.controller.OrderController;
import com.rbalazs.orders.dto.QuoteDTO;
import com.rbalazs.orders.model.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Swagger interface related to {@link OrderController}.
 * API Documentation/Swagger at => http://<orders_app_url>/swagger-ui/index.html
 */
@Tag(name = "Orders API", description = "API endpoints related to Orders App/Microservice")
public interface OrderControllerSwagger {

    @Operation(summary = "Retrieves all Orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns a JSON Array with all the Orders",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Order.class)))})})
    public ResponseEntity<List<Order>> getOrders();

    @Operation(summary = "Place a new Order",
            description = "Place a new Order based on the provided Quote details ( customer email + Items to purchase ).  " +
                    "This operation will also decrement the quantity in stock of the purchased Item(s) and will send a New Order email notification ( if configured )")
    public ResponseEntity<String> placeOrder(@RequestBody QuoteDTO quoteDTO);
}
