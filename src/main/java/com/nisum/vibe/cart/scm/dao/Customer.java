package com.nisum.vibe.cart.scm.dao;

import javax.persistence.Embeddable;

/**
 * Represents a customer entity that can be embedded in other entities.
 * This entity contains details such as customer ID, name, email, and phone number.
 *
 * <p>
 * It is marked as {@code @Embeddable} so that it can be embedded within other JPA entities.
 * </p>
 */
@Embeddable
public class Customer {

    private Long customerId;
    private String customerName;
    private String email;
    private String phoneNumber;

    /**
     * Constructs a new {@code Customer} with the specified customer ID.
     *
     * @param customerId the unique identifier of the customer.
     */
    public Customer(long customerId) {
        this.customerId = customerId;
    }

    /**
     * Constructs a new {@code Customer} with no specified details.
     * Default constructor required for JPA.
     */
    public Customer() {
    }

    /**
     * Returns the unique identifier of the customer.
     *
     * @return the customer ID.
     */
    public Long getCustomerId() {
        return customerId;
    }

    /**
     * Sets the unique identifier of the customer.
     *
     * @param customerId the customer ID to set.
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the name of the customer.
     *
     * @return the customer name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer.
     *
     * @param customerName the name to set for the customer.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the email address of the customer.
     *
     * @return the customer's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     *
     * @param email the email to set for the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the customer.
     *
     * @return the customer's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phoneNumber the phone number to set for the customer.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
