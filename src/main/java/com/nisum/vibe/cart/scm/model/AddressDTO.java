package com.nisum.vibe.cart.scm.model;

/**
 * Data Transfer Object (DTO) for representing an address.
 * <p>
 * Contains fields for name, email, phone number, address, city, state, and zipcode.
 * </p>
 */
public class AddressDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String zipcode;

    /**
     * Constructs a new {@code AddressDTO} with the specified values.
     *
     * @param name the name associated with the address.
     * @param email the email associated with the address.
     * @param phoneNumber the phone number associated with the address.
     * @param address the street address.
     * @param city the city of the address.
     * @param state the state of the address.
     * @param zipcode the ZIP code of the address.
     */
    public AddressDTO(String name, String email, String phoneNumber, String address, String city, String state, String zipcode) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    /**
     * Default constructor for {@code AddressDTO}.
     */
    public AddressDTO() {
    }

    /**
     * Returns the name associated with the address.
     *
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name associated with the address.
     *
     * @param name the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email associated with the address.
     *
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email associated with the address.
     *
     * @param email the email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number associated with the address.
     *
     * @return the phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number associated with the address.
     *
     * @param phoneNumber the phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the street address.
     *
     * @return the address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the street address.
     *
     * @param address the address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns the city of the address.
     *
     * @return the city.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the address.
     *
     * @param city the city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Returns the state of the address.
     *
     * @return the state.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the address.
     *
     * @param state the state.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Returns the ZIP code of the address.
     *
     * @return the ZIP code.
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the ZIP code of the address.
     *
     * @param zipcode the ZIP code.
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
