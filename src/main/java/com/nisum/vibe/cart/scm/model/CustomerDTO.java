package com.nisum.vibe.cart.scm.model;

/**
 * Data Transfer Object (DTO) for representing a customer.
 * <p>
 * Contains fields for customer ID, name, email, and phone number.
 * </p>
 */
public class CustomerDTO {
    private Long customerId;
    private String customerName;
    private String email;
    private String phoneNumber;

    /**
     * Constructs a new {@code CustomerDTO} with the specified values.
     *
     * @param customerId the unique identifier of the customer.
     * @param customerName the name of the customer.
     * @param email the email address of the customer.
     * @param phoneNumber the phone number of the customer.
     */
    public CustomerDTO(Long customerId, String customerName, String email, String phoneNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Default constructor for {@code CustomerDTO}.
     */
    public CustomerDTO() {
    }

    /**
     * Sets the unique identifier of the customer.
     *
     * @param customerId the unique identifier.
     */
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the unique identifier of the customer.
     *
     * @return the unique identifier.
     */
    public Long getCustomerId() {
        return customerId;
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
     * @param customerName the customer name.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the email address of the customer.
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the customer.
     *
     * @param email the email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the customer.
     *
     * @return the phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phoneNumber the phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
