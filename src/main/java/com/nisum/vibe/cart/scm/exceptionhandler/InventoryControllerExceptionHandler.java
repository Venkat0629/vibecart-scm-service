package com.nisum.vibe.cart.scm.exceptionhandler;


import com.nisum.vibe.cart.scm.ResponseEntity.ApiResponse;
import com.nisum.vibe.cart.scm.controller.InventoryController;
import com.nisum.vibe.cart.scm.exception.InventoryNotFoundException;
import com.nisum.vibe.cart.scm.exception.WarehouseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for inventory-related exceptions in the VibeCart application.
 * This class handles exceptions thrown by the InventoryController and provides
 * appropriate HTTP responses.
 *
 * <p>
 * Extends {@link ResponseEntityExceptionHandler} to leverage Spring's default exception handling capabilities.
 */
@ControllerAdvice(assignableTypes = {InventoryController.class})
public class InventoryControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link InventoryNotFoundException} thrown by the controller methods.
     *
     * @param exception the exception thrown when an inventory item is not found
     * @return a {@link ResponseEntity} containing the exception message and a 404 NOT FOUND status
     */
    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> displayInventoryNotFoundExceptionMessage(InventoryNotFoundException exception){

        String message = exception.getMessage();
        ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handles {@link WarehouseNotFoundException} thrown by the controller methods.
     *
     * @param exception the exception thrown when a warehouse is not found
     * @return a {@link ResponseEntity} containing the exception message and a 404 NOT FOUND status
     */
    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> displayWarehouseNotFoundExceptionMessage(WarehouseNotFoundException exception){

        String message = exception.getMessage();
        ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
