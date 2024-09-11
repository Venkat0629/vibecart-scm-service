package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when there is an issue converting an order.
 * Extends {@link RuntimeException}.
 */
public class OrderConversionException extends RuntimeException {

    private String conversionError;

    /**
     * Constructs a new {@code OrderConversionException} with no detail message.
     */
    public OrderConversionException() {
        super();
    }

    /**
     * Constructs a new {@code OrderConversionException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public OrderConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code OrderConversionException} with the specified detail message
     * and cause.
     *
     * @param message the detail message.
     * @param cause the cause of the exception.
     */
    public OrderConversionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Gets the additional error information related to the conversion.
     *
     * @return the conversion error message.
     */
    public String getConversionError() {
        return conversionError;
    }

    /**
     * Sets the additional error information related to the conversion.
     *
     * @param conversionError the conversion error message.
     */
    public void setConversionError(String conversionError) {
        this.conversionError = conversionError;
    }
}
