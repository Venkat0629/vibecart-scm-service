package com.nisum.vibe.cart.scm.exception;

/**
 * Custom exception to indicate an invalid order ID was provided.
 * <p>
 * This exception is used when an order ID does not match the expected format or does not
 * correspond to a valid order in the system.
 * </p>
 */
public class InvalideOrderId {

    public String getInvalideOrderIdError() {
        return InvalideOrderIdError;
    }

    /**
     * Retrieves the error message associated with this exception.
     *
     * @return the error message
     */
    public void setInvalideOrderIdError(String invalideOrderIdError) {
        InvalideOrderIdError = invalideOrderIdError;
    }

    public String InvalideOrderIdError;

}
