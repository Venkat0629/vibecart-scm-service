package com.nisum.vibe.cart.scm.model;

/**
 * Data Transfer Object (DTO) for representing the stock details of a warehouse.
 * <p>
 * This class encapsulates information about the stock levels in a specific warehouse, including:
 * <ul>
 *     <li>The ID of the warehouse.</li>
 *     <li>The quantity of items available for sale.</li>
 *     <li>The quantity of items reserved for orders.</li>
 *     <li>The total quantity of items (available + reserved).</li>
 * </ul>
 * </p>
 */
public class WarehouseStockDto {

    private String warehouseId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer totalQuantity;  //available + reserved

    /**
     * Default constructor for creating an empty instance of {@code WarehouseStockDto}.
     */
    public WarehouseStockDto() {
    }

    /**
     * Constructs a new {@code WarehouseStockDto} with the specified details.
     *
     * @param warehouseId The ID of the warehouse.
     * @param availableQuantity The quantity of items available for sale.
     * @param reservedQuantity The quantity of items reserved for orders.
     * @param totalQuantity The total quantity of items (available + reserved).
     */
    public WarehouseStockDto(String warehouseId, Integer availableQuantity, Integer reservedQuantity, Integer totalQuantity) {
        this.warehouseId = warehouseId;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
        this.totalQuantity = totalQuantity;
    }

    /**
     * Returns the ID of the warehouse.
     *
     * @return The ID of the warehouse.
     */
    public String getWarehouseId() {
        return warehouseId;
    }

    /**
     * Sets the ID of the warehouse.
     *
     * @param warehouseId The ID of the warehouse.
     */
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * Returns the quantity of items available for sale.
     *
     * @return The quantity of items available for sale.
     */
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    /**
     * Sets the quantity of items available for sale.
     *
     * @param availableQuantity The quantity of items available for sale.
     */
    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    /**
     * Returns the quantity of items reserved for orders.
     *
     * @return The quantity of items reserved for orders.
     */
    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    /**
     * Sets the quantity of items reserved for orders.
     *
     * @param reservedQuantity The quantity of items reserved for orders.
     */
    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    /**
     * Returns the total quantity of items (available + reserved).
     *
     * @return The total quantity of items.
     */
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Sets the total quantity of items (available + reserved).
     *
     * @param totalQuantity The total quantity of items.
     */
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}