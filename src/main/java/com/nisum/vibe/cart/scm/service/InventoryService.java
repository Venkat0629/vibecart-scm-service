package com.nisum.vibe.cart.scm.service;

import com.nisum.vibe.cart.scm.exception.InventoryNotFoundException;
import com.nisum.vibe.cart.scm.exception.OutOfStockException;
import com.nisum.vibe.cart.scm.exception.WarehouseNotFoundException;
import com.nisum.vibe.cart.scm.model.*;

import java.util.List;
import java.util.Map;

/**
 * Service interface for handling inventory operations in the VibeCart application.
 * Provides methods for checking SKU quantities, updating inventory, calculating
 * expected delivery dates, and managing stock in multiple warehouses.
 *
 * <p>
 * Implementations of this interface should handle the core business logic
 * related to inventory management.
 */
public interface InventoryService {
    List<Integer> checkSkuQuantity(List<Long> skuList);

    Map<Long, String> stockReservationCall(List<CustomerOrderItemDto> customerOrderItemDtos, Long customerZipcode)
            throws WarehouseNotFoundException, InventoryNotFoundException;

    String getExpectedDeliveryDateWithSkuAndZipcode(Long sku, Long zipcode)
            throws InventoryNotFoundException, WarehouseNotFoundException;

    List<WarehouseStockDto> displayInventoryReport();

    void addStockToSingleInventory(SkuQuantityWarehouseDto skuQuantityWarehouseDto) throws InventoryNotFoundException, WarehouseNotFoundException;

    void addStockToMultipleInventories(List<SkuQuantityWarehouseDto> skuQuantityWarehouseDtos) throws InventoryNotFoundException, WarehouseNotFoundException;

    List<InventoryConsoleResponse> getAllInventories();

    Integer getQuantityByItemId(Long itemId) throws InventoryNotFoundException;

    Integer getQuantityBySku(Long sku) throws InventoryNotFoundException;

    void confirmStockReservation(List<Long> skuList) throws InventoryNotFoundException;

    List<InventoryLocationResponse> getAllWarehouses();

    void revertStockIfOrderCancel(List<CustomerOrderItemDto> customerOrderItemDtos, Long customerZipcode) throws WarehouseNotFoundException, InventoryNotFoundException;
}
