package com.nisum.vibe.cart.scm.exception;

/**
 * Custom exception to indicate an error occurred during the estimation of delivery.
 * <p>
 * This exception is used when there is an issue with estimating the delivery date
 * for an order, such as an error in retrieving or calculating the estimated delivery
 * information.
 * </p>
 */
public class DeliveryEstimationException extends RuntimeException {

    /**
     * Constructs a new {@code DeliveryEstimationException} with the specified detail message and cause.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public DeliveryEstimationException(String message, Throwable cause) {
        super(message, cause);
    }
}
