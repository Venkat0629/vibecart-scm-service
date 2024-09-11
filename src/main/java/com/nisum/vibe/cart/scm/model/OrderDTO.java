package com.nisum.vibe.cart.scm.model;

import com.nisum.vibe.cart.scm.exception.InvalidOrderIdException;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) for representing an order.
 * <p>
 * This class is used to transfer order data between different layers of the application. It contains details about the
 * order including customer information, order status, payment status, and the list of order items.
 * </p>
 */
public class OrderDTO {

    private String orderId;
    private CustomerDTO customer;
    private List<OrderItemDTO> orderItems;
    private Instant orderDate;
    private Instant createdDate;
    private Instant updatedDate;
    private double totalAmount;
    private double subTotal;
    private BigDecimal discountPrice;
    private Long offerId;
    private int totalQuantity;
    private LocalDateTime estimated_delivery_date;
    private AddressDTO shippingAddress;
    private AddressDTO billingAddress;
    private Long shippingzipCode;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;

    /**
     * Default constructor.
     */
    public OrderDTO() {
    }

    /**
     * Constructs an OrderDTO with the specified parameters.
     *
     * @param orderId the unique identifier for the order
     * @param customerDTO the customer details
     * @param orderItems the list of items in the order
     * @param orderDate the date when the order was placed
     * @param createdDate the date when the order was created
     * @param updatedDate the date when the order was last updated
     * @param totalAmount the total amount of the order
     * @param subTotal the subtotal amount of the order
     * @param discountPrice the discount applied to the order
     * @param offerId the identifier for any offer applied to the order
     * @param totalQuantity the total quantity of items in the order
     * @param estimated_delivery_date the estimated delivery date of the order
     * @param shippingAddress the shipping address for the order
     * @param billingAddress the billing address for the order
     * @param shippingzipCode the shipping zip code
     * @param orderStatus the status of the order
     * @param paymentStatus the payment status of the order
     * @param paymentMethod the payment method used for the order
     */
    public OrderDTO(String orderId, CustomerDTO customerDTO, List<OrderItemDTO> orderItems, Instant orderDate, Instant createdDate, Instant updatedDate, double totalAmount, double subTotal, BigDecimal discountPrice, Long offerId, int totalQuantity, LocalDateTime estimated_delivery_date, AddressDTO shippingAddress, AddressDTO billingAddress, Long shippingzipCode, OrderStatus orderStatus, PaymentStatus paymentStatus, PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.customer = customerDTO;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.totalAmount = totalAmount;
        this.subTotal = subTotal;
        this.discountPrice = discountPrice;
        this.offerId = offerId;
        this.totalQuantity = totalQuantity;
        this.estimated_delivery_date = estimated_delivery_date;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.shippingzipCode = shippingzipCode;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.paymentMethod = paymentMethod;
    }

    /**
     * Returns the unique identifier for the order.
     *
     * @return the order ID
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the unique identifier for the order.
     *
     * @param orderId the order ID
     * @throws InvalidOrderIdException if the order ID is null or empty
     */
    public void setOrderId(String orderId) {
        if(orderId.trim().isEmpty()){
            throw new InvalidOrderIdException("Order Id should not be null or Empty");
        }
        this.orderId = orderId;
    }

    /**
     * Returns the customer details.
     *
     * @return the customer DTO
     */
    public CustomerDTO getCustomer() {
        return customer;
    }

    /**
     * Sets the customer details.
     *
     * @param customer the customer DTO
     */
    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    /**
     * Returns the list of items in the order.
     *
     * @return the list of order item DTOs
     */
    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    /**
     * Sets the list of items in the order.
     *
     * @param orderItems the list of order item DTOs
     */
    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Returns the date when the order was placed.
     *
     * @return the order date
     */
    public Instant getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date when the order was placed.
     *
     * @param orderDate the order date
     * @throws NullPointerException if the order date is null
     */
    public void setOrderDate(Instant orderDate) {
        if (orderDate == null) {
            throw new NullPointerException("OrderDate Cannot be Null");
        }
        this.orderDate = orderDate;
    }

