package com.nisum.vibe.cart.scm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.vibe.cart.scm.model.*;
import com.nisum.vibe.cart.scm.service.InventoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(InventoryController.class)
@AutoConfigureMockMvc(addFilters = false)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCheckSkuQuantityEndpoint() throws Exception {

        List<Long> skuList = Arrays.asList(950L, 970L);
        List<Integer> quantityList = Arrays.asList(30, 27);

        when(inventoryService.checkSkuQuantity(skuList)).thenReturn(quantityList);

        mockMvc.perform(MockMvcRequestBuilders.post("/vibe-cart/scm/inventory/check-quantity")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[950, 970]"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("SKUs quantities retrieved successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0]").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1]").value(27));

        Mockito.verify(inventoryService, Mockito.times(1)).checkSkuQuantity(skuList);
    }

    @Test
    void testGetExpectedDeliveryDateEndpoint() throws Exception {
        Long sku = 1004L;
        Long zipcode = 500001L;

        String result = LocalDate.now().plusDays(2).toString();

        when(inventoryService.getExpectedDeliveryDateWithSkuAndZipcode(sku, zipcode)).thenReturn(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/vibe-cart/scm/inventory/expected-delivery-date")
                .param("sku", "1004")
                .param("zipcode", "500001"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Expected delivery date retrieved successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(LocalDate.now().plusDays(2).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));

        Mockito.verify(inventoryService, Mockito.times(1)).getExpectedDeliveryDateWithSkuAndZipcode(sku, zipcode);
    }

    @Test
    void testAddStockToSingleInventoryEndpoint() throws Exception {
        SkuQuantityWarehouseDto skuQuantityWarehouseDto = new SkuQuantityWarehouseDto(1104L, 15, "INV0001");

        Mockito.doNothing().when(inventoryService).addStockToSingleInventory(skuQuantityWarehouseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/vibe-cart/scm/inventory/update-single-inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"sku\": 1104,\n" +
                        "    \"quantityToAdd\": 15,\n" +
                        "    \"warehouseId\": \"INV0001\" \n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Stock updated in the inventory successfully"));
        Mockito.verify(inventoryService, Mockito.times(1)).addStockToSingleInventory
                (ArgumentMatchers.refEq(skuQuantityWarehouseDto));
    }

    @Test
    void testAddStockToMultipleInventoriesEndpoint() throws Exception {
        SkuQuantityWarehouseDto skuQuantityWarehouseDto1 = new SkuQuantityWarehouseDto(1104L, 10, "INV0002");
        SkuQuantityWarehouseDto skuQuantityWarehouseDto2 = new SkuQuantityWarehouseDto(1093L, 13, "INV0003");
        List<SkuQuantityWarehouseDto> skuQuantityWarehouseDtos = Arrays.asList
                (skuQuantityWarehouseDto1, skuQuantityWarehouseDto2);

        Mockito.doNothing().when(inventoryService).addStockToMultipleInventories(skuQuantityWarehouseDtos);

        mockMvc.perform(MockMvcRequestBuilders.put("/vibe-cart/scm/inventory/update-multiple-inventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\n" +
                                "    {\n" +
                                "        \"sku\": 1104,\n" +
                                "        \"quantityToAdd\": 10,\n" +
                                "        \"warehouseId\": \"INV0002\"\n" +
                                "    },\n" +
                                "    {\n" +
                                "        \"sku\": 1093,\n" +
                                "        \"quantityToAdd\": 13,\n" +
                                "        \"warehouseId\": \"INV0003\"\n" +
                                "    }\n" +
                                "]"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Stock updated in all inventories successfully"));
    }

    @Test
    void testGetAllInventoriesEndpoint() throws Exception {
        InventoryConsoleResponse response1 = new InventoryConsoleResponse();
        InventoryConsoleResponse response2 = new InventoryConsoleResponse();

        when(inventoryService.getAllInventories()).thenReturn(Arrays.asList(response1, response2));

        mockMvc.perform(MockMvcRequestBuilders.get("/vibe-cart/scm/inventory/get-all-inventories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("All inventory details retrieved successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)));

        Mockito.verify(inventoryService, Mockito.times(1)).getAllInventories();
    }

    @Test
    void testDisplayInventoryReportEndpoint() throws Exception {
        WarehouseStockDto warehouseStockDto1 = new WarehouseStockDto("INV0001", 35, 10, 45);
        WarehouseStockDto warehouseStockDto2 = new WarehouseStockDto("INV0002", 40, 12, 52);
        List<WarehouseStockDto> warehouseStockDtoList = Arrays.asList(warehouseStockDto1, warehouseStockDto2);

        when(inventoryService.displayInventoryReport()).thenReturn(warehouseStockDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/vibe-cart/scm/inventory/inventory-report")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Inventory report retrieved successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].warehouseId", Matchers.is("INV0001")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].warehouseId", Matchers.is("INV0002")));

        Mockito.verify(inventoryService, Mockito.times(1)).displayInventoryReport();
    }

    @Test
    void testGetAllWarehousesEndpoint() throws Exception {
        InventoryLocationResponse inventoryLocationResponse1 = new InventoryLocationResponse("INV0001", 1234L, 45, 7, 52);
        InventoryLocationResponse inventoryLocationResponse2 = new InventoryLocationResponse("INV0002", 1235L, 40, 8, 48);
        List<InventoryLocationResponse> responseList = Arrays.asList(inventoryLocationResponse1, inventoryLocationResponse2);

        when(inventoryService.getAllWarehouses()).thenReturn(responseList);

        mockMvc.perform(MockMvcRequestBuilders.get("/vibe-cart/scm/inventory/get-all-warehouses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("All warehouse details retrieved successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].warehouseId", Matchers.is("INV0001")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].skuId", Matchers.is(1234)));
        Mockito.verify(inventoryService, Mockito.times(1)).getAllWarehouses();
    }

    @Test
    void testGetQuantityByItemIdEndpoint() throws Exception {
        Long itemId = 300L;
        Integer quantity = 150;

        when(inventoryService.getQuantityByItemId(itemId)).thenReturn(quantity);

        mockMvc.perform(MockMvcRequestBuilders.get("/vibe-cart/scm/inventory/quantity-by-itemId")
                        .param("itemId", itemId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Quantity by Item ID retrieved successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is(quantity)));

        Mockito.verify(inventoryService, Mockito.times(1)).getQuantityByItemId(itemId);
    }

    @Test
    void testGetQuantityBySku() throws Exception {
        Long sku = 12345L;
        Integer quantity = 250;

        when(inventoryService.getQuantityBySku(sku)).thenReturn(quantity);

        mockMvc.perform(MockMvcRequestBuilders.get("/vibe-cart/scm/inventory/quantity-by-sku")
                        .param("sku", sku.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Matchers.is(true)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Quantity by SKU ID retrieved successfully")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.is(quantity)));

        Mockito.verify(inventoryService, Mockito.times(1)).getQuantityBySku(sku);
    }
}