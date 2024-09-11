package com.nisum.vibe.cart.scm.model;

/**
 * Enumeration representing the possible statuses of an order.
 * <p>
 * Defines various stages an order can be in, from confirmation to delivery, including intermediate statuses such as
 * being with the courier, on the way, and out for delivery. It also includes statuses for cancellation and completion.
 * </p>
 */
public enum OrderStatus {
    SHIPPED,
    CONFIRMED,        // Order has been confirmed
    DISPATCHED,       // Order has been dispatched
    PICKUP_COURIER,   // Order is with the courier for pickup
    ON_THE_WAY,       // Order is on its way
    OUT_FOR_DELIVERY, // Order is out for delivery
    DELIVERED,        // Order has been delivered
    CANCELLED,          // Order has been canceled
    COMPLETED         // Order is Completed
}
