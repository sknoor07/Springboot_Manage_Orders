package com.rbalazs.stock.exception;

import com.rbalazs.stock.enums.StockAppValidations;
import org.springframework.http.HttpStatus;

/**
 * Custom Exception related to the Stock app/microservice,
 * the error messages/status codes could be taken from {@link StockAppValidations}
 */
public class StockCustomException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public StockCustomException(final StockAppValidations stockAppValidations) {
        this.status = stockAppValidations.getHttpStatus();
        this.message = stockAppValidations.getMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}