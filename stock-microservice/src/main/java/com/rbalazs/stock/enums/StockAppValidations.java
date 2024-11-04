package com.rbalazs.stock.enums;

import org.springframework.http.HttpStatus;

/**
 * Enum which contains some validation messages related to the Stock app/microservice.
 */
public enum StockAppValidations {
    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "the Product was not found in the Application");

    private final HttpStatus httpStatus;
    private final String message;

    StockAppValidations(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
