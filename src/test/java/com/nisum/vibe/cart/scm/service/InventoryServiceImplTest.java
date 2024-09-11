package com.nisum.vibe.cart.scm.service;

import com.nisum.vibe.cart.scm.entity.Inventory;
import com.nisum.vibe.cart.scm.entity.Warehouse;
import com.nisum.vibe.cart.scm.exception.InventoryNotFoundException;
import com.nisum.vibe.cart.scm.exception.WarehouseNotFoundException;
import com.nisum.vibe.cart.scm.model.*;
import com.nisum.vibe.cart.scm.repository.InventoryRepository;
import com.nisum.vibe.cart.scm.repository.WarehouseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @Spy
    @InjectMocks
    private InventoryServiceImpl inventoryServiceImpl;

    @Test
    void testCheckItemQuantity_MultipleInventoriesWithStock() {
        Long sku = 302L;

        Warehouse warehouse1 = new Warehouse(), warehouse2 = new Warehouse();

        Inventory inventory1 = new Inventory();
        inventory1.setSku(sku);
        inventory1.setQuantityAvailable(45);
        inventory1.setWarehouse(warehouse1);

        Inventory inventory2 = new Inventory();
        inventory2.setSku(sku);
        inventory2.setQuantityAvailable(40);
        inventory2.setWarehouse(warehouse2);

        when(inventoryRepository.findBySku(sku)).thenReturn(Arrays.asList(inventory1, inventory2));

        List<Integer> result = inventoryServiceImpl.checkSkuQuantity(Collections.singletonList(sku));

        assertEquals(1, result.size());
        assertEquals(85, result.get(0));
    }

    @Test
    void testCheckItemQuantity_InventoryWithZeroQuantity() {
        Long sku = 302L;

        Warehouse warehouse = new Warehouse();

        Inventory inventory = new Inventory();
        inventory.setSku(sku);
        inventory.setQuantityAvailable(0);
        inventory.setWarehouse(warehouse);

        when(inventoryRepository.findBySku(sku)).thenReturn(Collections.singletonList(inventory));

        List<Integer> result = inventoryServiceImpl.checkSkuQuantity(Collections.singletonList(sku));

        assertEquals(0, result.get(0));
    }

    @Test
    void testCheckItemQuantity_SkuNotFoundInInventory() {
        Long sku = 303L;

        when(inventoryRepository.findBySku(sku)).thenReturn(Collections.emptyList());

        List<Integer> result = inventoryServiceImpl.checkSkuQuantity(Collections.singletonList(sku));

        assertEquals(Collections.singletonList(0), result);
    }


    @Test
    void testStockReservation_WithSufficientStockInNearestWarehouse() throws WarehouseNotFoundException, InventoryNotFoundException {
        Long customerZipcode = 12345L;
        Long sku = 1276L;
        Integer orderQuantity = 15;

        CustomerOrderItemDto customerOrderItemDto = new CustomerOrderItemDto(sku, orderQuantity);
        List<CustomerOrderItemDto> customerOrderItemDtos = Collections.singletonList(customerOrderItemDto);

        Warehouse warehouse = new Warehouse("INV0001", "Mumbai Warehouse", "Mumbai", 400001L, 400706L);
        Inventory inventory = new Inventory(1L, 301L, sku, 45, warehouse, 0, 0, null);

        when(warehouseRepository.findWarehouseByZipcode(customerZipcode)).thenReturn(Optional.of(warehouse));
        when(inventoryRepository.findBySkuAndWarehouse(sku, warehouse)).thenReturn(Optional.of(inventory));

        Map<Long, String> result = inventoryServiceImpl.stockReservationCall(customerOrderItemDtos, customerZipcode);

        assertEquals(1, result.size());
        verify(inventoryRepository).save(inventory);
        assertEquals(30, inventory.getQuantityAvailable());
        assertEquals(15, inventory.getQuantityOnOrder());
        assertEquals(15, inventory.getQuantityOnHold());
    }

    @Test
    void testStockReservation_WithWarehouseNotFound() {
        Long customerZipcode = 12345L;
        Long sku = 1001L;
        Integer orderQuantity = 10;

        CustomerOrderItemDto customerOrderItemDto = new CustomerOrderItemDto(sku, orderQuantity);
        List<CustomerOrderItemDto> customerOrderItemDtos = Collections.singletonList(customerOrderItemDto);

        when(warehouseRepository.findWarehouseByZipcode(customerZipcode)).thenReturn(Optional.empty());

        WarehouseNotFoundException exception = assertThrows(WarehouseNotFoundException.class, () -> {
            inventoryServiceImpl.stockReservationCall(customerOrderItemDtos, customerZipcode);
        });

        verify(warehouseRepository).findWarehouseByZipcode(customerZipcode);
        verify(inventoryRepository, never()).save(any());
        assertEquals("No Warehouse found for the zipcode: 12345", exception.getMessage());
    }

    @Test
    void testGetExpectedDeliveryDate_WarehouseNotAvailable() {
        Long sku = 12355L;
        Long zipcode = 452001L;

        when(warehouseRepository.findWarehouseByZipcode(zipcode)).thenReturn(Optional.empty());

        WarehouseNotFoundException exception = assertThrows(WarehouseNotFoundException.class, () -> {
            inventoryServiceImpl.getExpectedDeliveryDateWithSkuAndZipcode(sku, zipcode);
        });

        assertEquals("Delivery not available for the zipcode: 452001", exception.getMessage());
    }


    @Test
    void testGetExpectedDeliveryDate_StockAvailableInTargetWarehouse() throws InventoryNotFoundException, WarehouseNotFoundException {
        Long sku = 12355L;
        Long zipcode = 452001L;

        Warehouse warehouse = new Warehouse();
        Inventory inventory = new Inventory();
        inventory.setSku(sku);
        inventory.setQuantityAvailable(30);
        inventory.setWarehouse(warehouse);

        when(warehouseRepository.findWarehouseByZipcode(zipcode)).thenReturn(Optional.of(warehouse));
        when(inventoryRepository.findBySkuAndWarehouse(sku, warehouse)).thenReturn(Optional.of(inventory));

        String result = inventoryServiceImpl.getExpectedDeliveryDateWithSkuAndZipcode(sku, zipcode);

        assertEquals(LocalDate.now().plusDays(2).toString(), result);
    }

    @Test
    void testGetExpectedDeliveryDate_StockNotAvailableInTargetWarehouseButAvailableInNearestWarehouse() throws InventoryNotFoundException, WarehouseNotFoundException {
        Long sku = 12355L;
        Long zipcode = 452001L;

        Warehouse warehouse = new Warehouse();

        Inventory inventory = new Inventory();
        inventory.setSku(sku);
        inventory.setQuantityAvailable(0);
        inventory.setWarehouse(warehouse);

        Warehouse nearestWarehouse = new Warehouse();

        when(warehouseRepository.findWarehouseByZipcode(zipcode)).thenReturn(Optional.of(warehouse));
        when(inventoryRepository.findBySkuAndWarehouse(sku, warehouse)).thenReturn(Optional.of(inventory));
        Mockito.doReturn(nearestWarehouse).when(inventoryServiceImpl).findNearestWarehouseWithStock(sku);

        String result = inventoryServiceImpl.getExpectedDeliveryDateWithSkuAndZipcode(sku, zipcode);

        assertEquals(LocalDate.now().plusDays(5).toString(), result);
    }

    @Test
    void testGetExpectedDeliveryDate_StockNotAvailableInAnyWarehouse() {
        Long sku = 12355L;
        Long zipcode = 452001L;

        Warehouse warehouse = new Warehouse();
        Inventory inventory = new Inventory();
        inventory.setSku(sku);
        inventory.setQuantityAvailable(0);
        inventory.setWarehouse(warehouse);

        when(warehouseRepository.findWarehouseByZipcode(zipcode)).thenReturn(Optional.of(warehouse));
        when(inventoryRepository.findBySkuAndWarehouse(sku, warehouse)).thenReturn(Optional.of(inventory));
        Mockito.doReturn(null).when(inventoryServiceImpl).findNearestWarehouseWithStock(sku);

        InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, () -> {
            inventoryServiceImpl.getExpectedDeliveryDateWithSkuAndZipcode(sku, zipcode);
        });

        assertEquals("No stock available for the SKU: 12355 in any inventory.", exception.getMessage());
    }

    @Test
    public void testAddStockToSingleInventory() {
        SkuQuantityWarehouseDto dto = new SkuQuantityWarehouseDto(12345L, 20, "INV0001");

        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId("INV0001");

        Inventory inventory = new Inventory();
        inventory.setSku(12345L);
        inventory.setWarehouse(warehouse);
        inventory.setQuantityAvailable(30);
        inventory.setLastUpdatedDate(null);

        when(inventoryRepository.findBySkuAndWarehouseId(12345L, warehouse.getWarehouseId())).thenReturn(inventory);

        inventoryServiceImpl.addStockToSingleInventory(dto);

        verify(inventoryRepository, times(1)).save(inventory);
        verify(inventoryRepository, times(1)).findBySkuAndWarehouseId(12345L, warehouse.getWarehouseId());

        assert inventory.getQuantityAvailable().equals(50);
        assert inventory.getLastUpdatedDate().equals(LocalDate.now());
    }

    @Test
    public void testAddStockToMultipleInventories_Success() throws InventoryNotFoundException, WarehouseNotFoundException {
        // Arrange
        SkuQuantityWarehouseDto dto1 = new SkuQuantityWarehouseDto(12345L, 20, "INV0001");
        SkuQuantityWarehouseDto dto2 = new SkuQuantityWarehouseDto(12346L, 15, "INV0002");
        List<SkuQuantityWarehouseDto> dtos = Arrays.asList(dto1, dto2);

        Warehouse warehouse1 = new Warehouse();
        warehouse1.setWarehouseId("INV0001");
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setWarehouseId("INV0002");

        Inventory inventory1 = new Inventory();
        inventory1.setSku(12345L);
        inventory1.setWarehouse(warehouse1);
        inventory1.setQuantityAvailable(30);
        inventory1.setLastUpdatedDate(null);
        Inventory inventory2 = new Inventory();
        inventory2.setSku(12346L);
        inventory2.setWarehouse(warehouse2);
        inventory2.setQuantityAvailable(35);
        inventory2.setLastUpdatedDate(null);

        when(inventoryRepository.findBySkuAndWarehouseId(12345L, warehouse1.getWarehouseId())).thenReturn(inventory1);
        when(inventoryRepository.findBySkuAndWarehouseId(12346L, warehouse2.getWarehouseId())).thenReturn(inventory2);

        inventoryServiceImpl.addStockToMultipleInventories(dtos);

        verify(inventoryRepository, times(1)).save(inventory1);
        verify(inventoryRepository, times(1)).save(inventory2);

        assert inventory1.getQuantityAvailable().equals(50);
        assert inventory1.getLastUpdatedDate().equals(LocalDate.now());
        assert inventory2.getQuantityAvailable().equals(50);
        assert inventory2.getLastUpdatedDate().equals(LocalDate.now());
    }

    @Test
    void testDisplayInventoryReport_WithValidData() {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId("INV0001");

        Inventory inventory1 = new Inventory(1L, 301L, 1001L, 40, warehouse, 10, 0, null);
        Inventory inventory2 = new Inventory(2L, 301L, 1002L, 35, warehouse, 12, 0, null);

        when(warehouseRepository.findAll()).thenReturn(Collections.singletonList(warehouse));
        when(inventoryRepository.findByWarehouseId(warehouse.getWarehouseId())).thenReturn(Arrays.asList(inventory1, inventory2));

        List<WarehouseStockDto> result = inventoryServiceImpl.displayInventoryReport();

        assertNotNull(result);
        assertEquals(1, result.size());

        WarehouseStockDto warehouseStockDto = result.get(0);
        assertEquals("INV0001", warehouseStockDto.getWarehouseId());
        assertEquals(75, warehouseStockDto.getAvailableQuantity());
        assertEquals(22, warehouseStockDto.getReservedQuantity());
        assertEquals(97, warehouseStockDto.getTotalQuantity());

        verify(warehouseRepository).findAll();
        verify(inventoryRepository).findByWarehouseId(warehouse.getWarehouseId());
    }

    @Test
    void testGetQuantityByItemId_Success() throws InventoryNotFoundException {
        Long itemId = 303L;
        Inventory inventory1 = new Inventory();
        inventory1.setItemId(itemId);
        inventory1.setQuantityAvailable(42);

        Inventory inventory2 = new Inventory();
        inventory2.setItemId(itemId);
        inventory2.setQuantityAvailable(35);

        when(inventoryRepository.findByItemId(itemId)).thenReturn(Arrays.asList(inventory1, inventory2));

        Integer totalQuantity = inventoryServiceImpl.getQuantityByItemId(itemId);

        assertNotNull(totalQuantity);
        assertEquals(77, totalQuantity);
        verify(inventoryRepository).findByItemId(itemId);
    }

    @Test
    void testGetQuantityByItemId_ThrowsInventoryNotFoundException() {
        Long itemId = 1001L;

        when(inventoryRepository.findByItemId(itemId)).thenReturn(Collections.emptyList());

        InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, () -> {
            inventoryServiceImpl.getQuantityByItemId(itemId);
        });

        verify(inventoryRepository).findByItemId(itemId);
        assertEquals("No inventory found for item id: 1001", exception.getMessage());
    }

    @Test
    void testGetQuantityBySku_Success() throws InventoryNotFoundException {
        Long sku = 2001L;
        Inventory inventory1 = new Inventory();
        inventory1.setSku(sku);
        inventory1.setQuantityAvailable(30);

        Inventory inventory2 = new Inventory();
        inventory2.setSku(sku);
        inventory2.setQuantityAvailable(20);

        when(inventoryRepository.findBySku(sku)).thenReturn(Arrays.asList(inventory1, inventory2));

        Integer totalQuantity = inventoryServiceImpl.getQuantityBySku(sku);

        assertEquals(50, totalQuantity);
        verify(inventoryRepository).findBySku(sku);
    }

    @Test
    void testGetQuantityBySku_ThrowsInventoryNotFoundException() {
        Long sku = 2001L;

        when(inventoryRepository.findBySku(sku)).thenReturn(Collections.emptyList());

        InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, () -> {
            inventoryServiceImpl.getQuantityBySku(sku);
        });

        verify(inventoryRepository).findBySku(sku);
        assertEquals("No inventory found for sku: 2001", exception.getMessage());
    }

    @Test
    void testGetAllInventoriesSuccess() {
        Warehouse warehouse = new Warehouse();
        Inventory inventory1 = new Inventory(1L, 301L, 1001L, 40, warehouse, 10, 0, null);
        Inventory inventory2 = new Inventory(2L, 301L, 1002L, 35, warehouse, 12, 0, null);

        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inventory1, inventory2));

        List<InventoryConsoleResponse> result = inventoryServiceImpl.getAllInventories();

        assertNotNull(result);
        assertEquals(2, result.size());

        InventoryConsoleResponse response1 = result.get(0);
        assertEquals(1001, response1.getSkuId());
        assertEquals(40, response1.getAvailableQuantity());
        assertEquals(10, response1.getReservedQuantity());
        assertEquals(50, response1.getTotalQuantity());

        InventoryConsoleResponse response2 = result.get(1);
        assertEquals(1002, response2.getSkuId());
        assertEquals(35, response2.getAvailableQuantity());
        assertEquals(12, response2.getReservedQuantity());
        assertEquals(47, response2.getTotalQuantity());

        verify(inventoryRepository).findAll();
    }

    @Test
    void testGetAllWarehousesSuccess() {
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setWarehouseId("INV0001");
        Warehouse warehouse2 = new Warehouse();
        warehouse2.setWarehouseId("INV0002");

        Inventory inventory1 = new Inventory(1L, 301L, 1001L, 40, warehouse1, 10, 0, null);
        Inventory inventory2 = new Inventory(2L, 301L, 1002L, 35, warehouse2, 12, 0, null);

        when(inventoryRepository.findAll()).thenReturn(Arrays.asList(inventory1, inventory2));

        List<InventoryLocationResponse> result = inventoryServiceImpl.getAllWarehouses();

        assertNotNull(result);
        assertEquals(2, result.size());

        InventoryLocationResponse response1 = result.get(0);
        assertEquals(1001L, response1.getSkuId());
        assertEquals(40, response1.getAvailableQuantity());
        assertEquals(10, response1.getReservedQuantity());
        assertEquals(50, response1.getTotalQuantity());

        InventoryLocationResponse response2 = result.get(1);
        assertEquals(1002L, response2.getSkuId());
        assertEquals(35, response2.getAvailableQuantity());
        assertEquals(12, response2.getReservedQuantity());
        assertEquals(47, response2.getTotalQuantity());

        verify(inventoryRepository).findAll();
    }

    @Test
    void testConfirmStockReservationSuccess() throws InventoryNotFoundException {
        Inventory inventory1 = new Inventory();
        inventory1.setSku(1001L);
        inventory1.setQuantityOnHold(15);

        Inventory inventory2 = new Inventory();
        inventory1.setSku(1001L);
        inventory1.setQuantityOnHold(10);

        List<Long> skuList = Collections.singletonList(1001L);

        when(inventoryRepository.findBySkuAndQuantityOnHoldGreaterThanZero(1001L))
                .thenReturn(Arrays.asList(inventory1, inventory2));

        inventoryServiceImpl.confirmStockReservation(skuList);

        assertEquals(0, inventory1.getQuantityOnHold());
        assertEquals(0, inventory2.getQuantityOnHold());

        verify(inventoryRepository, times(1)).findBySkuAndQuantityOnHoldGreaterThanZero(1001L);
        verify(inventoryRepository, times(2)).save(any(Inventory.class));
    }

    @Test
    void testConfirmStockReservationInventoryNotFound() {
        List<Long> skuList = Collections.singletonList(1002L);

        when(inventoryRepository.findBySkuAndQuantityOnHoldGreaterThanZero(1002L))
                .thenReturn(Collections.emptyList());

        InventoryNotFoundException exception = assertThrows(InventoryNotFoundException.class, () -> {
            inventoryServiceImpl.confirmStockReservation(skuList);
        });

        assertEquals("No inventory found with stock on hold for SKU: 1002", exception.getMessage());

        verify(inventoryRepository, times(1)).findBySkuAndQuantityOnHoldGreaterThanZero(1002L);
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }
}