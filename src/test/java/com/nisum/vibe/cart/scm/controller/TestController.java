package com.nisum.vibe.cart.scm.controller;

import com.nisum.vibe.cart.scm.response.ApiResponse;
import com.nisum.vibe.cart.scm.exception.InventoryNotFoundException;
import com.nisum.vibe.cart.scm.exception.OrderNotFoundException;
import com.nisum.vibe.cart.scm.exception.WarehouseNotFoundException;
import com.nisum.vibe.cart.scm.model.*;
import com.nisum.vibe.cart.scm.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the OrderController class.
 */
class TestController {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private OrderDTO orderDTO;

    /**
     * Setup method to initialize the test environment before each test.
     * Mocks are initialized and an OrderDTO object is created and populated with sample data.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        orderDTO = new OrderDTO();

        // Set properties of orderDTO as needed for tests
        orderDTO.setOrderId("278C5773AA16");
        orderDTO.setOrderDate(Instant.now());
        orderDTO.setCreatedDate(Instant.now());
        orderDTO.setUpdatedDate(Instant.now());

        OrderItemDTO item1 = new OrderItemDTO();
        item1.setItemId(1001L);
        item1.setItemName("Product 1");
        item1.setQuantity(2);
        item1.setPrice(new BigDecimal("50.00"));

        OrderItemDTO item2 = new OrderItemDTO();
        item2.setItemId(1002L);
        item2.setItemName("Product 2");
        item2.setQuantity(1);
        item2.setPrice(new BigDecimal("30.00"));

        List<OrderItemDTO> Items = Arrays.asList(item1, item2);
        orderDTO.setOrderItems(Items);
        AddressDTO addressDTOObj = new AddressDTO();
        addressDTOObj.setAddress("123 Main St");
        addressDTOObj.setCity("Springfield");
        addressDTOObj.setState("IL");
        addressDTOObj.setZipcode("62704");

        orderDTO.setTotalAmount(130.00);
        orderDTO.setTotalQuantity(3);
        orderDTO.setEstimated_delivery_date(LocalDateTime.now().plusDays(3));
        orderDTO.setShippingAddress(addressDTOObj);
        orderDTO.setBillingAddress(addressDTOObj);
        orderDTO.setShippingzipCode(12345L);
        orderDTO.setOrderStatus(OrderStatus.CONFIRMED);
        orderDTO.setPaymentStatus(PaymentStatus.COMPLETED);
    }

    /**
     * Test method for the getAllOrders endpoint.
     * Verifies that the service method is called once and returns the expected list of orders.
     */
    @Test
    void testGetAllOrders() {
        List<OrderDTO> orderList = Collections.singletonList(orderDTO);
        when(orderService.getAllOrders()).thenReturn(orderList);

        ResponseEntity<ApiResponse<List<OrderDTO>>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderList, Objects.requireNonNull(response.getBody()).getData());
        assertEquals("Orders retrieved successfully", response.getBody().getMessage());
        verify(orderService, times(1)).getAllOrders();
    }

    /**
     * Test method for the getOrderHistoryById endpoint when the order exists.
     * Verifies that the service method returns the expected order and status.
     */
    @Test
    void testGetOrderHistoryById_Success() {
        List<OrderDTO> orderList = Collections.singletonList(orderDTO);
        when(orderService.getOrderHistoryByCustomerId(101L)).thenReturn(orderList);

        ResponseEntity<ApiResponse<List<OrderDTO>>> response = orderController.getOrderHistoryByCustomerId(101L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderList, Objects.requireNonNull(response.getBody()).getData());
        assertEquals("Order history retrieved successfully", response.getBody().getMessage());
        verify(orderService, times(1)).getOrderHistoryByCustomerId(101L);
    }

    /**
     * Test method for the getOrderHistoryById endpoint when the order is not found.
     * Verifies that an OrderNotFoundException is thrown.
     */

    @Test
    void testGetOrderHistoryByCustomerId_Success() {
        List<OrderDTO> mockOrders = Collections.singletonList(new OrderDTO());
        when(orderService.getOrderHistoryByCustomerId(1L)).thenReturn(mockOrders);

        ResponseEntity<ApiResponse<List<OrderDTO>>> response = orderController.getOrderHistoryByCustomerId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertTrue(Objects.requireNonNull(response.getBody()).isSuccess());
        assertEquals("Order history retrieved successfully", response.getBody().getMessage());
        assertEquals(mockOrders, response.getBody().getData());

        // Verify service method was called
        verify(orderService, times(1)).getOrderHistoryByCustomerId(1L);
    }

    @Test
    void testGetOrderHistoryByCustomerId_InvalidCustomerId() {
        when(orderService.getOrderHistoryByCustomerId(0L)).thenThrow(new IllegalArgumentException("Invalid customer ID"));
        ResponseEntity<ApiResponse<List<OrderDTO>>> response = orderController.getOrderHistoryByCustomerId(0L);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).isSuccess());
        assertEquals("An unexpected error occurred while retrieving the order history", response.getBody().getMessage());
        verify(orderService, times(1)).getOrderHistoryByCustomerId(0L);
    }
    @Test
    void testGetOrderHistoryByCustomerId_OrderNotFound() {
        when(orderService.getOrderHistoryByCustomerId(2L)).thenThrow(new OrderNotFoundException("Order not found for customer ID: {}"));
        ResponseEntity<ApiResponse<List<OrderDTO>>> response = orderController.getOrderHistoryByCustomerId(2L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).isSuccess());
        assertEquals("Order not found for customer ID: {}", response.getBody().getMessage());
        verify(orderService, times(1)).getOrderHistoryByCustomerId(2L);
    }

    /**
     * Test method for the createOrder endpoint.
     * Verifies that the service method creates the order and returns the expected response.
     */
    @Test
    void testCreateOrder() throws InventoryNotFoundException {
        when(orderService.createOrder(orderDTO)).thenReturn(orderDTO);
        ResponseEntity<ApiResponse<OrderDTO>> response = orderController.createOrder(orderDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderDTO, Objects.requireNonNull(response.getBody()).getData());
        assertEquals("Order created successfully", response.getBody().getMessage());
        verify(orderService, times(1)).createOrder(orderDTO);
    }

    /**
     * Test method for the updateOrder endpoint.
     * Verifies that the service method updates the order and returns the expected response.
     */
    @Test
    void testUpdateOrder() {
        when(orderService.updateOrder(orderDTO, "278C5773AA16")).thenReturn(orderDTO);
        ResponseEntity<ApiResponse<OrderDTO>> response = orderController.updateOrder(orderDTO, "278C5773AA16");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDTO, Objects.requireNonNull(response.getBody()).getData());
        assertEquals("Order updated successfully", response.getBody().getMessage());
        verify(orderService, times(1)).updateOrder(orderDTO, "278C5773AA16");
    }

    /**
     * Test method for the cancelOrder endpoint.
     * Verifies that the service method cancels the order and returns the expected response.
     */
    @Test
    void testCancelOrder() throws InventoryNotFoundException, WarehouseNotFoundException {
        String message = "Order with ID 1 cancelled successfully";
        when(orderService.cancelOrder("278C5773AA16")).thenReturn(message);
        ResponseEntity<ApiResponse<String>> response = orderController.cancelOrder("278C5773AA16");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, Objects.requireNonNull(response.getBody()).getData());
        assertEquals("Order canceled successfully", response.getBody().getMessage());
        verify(orderService, times(1)).cancelOrder("278C5773AA16");
    }

    /**
     * Test method for the getEstimatedDeliveryDate endpoint.
     * Verifies that the service method returns the estimated delivery date for the given zipcode.
     */
    @Test
    void testGetEstimatedDeliveryDate() {
        String message = "Estimated delivery date for zipcode: 12345 is 2024-09-10";
        when(orderService.estimatedCompletionDeliveryDate(12345L)).thenReturn(message);
        ResponseEntity<ApiResponse<String>> response = orderController.getEstimatedDeliveryDate(12345L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, Objects.requireNonNull(response.getBody()).getData());
        assertEquals("Estimated delivery date retrieved successfully", response.getBody().getMessage());
        verify(orderService, times(1)).estimatedCompletionDeliveryDate(12345L);
    }

    /**
     * Test method for the trackOrderStatus endpoint.
     * Verifies that the service method returns the current status of the order.
     */
    @Test
    void testTrackOrderStatus() {
        String message = "Your order is on the way.";
        when(orderService.trackOrderStatus("278C5773AA16")).thenReturn(message);
        ResponseEntity<ApiResponse<String>> response = orderController.trackOrderStatusById("278C5773AA16");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(message, Objects.requireNonNull(response.getBody()).getData());
        assertEquals("Order status tracked successfully", response.getBody().getMessage());
        verify(orderService, times(1)).trackOrderStatus("278C5773AA16");
    }
}
