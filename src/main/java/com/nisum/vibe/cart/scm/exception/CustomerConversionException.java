package com.nisum.vibe.cart.scm.exception;

/**
 * Custom exception to handle errors related to customer data conversion.
 * <p>
 * This exception is used when there are issues or failures in converting or processing customer data,
 * such as errors during data mapping or transformations.
 * </p>
 */
public class CustomerConversionException extends RuntimeException {

    /**
     * Constructs a new {@code CustomerConversionException} with the specified detail message.
     * <p>
     * The cause is not initialized, and may be initialized later by the {@link #initCause(Throwable)} method.
     * </p>
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     */
    public CustomerConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CustomerConversionException} with the specified detail message and cause.
     *
     * @param message the detail message, which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CustomerConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
