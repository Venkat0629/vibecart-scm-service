package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when an order cannot be found.
 * Extends {@link RuntimeException}.
 */
public class OrderNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code OrderNotFoundException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public OrderNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code OrderNotFoundException} with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code OrderNotFoundException} with the specified cause.
     *
     * @param cause the cause of the exception.
     */
    public OrderNotFoundException(Throwable cause) {
        super(cause);
    }
}
