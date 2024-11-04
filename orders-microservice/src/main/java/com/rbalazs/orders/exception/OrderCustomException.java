package com.rbalazs.orders.exception;

import com.rbalazs.orders.enums.OrderAppValidations;
import org.springframework.http.HttpStatus;

/**
 * Custom Exception related to the Order app/microservice,
 * the error messages/status codes could be taken from {@link OrderAppValidations}
 */
public class OrderCustomException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public OrderCustomException(final OrderAppValidations orderAppValidations) {
        this.status = orderAppValidations.getHttpStatus();
        this.message = orderAppValidations.getMessage();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}