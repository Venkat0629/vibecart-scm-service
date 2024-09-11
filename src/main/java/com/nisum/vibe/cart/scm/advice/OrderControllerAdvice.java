package com.nisum.vibe.cart.scm.advice;

import com.nisum.vibe.cart.scm.exception.*;

import com.nisum.vibe.cart.scm.exception.OrderConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The {@code OrderControllerAdvice} class is a global exception handler for all controllers
 * within the package `com.nisum.vibe.cart.scm`. It handles specific exceptions related to
 * orders, such as {@link OrderNotFoundException}, {@link OrderConversionException}, and
 * {@link InvalidOrderIdException}, returning appropriate HTTP status codes and error messages.
 * This class ensures consistent error handling and responses across the application's order management components.
 */
@RestControllerAdvice(basePackages = "com.nisum.vibe.cart.scm")
public class OrderControllerAdvice {

    /**
     * Handles {@link OrderNotFoundException} and returns a 404 Not Found response with the error details.
     *
     * @param orderNotFoundException the thrown {@code OrderNotFoundException}
     * @return a {@code ResponseEntity} containing an {@code OrderError} object with the error message and HTTP status 404
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException orderNotFoundException) {
        OrderError orderError = new OrderError();
        orderError.setOrderErrorMessage(orderNotFoundException.getMessage());
        return new ResponseEntity<>(orderError, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link OrderConversionException} and returns a 500 Internal Server Error response with the error details.
     *
     * @param orderConversionException the thrown {@code OrderConversionException}
     * @return a {@code ResponseEntity} containing an {@code OrderConversionException} object with the error message and HTTP status 500
     */
    @ExceptionHandler(OrderConversionException.class)
    public ResponseEntity<Object> handleOrderConversionException(OrderConversionException orderConversionException) {
        OrderConversionException orderConversion = new OrderConversionException();
        orderConversion.setConversionError(orderConversionException.getMessage());
        return new ResponseEntity<>(orderConversion, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles {@link InvalidOrderIdException} and returns a 400 Bad Request response with the error details.
     *
     * @param invalidOrderIdException the thrown {@code InvalidOrderIdException}
     * @return a {@code ResponseEntity} containing an {@code InvalideOrderId} object with the error message and HTTP status 400
     */
    @ExceptionHandler(InvalidOrderIdException.class)
    public ResponseEntity<Object> handleInvalidOrderIdException(InvalidOrderIdException invalidOrderIdException){
        InvalideOrderId invalideId = new InvalideOrderId();
        invalideId.setInvalideOrderIdError(invalidOrderIdException.getMessage());
        return new ResponseEntity<>(invalideId, HttpStatus.BAD_REQUEST);
    }

}