    /**
     * Returns the date when the order was created.
     *
     * @return the created date
     */
    public Instant getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date when the order was created.
     *
     * @param createdDate the created date
     */
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Returns the date when the order was last updated.
     *
     * @return the updated date
     */
    public Instant getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Sets the date when the order was last updated.
     *
     * @param updatedDate the updated date
     */
    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Returns the total amount of the order.
     *
     * @return the total amount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the order.
     *
     * @param totalAmount the total amount
     * @throws IllegalArgumentException if the total amount is zero or negative
     */
    public void setTotalAmount(double totalAmount) {
        if (totalAmount == 0 || totalAmount <= 0) {
            throw new IllegalArgumentException("Total Amount Can't be Zero");
        }
        this.totalAmount = totalAmount;
    }

    /**
     * Returns the total quantity of items in the order.
     *
     * @return the total quantity
     */
    public int getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Sets the total quantity of items in the order.
     *
     * @param totalQuantity the total quantity
     * @throws IllegalArgumentException if the total quantity is zero or negative
     */
    public void setTotalQuantity(int totalQuantity) {
        if (totalQuantity == 0 || totalQuantity <= 0) {
            throw new IllegalArgumentException("The Minimum Quantity should be 1");
        }
        this.totalQuantity = totalQuantity;
    }

    /**
     * Returns the estimated delivery date of the order.
     *
     * @return the estimated delivery date
     */
    public LocalDateTime getEstimated_delivery_date() {
        return estimated_delivery_date;
    }

    /**
     * Sets the estimated delivery date of the order.
     *
     * @param estimated_delivery_date the estimated delivery date
     * @throws NullPointerException if the estimated delivery date is null
     */
    public void setEstimated_delivery_date(LocalDateTime estimated_delivery_date) {
        if (estimated_delivery_date == null) {
            throw new NullPointerException("Estimated_delivery_date cannot be Null");
        }
        this.estimated_delivery_date = estimated_delivery_date;
    }

    /**
     * Returns the shipping address for the order.
     *
     * @return the shipping address DTO
     */
    public AddressDTO getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sets the shipping address for the order.
     *
     * @param shippingAddress the shipping address DTO
     * @throws NullPointerException if any field in the address is null or empty
     */
    public void setShippingAddress(AddressDTO shippingAddress) {
        if (shippingAddress.getZipcode() == "" || shippingAddress.getState() == "" || shippingAddress.getAddress() == "" || shippingAddress.getCity() == "") {
            throw new NullPointerException("Address field's Can't be Null");
        }
        this.shippingAddress = shippingAddress;
    }

    /**
     * Returns the billing address for the order.
     *
     * @return the billing address DTO
     */
    public AddressDTO getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the billing address for the order.
     *
     * @param billingAddress the billing address DTO
     * @throws NullPointerException if any field in the address is null or empty
     */
    public void setBillingAddress(AddressDTO billingAddress) {
        if (billingAddress.getAddress() == "" || billingAddress.getCity() == "" || billingAddress.getState() == "" || billingAddress.getZipcode() == "") {
            throw new NullPointerException("Billing Address Can't be Null");
        }
        this.billingAddress = billingAddress;
    }

    /**
     * Returns the shipping zip code.
     *
     * @return the shipping zip code
     */
    public Long getShippingzipCode() {
        return shippingzipCode;
    }

    /**
     * Sets the shipping zip code.
     *
     * @param shippingzipCode the shipping zip code
     * @throws IllegalArgumentException if the zip code is zero or negative
     */
    public void setShippingzipCode(Long shippingzipCode) {
        if (shippingzipCode == 0 || shippingzipCode <= 0) {
            throw new IllegalArgumentException("Shipping Zipcode should be valid it can't be zero or Negative");
        }
        this.shippingzipCode = shippingzipCode;
    }

    /**
     * Returns the status of the order.
     *
     * @return the order status
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the status of the order.
     *
     * @param orderStatus the order status
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Returns the payment status of the order.
     *
     * @return the payment status
     */
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status of the order.
     *
     * @param paymentStatus the payment status
     */
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Returns the payment method used for the order.
     *
     * @return the payment method
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the payment method used for the order.
     *
     * @param paymentMethod the payment method
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Returns the discount applied to the order.
     *
     * @return the discount price
     */
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    /**
     * Sets the discount applied to the order.
     *
     * @param discountPrice the discount price
     */
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * Returns the identifier for any offer applied to the order.
     *
     * @return the offer ID
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     * Sets the identifier for any offer applied to the order.
     *
     * @param offerId the offer ID
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    /**
     * Returns the subtotal amount of the order.
     *
     * @return the subtotal amount
     */
    public double getSubTotal() {
        return subTotal;
    }

    /**
     * Sets the subtotal amount of the order.
     *
     * @param subTotal the subtotal amount
     */
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
