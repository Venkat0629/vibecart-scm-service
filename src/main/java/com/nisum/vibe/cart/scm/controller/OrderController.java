package com.nisum.vibe.cart.scm.controller;

import com.nisum.vibe.cart.scm.response.ApiResponse;
import com.nisum.vibe.cart.scm.exception.*;
import com.nisum.vibe.cart.scm.model.CustomerOrderItemDTO;
import com.nisum.vibe.cart.scm.model.OrderDTO;
import com.nisum.vibe.cart.scm.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller for managing orders in the Vibe Cart application.
 * Provides endpoints to handle CRUD operations and additional functionalities related to orders.
 */
@RestController
@RequestMapping("/api/v1/vibe-cart/scm/orders")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Retrieves a list of all orders.
     *
     * @return ResponseEntity with a list of OrderDTO.
     */
    @GetMapping("/getAllOrders")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getAllOrders() {
        try {
            List<OrderDTO> orders = orderService.getAllOrders();
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Orders retrieved successfully", orders);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (OrderRetrievalException e) {
            logger.error(e.getMessage());
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve.
     * @return A ResponseEntity containing the ApiResponse with order details or an error message.
     */
    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> getOrderById(@PathVariable String orderId) {
        try {
            OrderDTO orders = orderService.getOrderById(orderId);
            ApiResponse<OrderDTO> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Orders retrieved successfully", orders);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidOrderIdException e) {
            logger.error(e.getMessage());
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (OrderNotFoundException e) {
            logger.error(e.getMessage());
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves the order history for a specific customer.
     *
     * @param custId Customer ID.
     * @return ResponseEntity with a list of OrderDTO.
     */
    @GetMapping("/getOrderHistory/{custId}")
    public ResponseEntity<ApiResponse<List<OrderDTO>>> getOrderHistoryByCustomerId(@PathVariable Long custId) {
        try {
            List<OrderDTO> orderDTO = orderService.getOrderHistoryByCustomerId(custId);
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Order history retrieved successfully", orderDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidCustomerIdException e) {
            logger.error("Invalid customer ID: {}", e.getMessage());
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (OrderNotFoundException e) {
            logger.error("Order not found for customer ID: {}", e.getMessage());
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (OrderRetrievalException e) {
            logger.error("Error retrieving order history: {}", e.getMessage());
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving order history: {}", e.getMessage());
            ApiResponse<List<OrderDTO>> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred while retrieving the order history", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a new order.
     *
     * @param orderDTO Order data.
     * @return ResponseEntity with the created OrderDTO.
     */
    @PostMapping("/createOrder")
    public ResponseEntity<ApiResponse<OrderDTO>> createOrder(@RequestBody OrderDTO orderDTO) throws InventoryNotFoundException {
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            ApiResponse<OrderDTO> response = new ApiResponse<>(true, HttpStatus.CREATED.value(), "Order created successfully", createdOrder);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (InvalidOrderDataException e) {
            logger.error("Error creating order: {}", e.getMessage());
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (OrderSaveException e) {
            logger.error("Error saving order: {}", e.getMessage());
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates an existing order.
     *
     * @param orderDTO Order data.
     * @param orderId  Order ID.
     * @return ResponseEntity with the updated OrderDTO.
     */
    @PutMapping("/updateOrder/{orderId}")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable String orderId) {
        try {
            OrderDTO updatedOrder = orderService.updateOrder(orderDTO, orderId);
            ApiResponse<OrderDTO> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Order updated successfully", updatedOrder);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidOrderIdException e) {
            logger.error("Error updating order: {}", e.getMessage());
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (InvalidOrderDataException e) {
            logger.error("Invalid Order Data: {}", e.getMessage());
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (OrderNotFoundException e) {
            logger.error("Order not found for ID: {}", e.getMessage());
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (OrderUpdateException e) {
            logger.error("Error updating order: {}", e.getMessage());
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error while updating the order: {}", e.getMessage());
            ApiResponse<OrderDTO> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred while updating the order", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Cancels an existing order.
     *
     * @param orderId Order ID.
     * @return ResponseEntity with a cancellation message.
     */
    @DeleteMapping("/cancelOrder/{orderId}")
    public ResponseEntity<ApiResponse<String>> cancelOrder(@PathVariable String orderId) throws InventoryNotFoundException, WarehouseNotFoundException {
        try {
            String message = orderService.cancelOrder(orderId);
            ApiResponse<String> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Order canceled successfully", message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidOrderIdException e) {
            logger.error(e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (OrderNotFoundException e) {
            logger.error(e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        catch (OrderCancellationException e) {
            logger.error(e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves the estimated delivery date based on ZIP code.
     *
     * @param zipcode ZIP code.
     * @return ResponseEntity with the estimated delivery date.
     */
    @GetMapping("/getEstimateDeliveryDate/{zipcode}")
    public ResponseEntity<ApiResponse<String>> getEstimatedDeliveryDate(@PathVariable Long zipcode) {
        try {
            String message = orderService.estimatedCompletionDeliveryDate(zipcode);
            ApiResponse<String> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Estimated delivery date retrieved successfully", message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidZipCodeException e) {
            logger.error("Invalid ZIP code: {}", e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.BAD_REQUEST.value(), e.getMessage(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (DeliveryNotAvailableException e) {
            logger.error("Delivery not available for ZIP code: {}", e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), e.getMessage(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving estimated delivery date: {}", e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred while retrieving the estimated delivery date", "An unexpected error occurred");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Tracks the status of an order.
     *
     * @param orderId Order ID.
     * @return ResponseEntity with the order status.
     */
    @GetMapping("/trackOrderStatus/{orderId}")
    public ResponseEntity<ApiResponse<String>> trackOrderStatusById(@PathVariable String orderId) {
        try {
            String message = orderService.trackOrderStatus(orderId);
            ApiResponse<String> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Order status tracked successfully", message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidOrderIdException | OrderTrackingException e) {
            logger.error("Error tracking order status: {}", e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.NOT_FOUND.value(), e.getMessage(), e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Unexpected error while tracking the order status: {}", e.getMessage());
            ApiResponse<String> response = new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred while tracking the order status", "An unexpected error occurred");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Makes a stock reservation call for the given list of customer order items.
     * This method checks inventory availability and reserves stock based on the provided customer ZIP code.
     *
     * @param customerOrderItemDTOS List of customer order item details for which stock needs to be reserved.
     * @param customerZipcode       The ZIP code of the customer to determine the warehouse for stock reservation.
     * @return ResponseEntity containing an ApiResponse with a map of item IDs and their reservation status.
     */
    @PutMapping("/stock-reservation-call")
    public ResponseEntity<ApiResponse<Map<Long, String>>> stockReservationCall(@RequestBody List<CustomerOrderItemDTO> customerOrderItemDTOS, @RequestParam("customerZipcode") Long customerZipcode) throws InventoryNotFoundException, WarehouseNotFoundException {

        Map<Long, String> responseMap = orderService.stockReservationCall(customerOrderItemDTOS, customerZipcode);
        ApiResponse<Map<Long, String>> response = new ApiResponse<>(true, HttpStatus.OK.value(), "Stock Reservation call made", responseMap);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
