package com.nisum.vibe.cart.scm.dao;

import javax.persistence.Embeddable;

/**
 * Represents an embeddable entity for an address.
 * <p>
 * This entity includes information such as the recipient's name, email,
 * physical address, city, state, ZIP code, and phone number.
 * </p>
 * <p>
 * It is marked as {@code @Embeddable} to indicate that it can be embedded
 * within other JPA entities.
 * </p>
 */
@Embeddable
public class Address {
    private String name;
    private String email;
    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String phoneNumber;

    /**
     * Constructs a new {@code Address} with the specified details.
     *
     * @param name        the name of the recipient.
     * @param email       the email address of the recipient.
     * @param address     the physical address of the recipient.
     * @param city        the city of the recipient's address.
     * @param state       the state of the recipient's address.
     * @param zipcode     the ZIP code of the recipient's address.
     * @param phoneNumber the phone number of the recipient.
     */
    public Address(String name, String email, String address, String city, String state, String zipcode, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Constructs a new {@code Address} with no specified details.
     * Default constructor required for JPA.
     */
    public Address() {

    }

    /**
     * Returns the name of the recipient.
     *
     * @return the recipient's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the recipient.
     *
     * @param name the name to set for the recipient.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email address of the recipient.
     *
     * @return the recipient's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the recipient.
     *
     * @param email the email to set for the recipient.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the physical address of the recipient.
     *
     * @return the recipient's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the physical address of the recipient.
     *
     * @param address the address to set for the recipient.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the city of the recipient's address.
     *
     * @return the recipient's city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the recipient's address.
     *
     * @param city the city to set for the recipient.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the state of the recipient's address.
     *
     * @return the recipient's state.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the recipient's address.
     *
     * @param state the state to set for the recipient.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the ZIP code of the recipient's address.
     *
     * @return the recipient's ZIP code.
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the ZIP code of the recipient's address.
     *
     * @param zipcode the ZIP code to set for the recipient.
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Returns the phone number of the recipient.
     *
     * @return the recipient's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the recipient.
     *
     * @param phoneNumber the phone number to set for the recipient.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
