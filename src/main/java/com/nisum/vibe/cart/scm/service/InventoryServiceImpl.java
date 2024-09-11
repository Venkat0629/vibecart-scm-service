package com.nisum.vibe.cart.scm.service;

import com.nisum.vibe.cart.scm.entity.Inventory;
import com.nisum.vibe.cart.scm.entity.Warehouse;
import com.nisum.vibe.cart.scm.exception.InventoryNotFoundException;
import com.nisum.vibe.cart.scm.exception.WarehouseNotFoundException;
import com.nisum.vibe.cart.scm.model.*;
import com.nisum.vibe.cart.scm.repository.InventoryRepository;
import com.nisum.vibe.cart.scm.repository.WarehouseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the InventoryService interface that provides methods to manage and retrieve inventory information.
 * This class handles various operations such as checking SKU quantities, updating inventory based on orders,
 * retrieving expected delivery dates, and managing stock levels across multiple warehouses.
 *
 * <p>
 * The service interacts with the InventoryRepository and WarehouseRepository to perform database operations.
 * It also communicates with external services to fetch product, SKU, and category details.
 * </p>
 *
 * <p>
 * This class uses {@code @Transactional} annotations to ensure that database operations are executed
 * within a transactional context where necessary.
 * </p>
 *
 * <p>
 * Logging is incorporated at various levels to provide insight into method execution.
 * </p>
 */
