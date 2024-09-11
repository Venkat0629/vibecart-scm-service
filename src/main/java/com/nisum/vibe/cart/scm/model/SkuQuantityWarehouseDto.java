package com.nisum.vibe.cart.scm.model;

/**
 * Data Transfer Object (DTO) for representing the quantity of a SKU (Stock Keeping Unit)
 * to be added to a specific warehouse.
 * <p>
 * This class contains the necessary information to update the inventory for a given SKU
 * in a particular warehouse:
 * <ul>
 *     <li><b>sku:</b> The unique identifier of the SKU (Stock Keeping Unit).</li>
 *     <li><b>quantityToAdd:</b> The quantity of the SKU to be added to the warehouse inventory.</li>
 *     <li><b>warehouseId:</b> The unique identifier of the warehouse where the SKU quantity is to be updated.</li>
 * </ul>
 * </p>
 */
public class SkuQuantityWarehouseDto {

    private Long sku;
    private Integer quantityToAdd;
    private String warehouseId;

    /**
     * Constructs an SkuQuantityWarehouseDto with the specified parameters.
     *
     * @param sku the unique identifier of the SKU (Stock Keeping Unit)
     * @param quantityToAdd the quantity of the SKU to be added to the warehouse inventory
     * @param warehouseId the unique identifier of the warehouse where the SKU quantity is to be updated
     */
    public SkuQuantityWarehouseDto(Long sku, Integer quantityToAdd, String warehouseId) {
        this.sku = sku;
        this.quantityToAdd = quantityToAdd;
        this.warehouseId = warehouseId;
    }

    /**
     * Default constructor.
     */
    public SkuQuantityWarehouseDto() {
    }

    /**
     * Returns the unique identifier of the SKU (Stock Keeping Unit).
     *
     * @return the SKU ID
     */
    public Long getSku() {
        return sku;
    }

    /**
     * Sets the unique identifier of the SKU (Stock Keeping Unit).
     *
     * @param sku the SKU ID
     */
    public void setSku(Long sku) {
        this.sku = sku;
    }

    /**
     * Returns the quantity of the SKU to be added to the warehouse inventory.
     *
     * @return the quantity to add
     */
    public Integer getQuantityToAdd() {
        return quantityToAdd;
    }

    /**
     * Sets the quantity of the SKU to be added to the warehouse inventory.
     *
     * @param quantityToAdd the quantity to add
     */
    public void setQuantityToAdd(Integer quantityToAdd) {
        this.quantityToAdd = quantityToAdd;
    }

    /**
     * Returns the unique identifier of the warehouse where the SKU quantity is to be updated.
     *
     * @return the warehouse ID
     */
    public String getWarehouseId() {
        return warehouseId;
    }

    /**
     * Sets the unique identifier of the warehouse where the SKU quantity is to be updated.
     *
     * @param warehouseId the warehouse ID
     */
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }
}
