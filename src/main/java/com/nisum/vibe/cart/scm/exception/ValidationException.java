package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when a validation error occurs.
 * Extends {@link RuntimeException}.
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructs a new {@code ValidationException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public ValidationException(String message) {
        super(message);
    }
}
