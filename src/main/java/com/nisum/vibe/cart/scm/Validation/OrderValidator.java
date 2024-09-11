package com.nisum.vibe.cart.scm.Validation;

import com.nisum.vibe.cart.scm.exception.ValidationException;
import com.nisum.vibe.cart.scm.model.AddressDTO;
import com.nisum.vibe.cart.scm.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@code OrderValidator} class provides static methods to validate {@link OrderDTO} objects.
 * It checks for required fields, ensures values are valid, and verifies that addresses and quantities are correctly specified.
 * Use {@link #validateOrder(OrderDTO)} to perform comprehensive validation on an OrderDTO before processing.
 */
public class OrderValidator {

    private static final Logger logger = LoggerFactory.getLogger(OrderValidator.class);

    /**
     * Validates the given {@link OrderDTO} object.
     *
     * @param orderDTO the {@link OrderDTO} to validate
     * @throws ValidationException if any of the validation rules are violated
     */
    public static void validateOrder(OrderDTO orderDTO) {
        logger.debug("Starting validation for OrderDTO: {}", orderDTO);

        if (orderDTO == null) {
            logger.error("Validation failed: OrderDTO cannot be null");
            throw new ValidationException("OrderDTO cannot be null");
        }

        if (orderDTO.getOrderId() == null) {
            logger.error("Validation failed: Order ID should not be null");
            throw new ValidationException("Order ID should not be null");
        }

        if (orderDTO.getOrderDate() == null) {
            logger.error("Validation failed: Order date cannot be null");
            throw new ValidationException("Order date cannot be null");
        }

        if (orderDTO.getCreatedDate() == null) {
            logger.error("Validation failed: Created date cannot be null");
            throw new ValidationException("Created date cannot be null");
        }

        validateDouble(orderDTO.getTotalAmount(), "Total Amount");

        if (orderDTO.getTotalQuantity() <= 0) {
            logger.error("Validation failed: Total quantity must be greater than 0");
            throw new ValidationException("Total quantity must be greater than 0");
        }

        if (orderDTO.getEstimated_delivery_date() == null) {
            logger.error("Validation failed: Estimated delivery date cannot be null");
            throw new ValidationException("Estimated delivery date cannot be null");
        }

        AddressDTO shippingAddress = orderDTO.getShippingAddress();
        if (shippingAddress == null ||
                shippingAddress.getName() == null || shippingAddress.getName().trim().isEmpty() ||
                shippingAddress.getEmail() == null || shippingAddress.getEmail().trim().isEmpty() ||
                shippingAddress.getPhoneNumber() == null || shippingAddress.getPhoneNumber().trim().isEmpty() ||
                shippingAddress.getAddress() == null || shippingAddress.getAddress().trim().isEmpty() ||
                shippingAddress.getCity() == null || shippingAddress.getCity().trim().isEmpty() ||
                shippingAddress.getState() == null || shippingAddress.getState().trim().isEmpty() ||
                shippingAddress.getZipcode() == null || shippingAddress.getZipcode().trim().isEmpty()) {
            logger.error("Validation failed: Shipping address cannot be null or contain empty fields");
            throw new ValidationException("Shipping address cannot be null or contain empty fields");
        }

        AddressDTO billingAddress = orderDTO.getBillingAddress();
        if (billingAddress == null ||
                billingAddress.getName() == null || billingAddress.getName().trim().isEmpty() ||
                billingAddress.getEmail() == null || billingAddress.getEmail().trim().isEmpty() ||
                billingAddress.getPhoneNumber() == null || billingAddress.getPhoneNumber().trim().isEmpty() ||
                billingAddress.getAddress() == null || billingAddress.getAddress().trim().isEmpty() ||
                billingAddress.getCity() == null || billingAddress.getCity().trim().isEmpty() ||
                billingAddress.getState() == null || billingAddress.getState().trim().isEmpty() ||
                billingAddress.getZipcode() == null || billingAddress.getZipcode().trim().isEmpty()) {
            logger.error("Validation failed: Billing address cannot be null or contain empty fields");
            throw new ValidationException("Billing address cannot be null or contain empty fields");
        }

        if (orderDTO.getShippingzipCode() == null) {
            logger.error("Validation failed: Shipping ZIP code cannot be null");
            throw new ValidationException("Shipping ZIP code cannot be null");
        }

        if (orderDTO.getOrderStatus() == null) {
            logger.error("Validation failed: Order status cannot be null");
            throw new ValidationException("Order status cannot be null");
        }

        if (orderDTO.getPaymentStatus() == null) {
            logger.error("Validation failed: Payment status cannot be null");
            throw new ValidationException("Payment status cannot be null");
        }

        if (orderDTO.getOrderItems() == null || orderDTO.getOrderItems().isEmpty()) {
            logger.error("Validation failed: Order items cannot be null or empty");
            throw new ValidationException("Order items cannot be null or empty");
        }

        logger.debug("Validation successful for OrderDTO: {}", orderDTO);
    }

    /**
     * Validates a Double value ensuring it is not null and greater than 0.
     *
     * @param value     the Double value to validate
     * @param fieldName the name of the field being validated
     * @throws ValidationException if the value is null or not greater than 0
     */
    private static void validateDouble(Double value, String fieldName) {
        if (value == null) {
            logger.error("Validation failed: {} cannot be null", fieldName);
            throw new ValidationException(fieldName + " cannot be null");
        }
        if (value <= 0) {
            logger.error("Validation failed: {} must be greater than 0", fieldName);
            throw new ValidationException(fieldName + " must be greater than 0");
        }
    }
}


