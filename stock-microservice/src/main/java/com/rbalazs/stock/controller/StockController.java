package com.rbalazs.stock.controller;

import com.rbalazs.stock.controller.swagger.StockControllerSwagger;
import com.rbalazs.stock.model.Product;
import com.rbalazs.stock.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Stock REST Controller.
 * API Documentation/Swagger at => http://<stock_app_url>/swagger-ui/index.html
 */
@RestController
@RequestMapping("/stock")
public class StockController implements StockControllerSwagger {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockController.class);
    private final StockService stockService;

    @Autowired
    public StockController(final StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/get-products")
    public ResponseEntity<List<Product>> getProducts() {
        LOGGER.info("starts to execute stockController.getProducts()");
        List<Product> products = stockService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable("name") String name) {
        LOGGER.info("starts to execute stockController.getProductByName() with name:{}" , name);
        Product product = stockService.getProductByName(name);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/is-in-stock")
    public boolean isInStock(@RequestParam(value = "productName") String productName,
                             @RequestParam(value = "requestedQuantity") int requestedQuantity) {
        LOGGER.info("starts to execute stockController.isInStock() with product name:{}", productName);
        return stockService.isInStock(productName, requestedQuantity);
    }

    @PutMapping("/decrease-product-available-quantity")
    public ResponseEntity<String> decreaseProductAvailableQuantity(@RequestParam(value = "productName") String productName,
                                                                   @RequestParam(value = "quantityToDecrease") int quantityToDecrease) {
        LOGGER.info("starts to execute stockController.decreaseProductAvailableQuantity() with product name:{}", productName);
        stockService.decreaseProductAvailableQuantity(productName, quantityToDecrease);
        return ResponseEntity.ok("the product available quantity has been successfully decreased");
    }
}

