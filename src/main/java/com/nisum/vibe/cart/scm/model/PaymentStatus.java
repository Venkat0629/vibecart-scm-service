package com.nisum.vibe.cart.scm.model;

/**
 * Enumeration representing the possible statuses of a payment for an order.
 * <p>
 * This enum indicates the state of a payment in the order lifecycle:
 * <ul>
 *     <li><b>PENDING:</b> Payment has been initiated but not yet completed or processed.</li>
 *     <li><b>COMPLETED:</b> Payment has been successfully completed and processed.</li>
 *     <li><b>FAILED:</b> Payment has failed due to an error or issue during processing.</li>
 *     <li><b>CANCELLED:</b> Payment has been canceled and will not be processed.</li>
 * </ul>
 * </p>
 */
public enum PaymentStatus {
    PENDING,            // Payment has been initiated but not yet completed or processed.
    COMPLETED,          // Payment has been successfully completed and processed.
    FAILED,             // Payment has failed due to an error or issue during processing.
    CANCELLED           // Payment has been canceled and will not be processed.
}
