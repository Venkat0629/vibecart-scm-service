package com.nisum.vibe.cart.scm.exception;

/**
 * Custom exception to indicate an error related to customer cancellations.
 * <p>
 * This exception is used to signal issues or constraints when a customer attempts to cancel an order
 * or similar operations that are not allowed or fail for specific reasons.
 * </p>
 */
public class CustomerCancellationException extends RuntimeException {

    /**
     * Constructs a new {@code CustomerCancellationException} with the specified detail message and cause.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CustomerCancellationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code CustomerCancellationException} with the specified detail message.
     * <p>
     * The cause is not initialized, and may be initialized later by the {@link #initCause(Throwable)} method.
     * </p>
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public CustomerCancellationException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CustomerCancellationException} with the specified cause.
     * <p>
     * The detail message is set to (cause == null ? null : cause.toString()).
     * </p>
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CustomerCancellationException(Throwable cause) {
        super(cause);
    }
}
