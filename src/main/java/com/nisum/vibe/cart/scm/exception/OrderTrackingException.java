package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when an error occurs while tracking an order.
 * Extends {@link RuntimeException}.
 */
public class OrderTrackingException extends RuntimeException {

    /**
     * Constructs a new {@code OrderTrackingException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public OrderTrackingException(String message) {
        super(message);
    }
}
