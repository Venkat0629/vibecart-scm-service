package com.nisum.vibe.cart.scm.exception;

/**
 * Custom exception to indicate that an invalid customer ID was provided.
 * <p>
 * This exception is used when a customer ID does not match the expected format or does not
 * correspond to a valid customer in the system.
 * </p>
 */
public class InvalidCustomerIdException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidCustomerIdException} with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public InvalidCustomerIdException(String message) {
        super(message);
    }
}
