package com.nisum.vibe.cart.scm.exception;

/**
 * Custom exception to indicate an invalid order ID was provided.
 * <p>
 * This exception is used when an order ID does not match the expected format or does not
 * correspond to a valid order in the system.
 * </p>
 */
public class InvalidOrderIdException extends RuntimeException {

    /**
     * Constructs a new InvalidOrderIdException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidOrderIdException(String message) {
        super(message);
    }
}
