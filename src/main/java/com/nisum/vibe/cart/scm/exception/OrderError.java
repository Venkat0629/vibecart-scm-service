package com.nisum.vibe.cart.scm.exception;

/**
 * Represents an error related to order processing.
 */
public class OrderError {

    private String orderErrorMessage;

    /**
     * Gets the error message associated with the order.
     *
     * @return the order error message.
     */
    public String getOrderErrorMessage() {
        return orderErrorMessage;
    }

    /**
     * Sets the error message associated with the order.
     *
     * @param orderErrorMessage the order error message.
     */
    public void setOrderErrorMessage(String orderErrorMessage) {
        this.orderErrorMessage = orderErrorMessage;
    }
}
