package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when an inventory item is not found.
 * Extends {@link Exception}.
 */
public class InventoryNotFoundException extends Exception {

    /**
     * Constructs a new {@code InventoryNotFoundException} with no detail message.
     */
    public InventoryNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code InventoryNotFoundException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public InventoryNotFoundException(String message) {
        super(message);
    }
}
