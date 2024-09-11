package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when a warehouse cannot be found.
 * Extends {@link Exception}.
 */
public class WarehouseNotFoundException extends Exception {

    /**
     * Constructs a new {@code WarehouseNotFoundException} with no detail message.
     */
    public WarehouseNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code WarehouseNotFoundException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public WarehouseNotFoundException(String message) {
        super(message);
    }
}
