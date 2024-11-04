package com.rbalazs.orders.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Used to intercept {@link OrderCustomException}
 */
@ControllerAdvice
public class OrderCustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Intercepts a given {@link OrderCustomException} in order to return to the view an HTTP RESPONSE with the
     * exception message.
     *
     * @return an HTTP RESPONSE with a given HTTP Status and a JSON body with the exception message e.g =>
     * {the entity was not found in the Order application}
     */
    @ExceptionHandler(OrderCustomException.class)
    public ResponseEntity<Object> handleOrderCustomException(final OrderCustomException ex, final WebRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }
}