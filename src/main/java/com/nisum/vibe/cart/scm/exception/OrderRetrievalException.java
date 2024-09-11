package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when an error occurs during order retrieval.
 * Extends {@link RuntimeException}.
 */
public class OrderRetrievalException extends RuntimeException {

    /**
     * Constructs a new {@code OrderRetrievalException} with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public OrderRetrievalException(String message, Throwable cause) {
        super(message, cause);
    }
}
