package com.nisum.vibe.cart.scm.repository;

import com.nisum.vibe.cart.scm.entity.Inventory;
import com.nisum.vibe.cart.scm.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on {@link Inventory} entities.
 * <p>
 * This interface extends {@link JpaRepository} and provides methods to query the {@link Inventory}
 * entity based on various criteria, including SKU, warehouse, and item ID. Custom queries are defined
 * using JPQL and native SQL.
 * </p>
 */
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findBySkuAndWarehouse(@Param("sku") Long sku, @Param("targetWarehouse") Warehouse targetWarehouse);

    List<Inventory> findBySku(Long sku);

    @Query(
            value = "SELECT * FROM vibe_cart_inventory WHERE sku = :sku AND quantity_available > 0 AND " +
                    "(:warehouseId IS NULL OR warehouse_id != :warehouseId) ORDER BY quantity_available DESC",
            nativeQuery = true)
    List<Inventory> findBySkuAndAvailableQuantityGreaterThanZero(@Param("sku") Long sku, @Param("warehouseId") String warehouseId);

    List<Inventory> findByItemId(Long itemId);

    @Query(value = "SELECT * FROM vibe_cart_inventory WHERE sku = :sku AND quantity_on_hold > 0", nativeQuery = true)
    List<Inventory> findBySkuAndQuantityOnHoldGreaterThanZero(@Param("sku") Long sku);

    @Query(
            value = "SELECT * FROM vibe_cart_inventory WHERE warehouse_id = :warehouseId",
            nativeQuery = true)
    List<Inventory> findByWarehouseId(@Param("warehouseId") String warehouseId);

    @Query(
            value = "SELECT * FROM vibe_cart_inventory WHERE sku = :sku AND warehouse_id = :warehouseId",
            nativeQuery = true)
    Inventory findBySkuAndWarehouseId(Long sku, String warehouseId);
}
