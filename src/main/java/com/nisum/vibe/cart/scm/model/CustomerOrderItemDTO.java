package com.nisum.vibe.cart.scm.model;

/**
 * Data Transfer Object (DTO) for representing an item in a customer's order.
 * <p>
 * Contains fields for SKU (stock keeping unit) and the quantity ordered.
 * </p>
 */
public class CustomerOrderItemDTO {

    private Long sku;
    private Integer orderQuantity;

    /**
     * Default constructor for {@code CustomerOrderItemDto}.
     */
    public CustomerOrderItemDTO() {
    }

    /**
     * Constructs a new {@code CustomerOrderItemDto} with the specified values.
     *
     * @param sku the stock keeping unit of the item.
     * @param orderQuantity the quantity of the item ordered.
     */
    public CustomerOrderItemDTO(Long sku, Integer orderQuantity) {
        this.sku = sku;
        this.orderQuantity = orderQuantity;
    }

    /**
     * Returns the stock keeping unit (SKU) of the item.
     *
     * @return the SKU.
     */
    public Long getSku() {
        return sku;
    }

    /**
     * Sets the stock keeping unit (SKU) of the item.
     *
     * @param sku the SKU.
     */
    public void setSku(Long sku) {
        this.sku = sku;
    }

    /**
     * Returns the quantity of the item ordered.
     *
     * @return the order quantity.
     */
    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    /**
     * Sets the quantity of the item ordered.
     *
     * @param orderQuantity the order quantity.
     */
    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }
}
