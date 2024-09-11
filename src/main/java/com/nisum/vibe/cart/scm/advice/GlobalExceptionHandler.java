package com.nisum.vibe.cart.scm.advice;

import com.nisum.vibe.cart.scm.ResponseEntity.ApiResponse;
import com.nisum.vibe.cart.scm.controller.OrderController;
import com.nisum.vibe.cart.scm.exception.InventoryNotFoundException;
import com.nisum.vibe.cart.scm.exception.OrderAlreadyExistsException;
import com.nisum.vibe.cart.scm.exception.OrderNotFoundException;
import com.nisum.vibe.cart.scm.exception.WarehouseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The {@code GlobalExceptionHandler} class is a centralized exception handler for the {@link OrderController}.
 * It handles various custom exceptions such as {@link OrderNotFoundException}, {@link OrderAlreadyExistsException},
 * {@link WarehouseNotFoundException}, and {@link InventoryNotFoundException}, providing appropriate HTTP responses.
 * This class improves error handling and ensures consistent response formats across the application.
 */
@RestControllerAdvice(assignableTypes = {OrderController.class})
public class GlobalExceptionHandler {

    /**
     * Handles {@link OrderNotFoundException} and returns a 404 Not Found response with the exception message.
     *
     * @param e the thrown {@code OrderNotFoundException}
     * @return a {@code ResponseEntity} containing the error message and HTTP status 404
     */
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link OrderAlreadyExistsException} and returns a 409 Conflict response with the exception message.
     *
     * @param e the thrown {@code OrderAlreadyExistsException}
     * @return a {@code ResponseEntity} containing the error message and HTTP status 409
     */
    @ExceptionHandler(OrderAlreadyExistsException.class)
    public ResponseEntity<String> handleOrderAlreadyExistsException(OrderAlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link WarehouseNotFoundException} and returns a 404 Not Found response wrapped in an {@code ApiResponse}.
     *
     * @param exception the thrown {@code WarehouseNotFoundException}
     * @return a {@code ResponseEntity} containing an {@code ApiResponse} object with the error message and HTTP status 404
     */
    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> displayWarehouseNotFoundExceptionMessage(WarehouseNotFoundException exception) {
        String message = exception.getMessage();
        ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles {@link InventoryNotFoundException} and returns a 404 Not Found response wrapped in an {@code ApiResponse}.
     *
     * @param exception the thrown {@code InventoryNotFoundException}
     * @return a {@code ResponseEntity} containing an {@code ApiResponse} object with the error message and HTTP status 404
     */
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> displayInventoryNotFoundExceptionMessage(InventoryNotFoundException exception) {
        String message = exception.getMessage();
        ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

