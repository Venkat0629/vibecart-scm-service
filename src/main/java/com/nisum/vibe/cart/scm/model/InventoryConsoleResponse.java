package com.nisum.vibe.cart.scm.model;

/**
 * Data Transfer Object (DTO) for representing inventory details in the console.
 * <p>
 * Contains fields for SKU ID, available quantity, reserved quantity, and total quantity.
 * </p>
 */
public class InventoryConsoleResponse {

    private Long skuId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer totalQuantity;

    /**
     * Default constructor for {@code InventoryConsoleResponse}.
     */
    public InventoryConsoleResponse() {
    }

    /**
     * Constructs a new {@code InventoryConsoleResponse} with the specified values.
     *
     * @param skuId the stock keeping unit identifier.
     * @param availableQuantity the quantity of the item currently available.
     * @param reservedQuantity the quantity of the item that is reserved.
     * @param totalQuantity the total quantity of the item.
     */
    public InventoryConsoleResponse(Long skuId, Integer availableQuantity, Integer reservedQuantity, Integer totalQuantity) {
        this.skuId = skuId;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
        this.totalQuantity = totalQuantity;
    }

    /**
     * Returns the stock keeping unit (SKU) identifier.
     *
     * @return the SKU ID.
     */
    public Long getSkuId() {
        return skuId;
    }

    /**
     * Sets the stock keeping unit (SKU) identifier.
     *
     * @param skuId the SKU ID.
     */
    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    /**
     * Returns the quantity of the item currently available.
     *
     * @return the available quantity.
     */
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * Sets the quantity of the item currently available.
     *
     * @param availableQuantity the available quantity.
     */
    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    /**
     * Returns the quantity of the item that is reserved.
     *
     * @return the reserved quantity.
     */
    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    /**
     * Sets the quantity of the item that is reserved.
     *
     * @param reservedQuantity the reserved quantity.
     */
    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    /**
     * Returns the total quantity of the item.
     *
     * @return the total quantity.
     */
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Sets the total quantity of the item.
     *
     * @param totalQuantity the total quantity.
     */
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
