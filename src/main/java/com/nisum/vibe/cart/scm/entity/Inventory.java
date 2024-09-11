package com.nisum.vibe.cart.scm.entity;

import org.hibernate.annotations.Check;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Represents the inventory details for a product.
 * <p>
 * This entity contains information such as the inventory ID, item ID, SKU, available quantity,
 * warehouse, quantity on hold, quantity on order, and the last updated date.
 * </p>
 * <p>
 * The table is constrained to ensure unique combinations of SKU and warehouse ID,
 * and that the available quantity is always greater than or equal to zero.
 * </p>
 */
@Entity
@Table(name = "vibe_cart_inventory",
        uniqueConstraints = @UniqueConstraint(columnNames = {"sku", "warehouse_id"}))
@Check(constraints = "quantity_available >= 0")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long inventoryId;

    @NotNull
    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @NotNull
    @Digits(integer = 10, fraction = 0)
    @Column(name = "sku", nullable = false, length = 20)
    private Long sku;

    @NotNull
    @Min(value = 0)
    @Column(name = "quantity_available", nullable = false)
    private Integer quantityAvailable;

    @NotNull
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", referencedColumnName = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "quantity_on_hold")
    private Integer quantityOnHold;

    @Column(name = "quantity_on_order")
    private Integer quantityOnOrder;

    @Column(name = "last_updated_date")
    private LocalDate lastUpdatedDate;

    /**
     * Constructs a new {@code Inventory} instance with no specified details.
     * Default constructor required for JPA.
     */
    public Inventory() {
    }

    /**
     * Constructs a new {@code Inventory} instance with the specified details.
     *
     * @param inventoryId       the unique identifier of the inventory record.
     * @param itemId            the item ID associated with the inventory.
     * @param sku               the SKU of the product.
     * @param quantityAvailable the available quantity of the product.
     * @param warehouse         the warehouse where the inventory is stored.
     * @param quantityOnHold    the quantity of the product on hold.
     * @param quantityOnOrder   the quantity of the product on order.
     * @param lastUpdatedDate   the date when the inventory was last updated.
     */
    public Inventory(Long inventoryId, Long itemId, Long sku, Integer quantityAvailable, Warehouse warehouse, Integer quantityOnHold, Integer quantityOnOrder, LocalDate lastUpdatedDate) {
        this.inventoryId = inventoryId;
        this.itemId = itemId;
        this.sku = sku;
        this.quantityAvailable = quantityAvailable;
        this.warehouse = warehouse;
        this.quantityOnHold = quantityOnHold;
        this.quantityOnOrder = quantityOnOrder;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * Constructs a new {@code Inventory} instance without specifying an inventory ID.
     *
     * @param itemId            the item ID associated with the inventory.
     * @param sku               the SKU of the product.
     * @param quantityAvailable the available quantity of the product.
     * @param warehouse         the warehouse where the inventory is stored.
     * @param quantityOnHold    the quantity of the product on hold.
     * @param quantityOnOrder   the quantity of the product on order.
     * @param lastUpdatedDate   the date when the inventory was last updated.
     */
    public Inventory(Long itemId, Long sku, Integer quantityAvailable, Warehouse warehouse, Integer quantityOnHold, Integer quantityOnOrder, LocalDate lastUpdatedDate) {
        this.itemId = itemId;
        this.sku = sku;
        this.quantityAvailable = quantityAvailable;
        this.warehouse = warehouse;
        this.quantityOnHold = quantityOnHold;
        this.quantityOnOrder = quantityOnOrder;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * Returns the unique identifier of the inventory record.
     *
     * @return the inventory ID.
     */
    public Long getInventoryId() {
        return inventoryId;
    }

    /**
     * Sets the unique identifier of the inventory record.
     *
     * @param inventoryId the inventory ID to set.
     */
    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
     * Returns the item ID associated with the inventory.
     *
     * @return the item ID.
     */
    public @NotNull Long getItemId() {
        return itemId;
    }

    /**
     * Sets the item ID associated with the inventory.
     *
     * @param itemId the item ID to set.
     */
    public void setItemId(@NotNull Long itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the SKU of the product.
     *
     * @return the SKU.
     */
    public @NotNull @Digits(integer = 10, fraction = 0) Long getSku() {
        return sku;
    }

    /**
     * Sets the SKU of the product.
     *
     * @param sku the SKU to set.
     */
    public void setSku(@NotNull @Digits(integer = 10, fraction = 0) Long sku) {
        this.sku = sku;
    }

    /**
     * Returns the available quantity of the product.
     *
     * @return the quantity available.
     */
    public @NotNull @Min(value = 0) Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    /**
     * Sets the available quantity of the product.
     *
     * @param quantityAvailable the quantity available to set.
     */
    public void setQuantityAvailable(@NotNull @Min(value = 0) Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    /**
     * Returns the warehouse where the inventory is stored.
     *
     * @return the warehouse.
     */
    public @NotNull Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Sets the warehouse where the inventory is stored.
     *
     * @param warehouse the warehouse to set.
     */
    public void setWarehouse(@NotNull Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * Returns the quantity of the product on hold.
     *
     * @return the quantity on hold.
     */
    public Integer getQuantityOnHold() {
        return quantityOnHold;
    }

    /**
     * Sets the quantity of the product on hold.
     *
     * @param quantityOnHold the quantity on hold to set.
     */
    public void setQuantityOnHold(Integer quantityOnHold) {
        this.quantityOnHold = quantityOnHold;
    }

    /**
     * Returns the quantity of the product on order.
     *
     * @return the quantity on order.
     */
    public Integer getQuantityOnOrder() {
        return quantityOnOrder;
    }

    /**
     * Sets the quantity of the product on order.
     *
     * @param quantityOnOrder the quantity on order to set.
     */
    public void setQuantityOnOrder(Integer quantityOnOrder) {
        this.quantityOnOrder = quantityOnOrder;
    }

    /**
     * Returns the date when the inventory was last updated.
     *
     * @return the last updated date.
     */
    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    /**
     * Sets the date when the inventory was last updated.
     *
     * @param lastUpdatedDate the last updated date to set.
     */
    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
