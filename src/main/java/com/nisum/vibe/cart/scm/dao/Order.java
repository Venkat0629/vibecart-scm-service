package com.nisum.vibe.cart.scm.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nisum.vibe.cart.scm.model.OrderStatus;
import com.nisum.vibe.cart.scm.model.PaymentMethod;
import com.nisum.vibe.cart.scm.model.PaymentStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Embedded;
import javax.persistence.AttributeOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.CascadeType;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Entity class representing an order in the system.
 * <p>
 * This class maps to the "vibe_cart_orders" table in the database and contains comprehensive details about an order,
 * including its unique identifier, customer information, order date, total amount, quantities, discounts, estimated delivery date,
 * shipping and billing addresses, and status details such as order, payment, and method of payment.
 * </p>
 */
@Entity
@Table(name = "vibe_cart_orders")
public class Order {


    @Id
    @Column(name = "order_id", unique = true, updatable = false, nullable = false)
    private String orderId;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "name", column = @Column(name = "customer_name")), @AttributeOverride(name = "email", column = @Column(name = "customer_email")), @AttributeOverride(name = "phoneNumber", column = @Column(name = "customer_phone_number"))})
    private Customer customer;

    @Column(name = "order_date", nullable = false, updatable = false)
    private Instant orderDate;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> orderItems;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "sub_total", nullable = false)
    private double subTotal;

    @Column(name = "discount_price", nullable = false)
    private BigDecimal discountPrice;

    @Column(name = "offer_id", nullable = false)
    private Long offerId;

    @Column(name = "total_quantity", nullable = false)
    private int totalQuantity;

    @Column(name = "estimated_delivery_date")
    private LocalDateTime estimated_delivery_date;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "name", column = @Column(name = "shipping_name")), @AttributeOverride(name = "email", column = @Column(name = "shipping_email")), @AttributeOverride(name = "phoneNumber", column = @Column(name = "shipping_phoneNumber")), @AttributeOverride(name = "address", column = @Column(name = "shipping_address")), @AttributeOverride(name = "city", column = @Column(name = "shipping_city")), @AttributeOverride(name = "state", column = @Column(name = "shipping_state")), @AttributeOverride(name = "zipcode", column = @Column(name = "shipping_zipcode"))})
    private Address shippingAddress;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "name", column = @Column(name = "billing_name")), @AttributeOverride(name = "email", column = @Column(name = "billing_email")), @AttributeOverride(name = "phoneNumber", column = @Column(name = "billing_phoneNumber")), @AttributeOverride(name = "address", column = @Column(name = "billing_address")), @AttributeOverride(name = "city", column = @Column(name = "billing_city")), @AttributeOverride(name = "state", column = @Column(name = "billing_state")), @AttributeOverride(name = "zipcode", column = @Column(name = "billing_zipcode"))})
    private Address billingAddress;

    @Column(name = "shipping_zip_Code")
    private Long shippingzipCode;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    /**
     * Constructs a new {@code Order} with the specified values.
     *
     * @param orderId the unique identifier of the order.
     * @param customer the customer details.
     * @param orderDate the date when the order was placed.
     * @param createdDate the date when the order was created.
     * @param updatedDate the date when the order was last updated.
     * @param orderItems the list of order items.
     * @param totalAmount the total amount of the order.
     * @param subTotal the subtotal of the order.
     * @param discountPrice the discount applied to the order.
     * @param offerId the ID of the offer applied to the order.
     * @param totalQuantity the total quantity of items in the order.
     * @param estimated_delivery_date the estimated delivery date of the order.
     * @param shippingAddress the shipping address for the order.
     * @param billingAddress the billing address for the order.
     * @param shippingzipCode the shipping ZIP code.
     * @param orderStatus the status of the order.
     * @param paymentStatus the payment status of the order.
     * @param paymentMethod the method of payment used for the order.
     */
    public Order(String orderId, Customer customer, Instant orderDate, Instant createdDate, Instant updatedDate, List<OrderItem> orderItems, double totalAmount, double subTotal, BigDecimal discountPrice, Long offerId, int totalQuantity, LocalDateTime estimated_delivery_date, Address shippingAddress, Address billingAddress, Long shippingzipCode, OrderStatus orderStatus, PaymentStatus paymentStatus, PaymentMethod paymentMethod) {
        this.orderId = orderId;
        this.customer = customer;
        this.orderDate = orderDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.orderItems = orderItems;
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
     * Default constructor for {@code Order}.
     */
    public Order() {
    }

    // Getters and Setters

    /**
     * Returns the unique identifier of the order.
     *
     * @return the order ID.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the unique identifier of the order.
     *
     * @param orderId the order ID.
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Returns the customer details.
     *
     * @return the customer.
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer details.
     *
     * @param customer the customer.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the date when the order was placed.
     *
     * @return the order date.
     */
    public Instant getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the date when the order was placed.
     *
     * @param orderDate the order date.
     */
    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Returns the date when the order was created.
     *
     * @return the created date.
     */
    public Instant getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date when the order was created.
     *
     * @param createdDate the created date.
     */
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Returns the date when the order was last updated.
     *
     * @return the updated date.
     */
    public Instant getUpdatedDate() {
        return updatedDate;
    }

    /**
     * Sets the date when the order was last updated.
     *
     * @param updatedDate the updated date.
     */
    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * Returns the list of order items.
     *
     * @return the order items.
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Sets the list of order items.
     *
     * @param orderItems the order items.
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * Returns the total amount of the order.
     *
     * @return the total amount.
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the total amount of the order.
     *
     * @param totalAmount the total amount.
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * Returns the subtotal of the order.
     *
     * @return the subtotal.
     */
    public double getSubTotal() {
        return subTotal;
    }

    /**
     * Sets the subtotal of the order.
     *
     * @param subTotal the subtotal.
     */
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * Returns the discount applied to the order.
     *
     * @return the discount price.
     */
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    /**
     * Sets the discount applied to the order.
     *
     * @param discountPrice the discount price.
     */
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * Returns the ID of the offer applied to the order.
     *
     * @return the offer ID.
     */
    public Long getOfferId() {
        return offerId;
    }

    /**
     * Sets the ID of the offer applied to the order.
     *
     * @param offerId the offer ID.
     */
    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    /**
     * Returns the total quantity of items in the order.
     *
     * @return the total quantity.
     */
    public int getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Sets the total quantity of items in the order.
     *
     * @param totalQuantity the total quantity.
     */
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    /**
     * Returns the estimated delivery date of the order.
     *
     * @return the estimated delivery date.
     */
    public LocalDateTime getEstimated_delivery_date() {
        return estimated_delivery_date;
    }

    /**
     * Sets the estimated delivery date of the order.
     *
     * @param estimated_delivery_date the estimated delivery date.
     */
    public void setEstimated_delivery_date(LocalDateTime estimated_delivery_date) {
        this.estimated_delivery_date = estimated_delivery_date;
    }

    /**
     * Returns the shipping address for the order.
     *
     * @return the shipping address.
     */
    public Address getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sets the shipping address for the order.
     *
     * @param shippingAddress the shipping address.
     */
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    /**
     * Returns the billing address for the order.
     *
     * @return the billing address.
     */
    public Address getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the billing address for the order.
     *
     * @param billingAddress the billing address.
     */
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    /**
     * Returns the shipping ZIP code.
     *
     * @return the shipping ZIP code.
     */
    public Long getShippingzipCode() {
        return shippingzipCode;
    }

    /**
     * Sets the shipping ZIP code.
     *
     * @param shippingzipCode the shipping ZIP code.
     */
    public void setShippingzipCode(Long shippingzipCode) {
        this.shippingzipCode = shippingzipCode;
    }

    /**
     * Returns the status of the order.
     *
     * @return the order status.
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * Sets the status of the order.
     *
     * @param orderStatus the order status.
     */
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * Returns the payment status of the order.
     *
     * @return the payment status.
     */
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Sets the payment status of the order.
     *
     * @param paymentStatus the payment status.
     */
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Returns the method of payment used for the order.
     *
     * @return the payment method.
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Sets the method of payment used for the order.
     *
     * @param paymentMethod the payment method.
     */
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}