package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown to indicate that an invalid ZIP code was provided.
 * This exception extends {@link RuntimeException} and is used to signal issues
 * related to invalid ZIP codes in the application.
 *
 * <p>Example usage:</p>
 * <pre>
 * if (!isValidZipCode(zipCode)) {
 *     throw new InvalidZipCodeException("Invalid ZIP code provided: " + zipCode);
 * }
 * </pre>
 *
 * @see RuntimeException
 */
public class InvalidZipCodeException extends RuntimeException {

    /**
     * Constructs a new {@code InvalidZipCodeException} with the specified detail message.
     * The detail message is saved for later retrieval by the {@link #getMessage()} method.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method.
     */
    public InvalidZipCodeException(String message) {
        super(message);
    }
}
