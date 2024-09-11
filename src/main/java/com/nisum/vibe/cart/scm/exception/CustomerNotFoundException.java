package com.nisum.vibe.cart.scm.exception;

/**
 * Custom exception to indicate that a customer was not found in the system.
 * <p>
 * This exception is used when an operation related to customer data cannot be completed
 * because the specified customer could not be located.
 * </p>
 */
public class CustomerNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code CustomerNotFoundException} with the specified detail message.
     * <p>
     * The cause is not initialized, and may be initialized later by the {@link #initCause(Throwable)} method.
     * </p>
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CustomerNotFoundException} with the specified detail message and cause.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code CustomerNotFoundException} with the specified cause.
     * <p>
     * The detail message is initialized to {@code (cause==null ? null : cause.toString())}.
     * </p>
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CustomerNotFoundException(Throwable cause) {
        super(cause);
    }
}
