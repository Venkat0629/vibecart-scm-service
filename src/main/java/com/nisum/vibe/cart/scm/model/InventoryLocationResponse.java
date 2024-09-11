package com.nisum.vibe.cart.scm.model;

/**
 * Data Transfer Object (DTO) for representing inventory details at a specific warehouse location.
 * <p>
 * Contains fields for warehouse ID, SKU ID, available quantity, reserved quantity, and total quantity.
 * </p>
 */
public class InventoryLocationResponse {

    private String warehouseId;
    private Long skuId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer totalQuantity;

    /**
     * Default constructor for {@code InventoryLocationResponse}.
     */
    public InventoryLocationResponse() {
    }

    /**
     * Constructs a new {@code InventoryLocationResponse} with the specified values.
     *
     * @param warehouseId the identifier of the warehouse where the inventory is located.
     * @param skuId the stock keeping unit identifier.
     * @param availableQuantity the quantity of the item currently available in the warehouse.
     * @param reservedQuantity the quantity of the item that is reserved in the warehouse.
     * @param totalQuantity the total quantity of the item in the warehouse.
     */
    public InventoryLocationResponse(String warehouseId, Long skuId, Integer availableQuantity, Integer reservedQuantity, Integer totalQuantity) {
        this.warehouseId = warehouseId;
        this.skuId = skuId;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
        this.totalQuantity = totalQuantity;
    }

    /**
     * Returns the identifier of the warehouse where the inventory is located.
     *
     * @return the warehouse ID.
     */
    public String getWarehouseId() {
        return warehouseId;
    }

    /**
     * Sets the identifier of the warehouse where the inventory is located.
     *
     * @param warehouseId the warehouse ID.
     */
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
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
     * Returns the quantity of the item currently available in the warehouse.
     *
     * @return the available quantity.
     */
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * Sets the quantity of the item currently available in the warehouse.
     *
     * @param availableQuantity the available quantity.
     */
    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    /**
     * Returns the quantity of the item that is reserved in the warehouse.
     *
     * @return the reserved quantity.
     */
    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    /**
     * Sets the quantity of the item that is reserved in the warehouse.
     *
     * @param reservedQuantity the reserved quantity.
     */
    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    /**
     * Returns the total quantity of the item in the warehouse.
     *
     * @return the total quantity.
     */
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Sets the total quantity of the item in the warehouse.
     *
     * @param totalQuantity the total quantity.
     */
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
