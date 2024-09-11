package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when there is an issue with order cancellation.
 * Extends {@link RuntimeException}.
 */
public class OrderCancellationException extends RuntimeException {

    /**
     * Constructs a new {@code OrderCancellationException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public OrderCancellationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code OrderCancellationException} with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public OrderCancellationException(String message, Throwable cause) {
        super(message, cause);
    }
}
