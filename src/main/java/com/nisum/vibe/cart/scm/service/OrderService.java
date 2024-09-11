package com.nisum.vibe.cart.scm.service;

import com.nisum.vibe.cart.scm.exception.InventoryNotFoundException;
import com.nisum.vibe.cart.scm.exception.WarehouseNotFoundException;
import com.nisum.vibe.cart.scm.model.CustomerOrderItemDto;
import com.nisum.vibe.cart.scm.model.OrderDTO;

import java.util.List;
import java.util.Map;

/**
 * Service interface for managing orders.
 * <p>
 * This interface defines the contract for order management operations including
 * creating, retrieving, updating, and canceling orders, as well as estimating
 * delivery dates and tracking order statuses and managing stock reservations.
 * </p>
 */
public interface OrderService {
    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(String orderId);

    List<OrderDTO> getOrderHistoryByCustomerId(Long custId);

    OrderDTO createOrder(OrderDTO orderDTO) throws InventoryNotFoundException;

    OrderDTO updateOrder(OrderDTO orderDTO, String orderId);

    String cancelOrder(String orderId) throws InventoryNotFoundException, WarehouseNotFoundException;

    String estimatedCompletionDeliveryDate(Long zipcode);

    String trackOrderStatus(String orderId);

    Map<Long, String> stockReservationCall(List<CustomerOrderItemDto> customerOrderItemDtos, Long customerZipcode) throws InventoryNotFoundException, WarehouseNotFoundException;
}