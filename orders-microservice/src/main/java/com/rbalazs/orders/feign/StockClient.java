package com.rbalazs.orders.feign;

import com.rbalazs.orders.enums.OrderAppConstants;
import com.rbalazs.stock.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Client used to communicate against the Stock app/microservice via Spring Cloud OpenFeign.
 *
 * @author Rodrigo Balazs
 */
@FeignClient(name = OrderAppConstants.STOCK_MICROSERVICE, url = OrderAppConstants.STOCK_MICROSERVICE_URL)
public interface StockClient {

    @GetMapping("/get-products")
    public ResponseEntity<List<Product>> getProducts();

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable("name") String name);

    @GetMapping("/is-in-stock")
    public boolean isInStock(@RequestParam(value = "productName") String productName,
                             @RequestParam(value = "requestedQuantity") int requestedQuantity);

    @PutMapping("/decrease-product-available-quantity")
    public ResponseEntity<String> decreaseProductAvailableQuantity(@RequestParam(value = "productName") String productName,
                                                                   @RequestParam(value = "quantityToDecrease") int quantityToDecrease);
}
