package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when there is an issue creating an order.
 * Extends {@link RuntimeException}.
 */
public class OrderCreationException extends RuntimeException {

    /**
     * Constructs a new {@code OrderCreationException} with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public OrderCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
