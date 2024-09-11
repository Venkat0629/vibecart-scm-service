package com.nisum.vibe.cart.scm.service;

import com.nisum.vibe.cart.scm.entity.Address;
import com.nisum.vibe.cart.scm.entity.Customer;
import com.nisum.vibe.cart.scm.entity.Order;
import com.nisum.vibe.cart.scm.entity.OrderItem;
import com.nisum.vibe.cart.scm.exception.*;
import com.nisum.vibe.cart.scm.mapper.OrderMapper;
import com.nisum.vibe.cart.scm.model.*;
import com.nisum.vibe.cart.scm.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplementationTest {

    @Mock
    private OrderRepository orderRepository;

    @Spy
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImplementation orderService;

    private Order order;
    private OrderDTO orderDTO;
    private AddressDTO addressDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setOrderId("278C5773AA16");
        order.setCustomer(new Customer(1L));
        order.setTotalQuantity(1);
        order.setTotalAmount(1000.00);
        order.setOrderDate(Instant.now());
        order.setCreatedDate(Instant.now());
        order.setEstimated_delivery_date(LocalDateTime.now().plusDays(5));
        order.setShippingAddress(new Address("john", "john@gmail.com","45125412154","tolichoki","hyd","ta","62703L"));
        order.setBillingAddress(new Address("john", "john@gmail.com","45125412154","tolichoki","hyd","ta","62703L"));
        order.setShippingzipCode(62701L);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setPaymentStatus(PaymentStatus.PENDING);
        OrderItem item1 = new OrderItem();
        item1.setItemId(1001L);
        item1.setItemName("Product 1");
        item1.setQuantity(2);
        item1.setPrice(new BigDecimal("50.00")); // Setting BigDecimal value

        OrderItem item2 = new OrderItem();
        item2.setItemId(1002L);
        item2.setItemName("Product 2");
        item2.setQuantity(1);
        item2.setPrice(new BigDecimal("30.00")); // Setting BigDecimal value
        List<OrderItem> Items = Arrays.asList(item1, item2);
        order.setOrderItems(Items);

        orderDTO = new OrderDTO();
        orderDTO.setOrderId("278C5773AA16");
        orderDTO.setTotalQuantity(2);
        orderDTO.setTotalAmount(1000.00);
        orderDTO.setOrderDate(Instant.now());
        orderDTO.setCreatedDate(Instant.now());
        orderDTO.setEstimated_delivery_date(LocalDateTime.now().plusDays(3));
        orderDTO.setShippingAddress(new AddressDTO("john", "john@gmail.com","45125412154","tolichoki","hyd","ta","62703L"));
        orderDTO.setBillingAddress(new AddressDTO("john", "john@gmail.com","45125412154","tolichoki","hyd","ta","62703L"));
        orderDTO.setShippingzipCode(62701L);
        orderDTO.setOrderStatus(OrderStatus.CONFIRMED);
        orderDTO.setPaymentStatus(PaymentStatus.COMPLETED);
        OrderItemDTO item3 = new OrderItemDTO();
        item1.setItemId(1001L);
        item1.setItemName("Product 1");
        item1.setQuantity(2);
        item1.setPrice(new BigDecimal("50.00")); // Setting BigDecimal value

        OrderItemDTO item4 = new OrderItemDTO();
        item2.setItemId(1002L);
        item2.setItemName("Product 2");
        item2.setQuantity(1);
        item2.setPrice(new BigDecimal("30.00")); // Setting BigDecimal value
        List<OrderItemDTO> ItemsDTO = Arrays.asList(item3, item4);
        orderDTO.setOrderItems(ItemsDTO);
    }

    @Test
    void getAllOrders_ShouldReturnOrderList_WhenOrdersExist() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<OrderDTO> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals(orderDTO.getOrderId(), result.get(0).getOrderId());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void getOrderHistoryByCustomerId_ShouldReturnOrderList_WhenOrdersExist() {
        Long customerId = 1L;
        when(orderRepository.findByCustomerCustomerId(customerId)).thenReturn(Arrays.asList(order));

        List<OrderDTO> result = orderService.getOrderHistoryByCustomerId(customerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findByCustomerCustomerId(customerId);
    }

    @Test
    void getOrderHistoryByCustomerId_ShouldThrowException_WhenOrdersNotFound() {
        Long customerId = 1L;
        when(orderRepository.findByCustomerCustomerId(customerId)).thenReturn(Arrays.asList());

        assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrderHistoryByCustomerId(customerId);
        });

        verify(orderRepository, times(1)).findByCustomerCustomerId(customerId);
    }

    @Test
    void cancelOrder_ShouldThrowException_WhenOrderIsAlreadyCancelled() {
        order.setOrderStatus(OrderStatus.CANCELLED);
        String orderId = "278C5773AA16";
        when(orderRepository.findById((orderId))).thenReturn(Optional.of(order));

        assertThrows(OrderCancellationException.class, () -> {
            orderService.cancelOrder(orderId);
        });

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void estimatedCompletionDeliveryDate_ShouldReturnEstimatedDate_WhenZipCodeIsValid() {
        Long zipcode = 12345L;
        when(orderRepository.findByShippingZipCode(zipcode)).thenReturn(Optional.of(order));

        String result = orderService.estimatedCompletionDeliveryDate(zipcode);

        assertTrue(result.contains("Estimated delivery date"));
        verify(orderRepository, times(1)).findByShippingZipCode(zipcode);
    }
}










