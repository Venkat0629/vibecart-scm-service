package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when there is invalid data related to an order.
 * <p>
 * This exception is used to indicate that the order data provided does not meet the expected format or constraints.
 * </p>
 */
public class InvalidOrderDataException extends RuntimeException {

    /**
     * Constructs a new InvalidOrderDataException with the specified detail message.
     * <p>
     * The detail message is a string that provides more information about the error that caused this exception.
     * </p>
     *
     * @param message the detail message
     */
    public InvalidOrderDataException(String message) {
        super(message);
    }
}