@Service
public class InventoryServiceImpl implements InventoryService{

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceImpl.class);

    /**
     * Checks the total available quantity for a list of SKUs.
     *
     * <p>
     * This method iterates over the provided list of SKUs and retrieves the corresponding inventory records.
     * The total quantity available for each SKU is calculated and returned in a list.
     * </p>
     *
     * @param skuList a list of SKU identifiers to check.
     * @return a list of integers representing the total available quantity for each SKU in the same order as the input list.
     */
    @Override
    public List<Integer> checkSkuQuantity(List<Long> skuList) {

        LOGGER.info("Inside checkSkuQuantity() method of InventoryServiceImpl class");

        List<Integer> totalQuantityList = new ArrayList<>();

        for(Long sku: skuList){
            List<Inventory> inventoryList = inventoryRepository.findBySku(sku);
            Integer totalQuantity = inventoryList.stream().mapToInt(Inventory::getQuantityAvailable).sum();
            totalQuantityList.add(totalQuantity);
        }

        return totalQuantityList;
    }

    /**
     * Updates the inventory based on the quantity of items ordered by a customer, considering the proximity of
     * warehouses to the customer's location.
     *
     * <p>
     * The method processes a list of ordered items and attempts to update the inventory for the nearest warehouse
     * to the customer's zipcode. If the SKU is not found in the nearest warehouse, it searches other warehouses.
     * If the available quantity in the nearest warehouse is insufficient, it distributes the remaining quantity
     * across other warehouses.
     * </p>
     *
     * @param customerOrderItemDtos a list of ordered items including SKU and quantity.
     * @param customerZipcode the customer's delivery zipcode used to find the nearest warehouse.
     * @throws WarehouseNotFoundException if no warehouse is found for the given zipcode.
     * @throws InventoryNotFoundException if no inventory is found for the SKU in any warehouse.
     */
    @Override
    @Transactional
    public Map<Long, String> stockReservationCall(List<CustomerOrderItemDto> customerOrderItemDtos, Long customerZipcode)
            throws WarehouseNotFoundException, InventoryNotFoundException {

        LOGGER.info("Inside updateInventory() method of InventoryServiceImpl class");

        Map<Long, String> responseMap = new HashMap<>();

        for (CustomerOrderItemDto customerOrderItemDto : customerOrderItemDtos) {
            Long sku = customerOrderItemDto.getSku();
            Integer orderQuantity = customerOrderItemDto.getOrderQuantity();

            Optional<Warehouse> nearestWarehouseOptional = warehouseRepository.findWarehouseByZipcode(customerZipcode);

            if (nearestWarehouseOptional.isPresent()) {
                Warehouse nearestWarehouse = nearestWarehouseOptional.get();
                Inventory nearestInventory = inventoryRepository.findBySkuAndWarehouse(sku, nearestWarehouse).orElse(null);

                if (nearestInventory == null) {
                    // SKU not found in the nearest warehouse, search in other warehouses
                    List<Inventory> otherInventories = inventoryRepository.findBySkuAndAvailableQuantityGreaterThanZero(sku, nearestWarehouse.getWarehouseId());
                    if (otherInventories.isEmpty()) {
                        throw new InventoryNotFoundException("No inventory found for SKU: " + sku + " in any warehouse.");
                    } else {
                        nearestInventory = otherInventories.get(0); // Use the inventory with high stock
                    }
                }

                if (nearestInventory.getQuantityAvailable() >= orderQuantity) {
                    // Scenario 1: Ordered quantity is less than or equal to available quantity in nearest or other warehouse
                    nearestInventory.setQuantityAvailable(nearestInventory.getQuantityAvailable() - orderQuantity);
                    nearestInventory.setQuantityOnOrder(nearestInventory.getQuantityOnOrder() + orderQuantity);
                    nearestInventory.setQuantityOnHold(nearestInventory.getQuantityOnHold() + orderQuantity);
                    nearestInventory.setLastUpdatedDate(LocalDate.now());
                    inventoryRepository.save(nearestInventory);
                } else {
                    List<Inventory> inventoryList = inventoryRepository.findBySku(sku);
                    int totalQuantityInAllInventories = inventoryList.stream().mapToInt(Inventory::getQuantityAvailable).sum();

                    if(totalQuantityInAllInventories >= orderQuantity) {

                        int remainingQuantity = orderQuantity;
                        int quantityAvailable = nearestInventory.getQuantityAvailable();
                        nearestInventory.setQuantityAvailable(0);
                        nearestInventory.setQuantityOnOrder(nearestInventory.getQuantityOnOrder() + quantityAvailable);
                        nearestInventory.setQuantityOnHold(nearestInventory.getQuantityOnHold() + quantityAvailable);
                        nearestInventory.setLastUpdatedDate(LocalDate.now());
                        remainingQuantity -= quantityAvailable;
                        inventoryRepository.save(nearestInventory);

                        // Continue searching for inventory in other warehouses
                        List<Inventory> otherInventories = inventoryRepository.findBySkuAndAvailableQuantityGreaterThanZero(sku, nearestWarehouse.getWarehouseId());

                        for (Inventory inventory : otherInventories) {
                            if (remainingQuantity <= 0) {
                                break; // All ordered quantities have been allocated
                            }

                            int availableQuantity = inventory.getQuantityAvailable();
                            if (availableQuantity >= remainingQuantity) {
                                inventory.setQuantityAvailable(availableQuantity - remainingQuantity);
                                inventory.setQuantityOnOrder(inventory.getQuantityOnOrder() + remainingQuantity);
                                inventory.setQuantityOnHold(inventory.getQuantityOnHold() + remainingQuantity);
                                nearestInventory.setLastUpdatedDate(LocalDate.now());
                                remainingQuantity = 0; // No more quantity to allocate
                            } else {
                                remainingQuantity -= availableQuantity;   // Remaining quantity to allocate
                                inventory.setQuantityAvailable(0);
                                inventory.setQuantityOnOrder(inventory.getQuantityOnOrder() + availableQuantity);
                                inventory.setQuantityOnHold(inventory.getQuantityOnHold() + availableQuantity);
                                nearestInventory.setLastUpdatedDate(LocalDate.now());
                            }

                            inventoryRepository.save(inventory);
                        }
                    }else{
                        responseMap.put(sku, "Not enough stock to fulfill the order for SKU: " + sku);
                    }
                }

            } else {
                throw new WarehouseNotFoundException("No Warehouse found for the zipcode: " + customerZipcode);
            }
            if(!responseMap.containsKey(sku)) {
                responseMap.put(sku, "Inventory updated with stock reservation");
            }
        }

        return responseMap;
    }

    /**
     * Retrieves the expected delivery date for a given SKU and customer zipcode.
     *
     * <p>
     * The method checks if the SKU is available in the nearest warehouse. If stock is available, a 2-day delivery time is assumed.
     * If not, the method checks other warehouses and assumes a 5-day delivery time if stock is found elsewhere.
     * </p>
     *
     * @param sku the SKU identifier for the product.
     * @param zipcode the customer's delivery zipcode.
     * @return a string representing the expected delivery date in YYYY-MM-DD format.
     * @throws InventoryNotFoundException if no stock is available for the SKU in any warehouse.
     * @throws WarehouseNotFoundException if no warehouse is found for the given zipcode.
     */
    @Override
    public String getExpectedDeliveryDateWithSkuAndZipcode(Long sku, Long zipcode) throws InventoryNotFoundException, WarehouseNotFoundException {

        LOGGER.info("Inside getExpectedDeliveryDateWithSkuAndZipcode() method of InventoryServiceImpl class");

        Optional<Warehouse> warehouseOptional = warehouseRepository.findWarehouseByZipcode(zipcode);

        if (!warehouseOptional.isPresent()) {
            throw new WarehouseNotFoundException("Delivery not available for the zipcode: " + zipcode);
        }

        Warehouse targetWarehouse = warehouseOptional.get();
        Optional<Inventory> inventoryOptional = inventoryRepository.findBySkuAndWarehouse(sku, targetWarehouse);

        if (inventoryOptional.isPresent() && inventoryOptional.get().getQuantityAvailable() > 0) {
            return LocalDate.now().plusDays(2).toString(); // Assume 2 days delivery time
        } else {
            Warehouse nearestWarehouse = findNearestWarehouseWithStock(sku);
            if (nearestWarehouse != null) {
                // Date Format: YYYY-MM-DD
                return LocalDate.now().plusDays(5).toString(); // Assume 5 days delivery time from nearest warehouse
            } else {
                throw new InventoryNotFoundException("No stock available for the SKU: " + sku + " in any inventory.");
            }
        }
    }

    /**
     * Finds the nearest warehouse with available stock for a given SKU.
     *
     * <p>
     * The method searches through all inventories associated with the SKU and returns the first warehouse
     * with available stock.
     * </p>
     *
     * @param sku the SKU identifier for the product.
     * @return the nearest warehouse with available stock, or {@code null} if no stock is found.
     */
    public Warehouse findNearestWarehouseWithStock(Long sku) {
        LOGGER.info("Inside findNearestWarehouseWithStock() method of InventoryServiceImpl class");
        List<Inventory> inventories = inventoryRepository.findBySku(sku);
        for (Inventory inventory : inventories) {
            if (inventory.getQuantityAvailable() > 0) {
                return inventory.getWarehouse();
            }
        }
        return null;
    }

    /**
     * Retrieves an inventory report for all warehouses.
     * <p>
     * This method fetches all warehouses and computes the available,
     * reserved, and total stock quantities for each warehouse. It
     * then maps the results into a list of WarehouseStockDto objects.
     *
     * @return List of WarehouseStockDto containing stock details for each warehouse.
     */
    @Override
    public List<WarehouseStockDto> displayInventoryReport() {
        LOGGER.info("Inside displayInventoryReport() method of InventoryServiceImpl class");

        List<Warehouse> warehouses = warehouseRepository.findAll();

        List<WarehouseStockDto> warehouseStockDtoList = new ArrayList<>();

        for(Warehouse warehouse: warehouses){
            List<Inventory> inventories = inventoryRepository.findByWarehouseId(warehouse.getWarehouseId());

            Integer availableQuantity = inventories.stream().mapToInt(Inventory::getQuantityAvailable).sum();
            Integer reservedQuantity = inventories.stream().mapToInt(Inventory::getQuantityOnHold).sum();
            Integer totalQuantity = availableQuantity + reservedQuantity;

            WarehouseStockDto warehouseStockDto = new WarehouseStockDto(warehouse.getWarehouseId(), availableQuantity,
                    reservedQuantity, totalQuantity);

            warehouseStockDtoList.add(warehouseStockDto);
        }

        return warehouseStockDtoList;
    }

    /**
     * Adds stock to a single inventory.
     * Updates the quantity available for the given SKU in the specified warehouse.
     *
     * @param skuQuantityWarehouseDto DTO containing SKU, quantity to add, and warehouse ID.
     */
    @Override
    @Transactional
    public void addStockToSingleInventory(SkuQuantityWarehouseDto skuQuantityWarehouseDto) {

        LOGGER.info("Inside addStockToSingleInventory() method of InventoryServiceImpl class");

        Long sku = skuQuantityWarehouseDto.getSku();
        Integer quantityToAdd = skuQuantityWarehouseDto.getQuantityToAdd();
        String warehouseId = skuQuantityWarehouseDto.getWarehouseId();

        Inventory inventory = inventoryRepository.findBySkuAndWarehouseId(sku, warehouseId);

        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantityToAdd);
        inventory.setLastUpdatedDate(LocalDate.now());

        inventoryRepository.save(inventory);
    }

    /**
     * Adds stock to multiple inventories.
     * Iterates through a list of SKU, warehouse, and quantity data to update the inventory records.
     *
     * @param skuQuantityWarehouseDtos List of DTOs containing SKU, quantity to add, and warehouse ID.
     */
    @Override
    @Transactional
    public void addStockToMultipleInventories(List<SkuQuantityWarehouseDto> skuQuantityWarehouseDtos) {

        LOGGER.info("Inside addStockToMultipleInventories() method of InventoryServiceImpl class");

        for(SkuQuantityWarehouseDto skuQuantityWarehouseDto: skuQuantityWarehouseDtos){
            Long sku = skuQuantityWarehouseDto.getSku();
            Integer quantityToAdd = skuQuantityWarehouseDto.getQuantityToAdd();
            String warehouseId = skuQuantityWarehouseDto.getWarehouseId();

            Inventory inventory = inventoryRepository.findBySkuAndWarehouseId(sku, warehouseId);

            inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantityToAdd);
            inventory.setLastUpdatedDate(LocalDate.now());

            inventoryRepository.save(inventory);
        }
    }

    /**
     * Retrieves consolidated inventory details for all SKUs.
     * <p>
     * This method fetches all inventory records, groups them by SKU,
     * and calculates the available, reserved, and total stock quantities
     * for each SKU. It then maps the results into a list of
     * InventoryConsoleResponse objects.
     *
     * @return List of InventoryConsoleResponse with stock details for each SKU.
     */
    @Override
    public List<InventoryConsoleResponse> getAllInventories() {

        LOGGER.info("Inside getAllInventories() method of InventoryServiceImpl class");

        // Fetch all inventories at once
        List<Inventory> inventoryList = inventoryRepository.findAll();

        // Group inventories by SKU
        Map<Long, List<Inventory>> inventoriesBySku = inventoryList.stream()
                .collect(Collectors.groupingBy(Inventory::getSku));

        List<InventoryConsoleResponse> consoleResponses = new ArrayList<>();

        // Process each SKU group
        inventoriesBySku.forEach((sku, inventories) -> {
            Integer availableQuantity = inventories.stream().mapToInt(Inventory::getQuantityAvailable).sum();
            Integer reservedQuantity = inventories.stream().mapToInt(Inventory::getQuantityOnHold).sum();
            Integer totalQuantity = availableQuantity + reservedQuantity;

            InventoryConsoleResponse consoleResponse = new InventoryConsoleResponse(sku, availableQuantity, reservedQuantity, totalQuantity);
            consoleResponses.add(consoleResponse);
        });

        return consoleResponses;
    }

    /**
     * Retrieves the total available quantity for a given item ID across all inventories.
     *
     * @param itemId the ID of the item to check inventory for
     * @return the total quantity available for the specified item ID
     * @throws InventoryNotFoundException if no inventory is found for the given item ID
     */
    @Override
    public Integer getQuantityByItemId(Long itemId) throws InventoryNotFoundException {
        LOGGER.info("Inside getQuantityByItemId() method of InventoryServiceImpl class");

        List<Inventory> inventories = inventoryRepository.findByItemId(itemId);

        if(inventories.isEmpty()){
            throw new InventoryNotFoundException("No inventory found for item id: " + itemId);
        }

        return inventories.stream().mapToInt(Inventory::getQuantityAvailable).sum();
    }

    /**
     * Retrieves the total available quantity for a given SKU across all inventories.
     *
     * @param sku the SKU (Stock Keeping Unit) to check inventory for
     * @return the total quantity available for the specified SKU
     * @throws InventoryNotFoundException if no inventory is found for the given SKU
     */
    @Override
    public Integer getQuantityBySku(Long sku) throws InventoryNotFoundException {
        LOGGER.info("Inside getQuantityBySku() method of InventoryServiceImpl class");

        List<Inventory> inventories = inventoryRepository.findBySku(sku);

        if(inventories.isEmpty()){
            throw new InventoryNotFoundException("No inventory found for sku: " + sku);
        }

        return inventories.stream().mapToInt(Inventory::getQuantityAvailable).sum();
    }

    /**
     * Confirms stock reservations by resetting the quantity on hold to zero for each SKU in the provided list.
     * If no inventory with stock on hold is found for a SKU, an InventoryNotFoundException is thrown.
     *
     * @param skuList List of SKUs for which stock reservations need to be confirmed.
     * @throws InventoryNotFoundException if no inventory with stock on hold is found for any SKU.
     */
    @Transactional
    @Override
    public void confirmStockReservation(List<Long> skuList) throws InventoryNotFoundException {
        LOGGER.info("Inside confirmStockReservation() method of InventoryServiceImpl class");

        for (Long sku : skuList) {
            // Find all inventories where this SKU has stock on hold
            List<Inventory> inventoriesWithHoldStock = inventoryRepository.findBySkuAndQuantityOnHoldGreaterThanZero(sku);

            if (inventoriesWithHoldStock.isEmpty()) {
                throw new InventoryNotFoundException("No inventory found with stock on hold for SKU: " + sku);
            }

            for (Inventory inventory : inventoriesWithHoldStock) {
                // Reset the quantity on hold to 0
                inventory.setQuantityOnHold(0);
                inventoryRepository.save(inventory);
            }
        }
    }

    /**
     * Retrieves all warehouse details along with inventory information.
     * Iterates through all inventory records, extracts relevant data including SKU, available quantity,
     * reserved quantity, and total quantity, and maps them into a response object.
     * The response includes warehouse details and stock information for each inventory item.
     *
     * @return List of InventoryLocationResponse containing warehouse and inventory details.
     */
    @Override
    public List<InventoryLocationResponse> getAllWarehouses() {
        LOGGER.info("Inside getAllWarehouses() method of InventoryServiceImpl class");

        List<Inventory> inventories = inventoryRepository.findAll();

        List<InventoryLocationResponse> responseList = new ArrayList<>();

        for (Inventory inventory : inventories) {
            Warehouse warehouse = inventory.getWarehouse();
            Long sku = inventory.getSku();
            Integer availableQuantity = inventory.getQuantityAvailable();
            Integer reservedQuantity = inventory.getQuantityOnHold();
            Integer totalQuantity = availableQuantity + reservedQuantity;

            InventoryLocationResponse response = new InventoryLocationResponse(warehouse.getWarehouseId(),
                    sku, availableQuantity, reservedQuantity, totalQuantity);
            responseList.add(response);
        }

        return responseList;
    }

    /**
     * Reverts the reserved stock for a list of ordered items. It first checks the nearest warehouse based on the
     * customer's zipcode. If sufficient stock is not available in the nearest warehouse or the SKU is not found,
     * it reverts stock from other warehouses. Throws an exception if the stock cannot be fully reverted.
     *
     * @param customerOrderItemDtos List of items ordered by the customer.
     * @param customerZipcode The zipcode of the customer to find the nearest warehouse.
     * @throws InventoryNotFoundException if enough reserved stock is not available for the specified SKU.
     * @throws WarehouseNotFoundException if no warehouse is found for the provided zipcode.
     */
    @Transactional
    @Override
    public void revertStockIfOrderCancel(List<CustomerOrderItemDto> customerOrderItemDtos, Long customerZipcode) throws WarehouseNotFoundException, InventoryNotFoundException {
        LOGGER.info("Inside revertStockReservation() method of InventoryServiceImpl class");

        for (CustomerOrderItemDto customerOrderItemDto : customerOrderItemDtos) {
            Long sku = customerOrderItemDto.getSku();
            Integer orderQuantity = customerOrderItemDto.getOrderQuantity();

            Optional<Warehouse> nearestWarehouseOptional = warehouseRepository.findWarehouseByZipcode(customerZipcode);

            if (!nearestWarehouseOptional.isPresent()) {
                throw new WarehouseNotFoundException("No Warehouse found for the zipcode: " + customerZipcode);
            }

            Warehouse nearestWarehouse = nearestWarehouseOptional.get();
            Optional<Inventory> nearestInventoryOptional = inventoryRepository.findBySkuAndWarehouse(sku, nearestWarehouse);

            int remainingQuantity = orderQuantity;

            if (nearestInventoryOptional.isPresent()) {
                Inventory nearestInventory = nearestInventoryOptional.get();

                // Check if there is enough reserved stock in the nearest warehouse to revert
                if (nearestInventory.getQuantityOnOrder() >= remainingQuantity) {
                    nearestInventory.setQuantityAvailable(nearestInventory.getQuantityAvailable() + remainingQuantity);
                    nearestInventory.setQuantityOnOrder(nearestInventory.getQuantityOnOrder() - remainingQuantity);
                    nearestInventory.setLastUpdatedDate(LocalDate.now());
                    inventoryRepository.save(nearestInventory);
                    remainingQuantity = 0; // All stock has been reverted
                } else {
                    // Partially revert the stock from the nearest warehouse
                    remainingQuantity -= nearestInventory.getQuantityOnOrder();
                    nearestInventory.setQuantityAvailable(nearestInventory.getQuantityAvailable() + nearestInventory.getQuantityOnOrder());
                    nearestInventory.setQuantityOnOrder(0); // All reserved stock from the nearest warehouse is reverted
                    nearestInventory.setLastUpdatedDate(LocalDate.now());
                    inventoryRepository.save(nearestInventory);
                }
            }
            // Step 4: If SKU is not found or stock is still remaining, revert from other warehouses
            if (remainingQuantity > 0) {
                // Find all other warehouses excluding the nearest warehouse
                List<Inventory> otherInventories = inventoryRepository.findBySkuAndAvailableQuantityGreaterThanZero(sku, nearestWarehouse.getWarehouseId());

                for (Inventory inventory : otherInventories) {
                    if (remainingQuantity <= 0) {
                        break; // All stock has been reverted
                    }
                    // Revert the remaining stock
                    int quantityToRevert = Math.min(inventory.getQuantityOnOrder(), remainingQuantity);
                    inventory.setQuantityAvailable(inventory.getQuantityAvailable() + quantityToRevert);
                    inventory.setQuantityOnOrder(inventory.getQuantityOnOrder() - quantityToRevert);
                    inventory.setLastUpdatedDate(LocalDate.now());
                    inventoryRepository.save(inventory);

                    remainingQuantity -= quantityToRevert;
                }
            }

            // Step 5: If there's still remaining quantity that couldn't be reverted
            if (remainingQuantity > 0) {
                LOGGER.warn("Could not revert all stock for SKU: {}. Remaining quantity: {}", sku, remainingQuantity);
                throw new InventoryNotFoundException("Not enough reserved stock available to revert for SKU: " + sku);
            }
        }
    }
}
