package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when an error occurs while updating an order.
 * Extends {@link RuntimeException}.
 */
public class OrderUpdateException extends RuntimeException {

    /**
     * Constructs a new {@code OrderUpdateException} with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public OrderUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code OrderUpdateException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public OrderUpdateException(String message) {
        super(message);
    }
}
