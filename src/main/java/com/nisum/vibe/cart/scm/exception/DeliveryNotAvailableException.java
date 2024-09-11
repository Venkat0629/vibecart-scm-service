package com.nisum.vibe.cart.scm.exception;

/**
 * Custom exception to indicate that delivery is not available for a given order or location.
 * <p>
 * This exception is used when a requested delivery cannot be fulfilled due to issues such as
 * the delivery address being outside the serviceable area, or other delivery constraints.
 * </p>
 */
public class DeliveryNotAvailableException extends RuntimeException {

    /**
     * Constructs a new {@code DeliveryNotAvailableException} with the specified detail message.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public DeliveryNotAvailableException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DeliveryNotAvailableException} with the specified detail message and cause.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public DeliveryNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
