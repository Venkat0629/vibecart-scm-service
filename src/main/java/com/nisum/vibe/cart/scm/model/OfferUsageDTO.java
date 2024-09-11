package com.nisum.vibe.cart.scm.model;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) for representing offer usage information.
 * Implements {@link Serializable} for object serialization.
 */
public class OfferUsageDTO implements Serializable {

    private Long offerUsageId;
    private Long offerId;
    private String orderId;
    private Long customerId;
    private String email;

    /**
     * Gets the ID of the offer usage.
     *
     * @return the offer usage ID
     */
    public Long getOfferUsageId() {
        return offerUsageId;
    }

    /**
     * Sets the ID of the offer usage.
     *
     * @param offerUsageId the offer usage ID to set
     * @return the current instance of {@link OfferUsageDTO}
     */
    public OfferUsageDTO setOfferUsageId(Long offerUsageId) {
        this.offerUsageId = offerUsageId;
        return this;
    }

    /**
     * Gets the ID of the offer associated with this usage.
     *
     * @return the offer ID
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     * Sets the ID of the offer associated with this usage.
     *
     * @param offerId the offer ID to set
     * @return the current instance of {@link OfferUsageDTO}
     */
    public OfferUsageDTO setOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    /**
     * Gets the ID of the order related to this offer usage.
     *
     * @return the order ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the ID of the order related to this offer usage.
     *
     * @param orderId the order ID to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Gets the ID of the customer who used the offer.
     *
     * @return the customer ID
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the ID of the customer who used the offer.
     *
     * @param customerId the customer ID to set
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the email of the customer who used the offer.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the customer who used the offer.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
