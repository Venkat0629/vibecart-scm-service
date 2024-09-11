package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when an error occurs while saving an order.
 * Extends {@link RuntimeException}.
 */
public class OrderSaveException extends RuntimeException {

    /**
     * Constructs a new {@code OrderSaveException} with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public OrderSaveException(String message, Throwable cause) {
        super(message, cause);
    }
}
