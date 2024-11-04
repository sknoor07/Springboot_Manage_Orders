package com.rbalazs.stock.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Used to intercept {@link StockCustomException}
 *
 * @author Rodrigo Balazs
 */
@ControllerAdvice
public class StockCustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Intercepts a given {@link StockCustomException} in order to return to the view an HTTP RESPONSE with the exception
     * message.
     *
     * @return an HTTP RESPONSE with a given HTTP Status and a JSON body with the exception message e.g =>
     * {the entity was not found in the Stock application}
     */
    @ExceptionHandler(StockCustomException.class)
    public ResponseEntity<Object> handleStockCustomException(final StockCustomException ex, final WebRequest request) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }
}