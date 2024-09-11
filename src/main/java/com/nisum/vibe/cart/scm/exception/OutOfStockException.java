package com.nisum.vibe.cart.scm.exception;

/**
 * Exception thrown when an item is out of stock.
 * Extends {@link Exception}.
 */
public class OutOfStockException extends Exception {

    /**
     * Constructs a new {@code OutOfStockException} with no detail message.
     */
    public OutOfStockException() {
        super();
    }

    /**
     * Constructs a new {@code OutOfStockException} with the specified detail message.
     *
     * @param message the detail message.
     */
    public OutOfStockException(String message) {
        super(message);
    }
}
