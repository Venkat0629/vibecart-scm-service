package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when an order already exists.
 * Extends {@link RuntimeException}.
 */
public class OrderAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new {@code OrderAlreadyExistsException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public OrderAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code OrderAlreadyExistsException} with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public OrderAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code OrderAlreadyExistsException} with the specified cause.
     *
     * @param cause the cause of the exception.
     */
    public OrderAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
