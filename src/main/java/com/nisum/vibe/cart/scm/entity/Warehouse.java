package com.nisum.vibe.cart.scm.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * Entity class representing a warehouse in the system.
 * <p>
 * Maps to the "vibe_cart_warehouse" table and includes details such as the warehouse ID, name, location,
 * and the range of zip codes it covers.
 * </p>
 */
@Entity
@Table(name = "vibe_cart_warehouse")
public class Warehouse {

    @Id
    @Column(name = "warehouse_id")
    private String warehouseId;

    @NotNull
    @Size(max = 100)
    @Column(name = "warehouse_name", nullable = false, length = 100)
    private String warehouseName;

    @NotNull
    @Size(max = 255)
    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "zipcode_start", nullable = false)
    @Min(value = 100000, message = "Zipcode should not be less than 100000")
    @Max(value = 999999, message = "Zipcode should not be more than 999999")
    private Long zipcodeStart;

    @Column(name = "zipcode_end", nullable = false)
    @Min(value = 100000, message = "Zipcode should not be less than 100000")
    @Max(value = 999999, message = "Zipcode should not be more than 999999")
    private Long zipcodeEnd;

    /**
     * Constructs a {@code Warehouse} with the specified details.
     *
     * @param warehouseId   the unique identifier for the warehouse
     * @param warehouseName the name of the warehouse
     * @param location      the location of the warehouse
     * @param zipcodeStart  the starting ZIP code covered by the warehouse
     * @param zipcodeEnd    the ending ZIP code covered by the warehouse
     */
    public Warehouse(String warehouseId, String warehouseName, String location, Long zipcodeStart, Long zipcodeEnd) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.location = location;
        this.zipcodeStart = zipcodeStart;
        this.zipcodeEnd = zipcodeEnd;
    }

    /**
     * Default constructor for {@code Warehouse}.
     */
    public Warehouse() {
    }

    /**
     * Gets the unique identifier for the warehouse.
     *
     * @return the unique identifier for the warehouse
     */
    public String getWarehouseId() {
        return warehouseId;
    }

    /**
     * Sets the unique identifier for the warehouse.
     *
     * @param warehouseId the unique identifier for the warehouse
     */
    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    /**
     * Gets the name of the warehouse.
     *
     * @return the name of the warehouse
     */
    public @NotNull @Size(max = 100) String getWarehouseName() {
        return warehouseName;
    }

    /**
     * Sets the name of the warehouse.
     *
     * @param warehouseName the name of the warehouse
     */
    public void setWarehouseName(@NotNull @Size(max = 100) String warehouseName) {
        this.warehouseName = warehouseName;
    }

    /**
     * Gets the location of the warehouse.
     *
     * @return the location of the warehouse
     */
    public @NotNull @Size(max = 255) String getLocation() {
        return location;
    }

    /**
     * Sets the location of the warehouse.
     *
     * @param location the location of the warehouse
     */
    public void setLocation(@NotNull @Size(max = 255) String location) {
        this.location = location;
    }

    /**
     * Gets the starting ZIP code covered by the warehouse.
     *
     * @return the starting ZIP code covered by the warehouse
     */
    public @Min(value = 100000, message = "Zipcode should not be less than 100000") @Max(value = 999999, message = "Zipcode should not be more than 999999") Long getZipcodeStart() {
        return zipcodeStart;
    }

    /**
     * Sets the starting ZIP code covered by the warehouse.
     *
     * @param zipcodeStart the starting ZIP code covered by the warehouse
     */
    public void setZipcodeStart(@Min(value = 100000, message = "Zipcode should not be less than 100000") @Max(value = 999999, message = "Zipcode should not be more than 999999") Long zipcodeStart) {
        this.zipcodeStart = zipcodeStart;
    }

    /**
     * Gets the ending ZIP code covered by the warehouse.
     *
     * @return the ending ZIP code covered by the warehouse
     */
    public @Min(value = 100000, message = "Zipcode should not be less than 100000") @Max(value = 999999, message = "Zipcode should not be more than 999999") Long getZipcodeEnd() {
        return zipcodeEnd;
    }

    /**
     * Sets the ending ZIP code covered by the warehouse.
     *
     * @param zipcodeEnd the ending ZIP code covered by the warehouse
     */
    public void setZipcodeEnd(@Min(value = 100000, message = "Zipcode should not be less than 100000") @Max(value = 999999, message = "Zipcode should not be more than 999999") Long zipcodeEnd) {
        this.zipcodeEnd = zipcodeEnd;
    }
}
