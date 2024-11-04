package com.rbalazs.stock.controller.swagger;

import com.rbalazs.stock.controller.StockController;
import com.rbalazs.stock.enums.StockAppConstants;
import com.rbalazs.stock.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * Swagger interface related to {@link StockController}.
 * API Documentation/Swagger at => http://<stock_app_url>/swagger-ui/index.html
 */
@Tag(name = "Stock API", description = "API endpoints related to the Stock App/Microservice")
public interface StockControllerSwagger {

    @Operation(summary = "Retrieves all Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns a JSON Array with all the Products",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Product.class)))})})
    public ResponseEntity<List<Product>> getProducts();

    @Operation(summary = "Retrieves a Product by Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "returns a JSON Object with the Product stock information",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Product.class))})})
    public ResponseEntity<Product> getProductByName(
            @Parameter(description = "product name", example = StockAppConstants.PRODUCT_SOFA_NAME, required = true)
            @PathVariable String name);

    @Operation(summary = "Retrieves whether the requested quantity of the Product given as parameter is in Stock or not")
    public boolean isInStock(
            @Parameter(description = "product name to check stock availability", example = StockAppConstants.PRODUCT_SOFA_NAME, required = true)
            @RequestParam(value = "productName") String productName,

            @Parameter(description = "quantity to check", example = "46", required = true)
            @RequestParam(value = "requestedQuantity") int requestedQuantity);

    @Operation(summary = "Decrease the available quantity of the Product given as parameter")
    public ResponseEntity<String> decreaseProductAvailableQuantity(
            @Parameter(description = "product name", example = StockAppConstants.PRODUCT_SOFA_NAME, required = true)
            @RequestParam(value = "productName") String productName,

            @Parameter(description = "quantity to decrease", example = "4", required = true)
            @RequestParam(value = "quantityToDecrease") int quantityToDecrease);
}
