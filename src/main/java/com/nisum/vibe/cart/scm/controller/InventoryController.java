package com.nisum.vibe.cart.scm.controller;


import com.nisum.vibe.cart.scm.ResponseEntity.ApiResponse;
import com.nisum.vibe.cart.scm.exception.InventoryNotFoundException;
import com.nisum.vibe.cart.scm.exception.WarehouseNotFoundException;
import com.nisum.vibe.cart.scm.model.*;
import com.nisum.vibe.cart.scm.service.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing inventory in the VibeCart application.
 * Provides endpoints for checking, updating, and retrieving inventory details.
 */
@RestController
@RequestMapping("/vibe-cart/scm/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    private final Logger LOGGER = LoggerFactory.getLogger(InventoryController.class);

    /**
     * Handles a request to provide a welcome message.
     *
     * @return a welcome message for the Inventory Service.
     */
    @GetMapping("/welcome")
    public ResponseEntity<String> welcomeMessage(){
        return ResponseEntity.ok("Welcome to Inventory Service of VibeCart");
    }

    /**
     * Handles a request to check the quantity of SKUs in the inventory.
     *
     * @param skuList the list of SKUs to check.
     * @return the list of available quantities for the provided SKUs.
     */
    @PostMapping("/check-quantity")
    public ResponseEntity<ApiResponse<List<Integer>>> checkSkuQuantity(@RequestBody List<Long> skuList) {
        LOGGER.info("Inside checkSkuQuantity() method of InventoryController class");
        List<Integer> quantityList = inventoryService.checkSkuQuantity(skuList);
        ApiResponse<List<Integer>> response = new ApiResponse<>(true, HttpStatus.OK.value(), "SKUs quantities retrieved successfully", quantityList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles a request to calculate the expected delivery date based on SKU and customer zipcode.
     *
     * @param sku the SKU for which the delivery date is to be calculated.
     * @param zipcode the zipcode of the customer.
     * @return the expected delivery date as a string.
     * @throws InventoryNotFoundException if the inventory is not found.
     * @throws WarehouseNotFoundException if the warehouse is not found.
     */
    @GetMapping("/expected-delivery-date")
    public ResponseEntity<ApiResponse<String>> getExpectedDeliveryDateWithSkuAndZipcode(@RequestParam("sku") Long sku,
                                                                                        @RequestParam("zipcode") Long zipcode)
            throws InventoryNotFoundException, WarehouseNotFoundException {
        LOGGER.info("Inside getExpectedDeliveryDateWithSkuAndZipcode() method of InventoryController class");
        String expectedDate = inventoryService.getExpectedDeliveryDateWithSkuAndZipcode(sku, zipcode);
        ApiResponse<String> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Expected delivery date retrieved successfully", expectedDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the inventory report of all warehouses.
     * Calls the service layer to fetch the report and returns it in the response.
     *
     * @return ApiResponse containing the inventory report.
     */
    @GetMapping("/inventory-report")
    public ResponseEntity<ApiResponse<List<WarehouseStockDto>>> displayInventoryReport() {
        LOGGER.info("Inside displayInventoryReport() method of InventoryController class");
        List<WarehouseStockDto> warehouseStockDtoList = inventoryService.displayInventoryReport();
        ApiResponse<List<WarehouseStockDto>> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Inventory report retrieved successfully", warehouseStockDtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles a request to add stock to a single inventory entry.
     *
     * @param skuQuantityWarehouseDto the DTO containing SKU, quantity, and warehouse details.
     * @return a confirmation message indicating that the stock was updated.
     * @throws InventoryNotFoundException if the inventory is not found.
     * @throws WarehouseNotFoundException if the warehouse is not found.
     */
    @PutMapping("/update-single-inventory")
    public ResponseEntity<ApiResponse<String>> addStockToSingleInventory(@RequestBody SkuQuantityWarehouseDto skuQuantityWarehouseDto)
            throws InventoryNotFoundException, WarehouseNotFoundException {
        LOGGER.info("Inside addStockToSingleInventory() method of InventoryController class");
        inventoryService.addStockToSingleInventory(skuQuantityWarehouseDto);
        ApiResponse<String> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Stock updated in the inventory successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles a request to add stock to multiple inventory entries.
     *
     * @param skuQuantityWarehouseDtos the list of DTOs containing SKU, quantity, and warehouse details.
     * @return a confirmation message indicating that the stock was updated in all inventories.
     * @throws InventoryNotFoundException if the inventory is not found.
     * @throws WarehouseNotFoundException if the warehouse is not found.
     */
    @PutMapping("/update-multiple-inventories")
    public ResponseEntity<ApiResponse<String>> addStockToMultipleInventories(@RequestBody List<SkuQuantityWarehouseDto> skuQuantityWarehouseDtos)
            throws InventoryNotFoundException, WarehouseNotFoundException {
        LOGGER.info("Inside addStockToMultipleInventories() method of InventoryController class");
        inventoryService.addStockToMultipleInventories(skuQuantityWarehouseDtos);
        ApiResponse<String> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Stock updated in all inventories successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves all inventory details.
     * Fetches all inventory data and returns it in the response.
     *
     * @return ApiResponse containing all inventory details.
     */
    @GetMapping("/get-all-inventories")
    public ResponseEntity<ApiResponse<List<InventoryConsoleResponse>>> getAllInventories() {
        LOGGER.info("Inside getAllInventories() method of InventoryController class");
        List<InventoryConsoleResponse> consoleResponses = inventoryService.getAllInventories();
        ApiResponse<List<InventoryConsoleResponse>> response = new ApiResponse<>(true, HttpStatus.OK.value(), "All inventory details retrieved successfully", consoleResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves details of all warehouses.
     * Calls the service to get the warehouse data and returns it.
     *
     * @return ApiResponse containing warehouse details.
     */
    @GetMapping("/get-all-warehouses")
    public ResponseEntity<ApiResponse<List<InventoryLocationResponse>>> getAllWarehouses() {
        LOGGER.info("Inside getAllWarehouses() method of InventoryController class");
        List<InventoryLocationResponse> responseList = inventoryService.getAllWarehouses();
        ApiResponse<List<InventoryLocationResponse>> response = new ApiResponse<>(true, HttpStatus.OK.value(), "All warehouse details retrieved successfully", responseList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles a request to get total quantity based on item id.
     *
     * @param itemId the item id for which quantity is retrieved
     * @return total quantity of the item present in inventory
     */
    @GetMapping("/quantity-by-itemId")
    public ResponseEntity<ApiResponse<Integer>> getQuantityByItemId(@RequestParam("itemId") Long itemId) throws InventoryNotFoundException {
        LOGGER.info("Inside getQuantityByItemId() method of InventoryController class");
        Integer quantity = inventoryService.getQuantityByItemId(itemId);
        ApiResponse<Integer> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Quantity by Item ID retrieved successfully", quantity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles a request to get total quantity based on SKU.
     *
     * @param sku the SKU for which quantity is retrieved
     * @return total quantity of the SKU present in inventory
     */
    @GetMapping("/quantity-by-sku")
    public ResponseEntity<ApiResponse<Integer>> getQuantityBySku(@RequestParam("sku") Long sku) throws InventoryNotFoundException {
        LOGGER.info("Inside getQuantityBySku() method of InventoryController class");
        Integer quantity = inventoryService.getQuantityBySku(sku);
        ApiResponse<Integer> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Quantity by SKU ID retrieved successfully", quantity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
