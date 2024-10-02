package com.nisum.vibe.cart.scm.service;

import com.nisum.vibe.cart.scm.Validation.OrderValidator;
import com.nisum.vibe.cart.scm.dao.Address;
import com.nisum.vibe.cart.scm.dao.Order;
import com.nisum.vibe.cart.scm.dao.OrderItem;
import com.nisum.vibe.cart.scm.exception.*;
import com.nisum.vibe.cart.scm.mapper.OrderItemMapper;
import com.nisum.vibe.cart.scm.mapper.OrderMapper;
import com.nisum.vibe.cart.scm.model.*;
import com.nisum.vibe.cart.scm.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.nisum.vibe.cart.scm.model.UUIDGenerator.generateUUID;

/**
 * Implementation of the {@link OrderService} interface that handles order management operations.
 * <p>
 * This service class provides business logic for creating, updating, retrieving, cancelling,
 * and tracking orders. It interacts with the {@link OrderRepository}
 * to manage order entities and ensures data integrity through validation and exception handling.
 * </p>
 */
@Service
public class OrderServiceImplementation implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImplementation.class);

    @Value("${ofms.service.api.url}")
    private String OFFER_SERVICE_URL;

    private OrderRepository orderRepository;
    private OrderMapper orderMapper;
    private InventoryService inventoryService;
    private RestTemplate restTemplate;

    @Autowired
    public OrderServiceImplementation(OrderRepository orderRepository, OrderMapper orderMapper, InventoryService inventoryService, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.inventoryService = inventoryService;
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves a list of all orders.
     * Logs the retrieval attempt and handles exceptions if retrieval fails.
     * Returns a list of {@link OrderDTO} objects.
     */
    @Override
    public List<OrderDTO> getAllOrders() {
        logger.info("Attempting to retrieve all orders.");
        try {
            List<OrderDTO> orderList = orderRepository.findAll().stream().map(OrderMapper::convertEntitytoDTO).collect(Collectors.toList());

            logger.info("Successfully retrieved {} orders.", orderList.size());
            return orderList;
        } catch (Exception e) {
            logger.error("Failed to retrieve orders", e);
            throw new OrderRetrievalException("Failed to retrieve orders", e);
        }
    }

    @Override
    public OrderDTO getOrderById(String orderId) {
        logger.info("Attempting to retrieve order by ID: {}", orderId);

        if (orderId == null || orderId.trim().isEmpty()) {
            String errorMessage = "Invalid or null order ID: " + orderId;
            logger.error(errorMessage);
            throw new InvalidOrderIdException(errorMessage);
        }

        try {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> {
                String errorMessage = "Order with ID " + orderId + " does not exist";
                logger.error(errorMessage);
                return new OrderNotFoundException(errorMessage);
            });

            logger.info("Successfully retrieved order with ID: {}", orderId);

            return OrderMapper.convertEntitytoDTO(order);
        } catch (OrderNotFoundException e) {
            throw e;
        } catch (InvalidOrderIdException e) {
            throw e;
        }
    }

    /**
     * Retrieves order history for a specific customer.
     * Validates the customer ID and logs the retrieval process.
     * Returns a list of {@link OrderDTO} for the specified customer ID.
     */
    @Override
    public List<OrderDTO> getOrderHistoryByCustomerId(Long custId) {
        logger.info("Retrieving order history for customer with ID: {}", custId);

        if (custId == null || custId <= 0) {
            String errorMessage = "Invalid or null customer ID: " + custId;
            logger.error(errorMessage);
            throw new InvalidOrderIdException(errorMessage);
        }
        try {
            List<Order> orderHistory = orderRepository.findByCustomerCustomerId(custId);

            if (orderHistory.isEmpty()) {
                String warningMessage = "No orders found for customer with ID: " + custId;
                logger.warn(warningMessage);
                throw new OrderNotFoundException(warningMessage);
            }

            List<OrderDTO> orderDTOs = orderHistory.stream().map(OrderMapper::convertEntitytoDTO).collect(Collectors.toList());

            logger.info("Successfully retrieved {} orders for customer with ID: {}", orderDTOs.size(), custId);
            return orderDTOs;
        } catch (OrderRetrievalException e) {
            String errorMessage = "Failed to retrieve orders for customer with ID: " + custId;
            logger.error(errorMessage, e);
            throw new OrderRetrievalException(errorMessage, e);
        }
    }

    /**
     * Creates a new order from the provided {@link OrderDTO}.
     * Validates the order data, sets necessary timestamps, and saves the order.
     * Returns the created {@link OrderDTO}.
     */
    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) throws InventoryNotFoundException {
        logger.info("Creating order with details: {}", orderDTO);
        if (orderDTO == null) {
            String errorMessage = "OrderDTO must not be null";
            logger.error(errorMessage);
            throw new InvalidOrderDataException(errorMessage);
        }

        try {
            Order order = OrderMapper.convertDTOtoEntity(orderDTO);
            order.setOrderId(generateUUID());
            order.setCreatedDate(Instant.now());
            order.setUpdatedDate(Instant.now());
            order.setOrderDate(Instant.now());

            if (order.getOrderItems() != null) {
                for (OrderItem orderItem : order.getOrderItems()) {
                    orderItem.setOrder(order);
                }
            }
            order = orderRepository.save(order);

            if (order.getOrderId() != null && order.getOfferId() != null) {
                updateOfferUsage(order);
            }

            logger.info("Order successfully created with ID: {}", order.getOrderId());

            List<Long> skuList = order.getOrderItems().stream().map(OrderItem::getSkuId).collect(Collectors.toList());
            inventoryService.confirmStockReservation(skuList);

            return OrderMapper.convertEntitytoDTO(order);
        } catch (InvalidOrderDataException e) {
            throw e;
        } catch (InventoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Failed to retrieve orders", e);
            throw new OrderSaveException("Failed to retrieve orders", e);
        }

    }

    /**
     * Updates an existing order with the given {@link OrderDTO} and order ID.
     * Fetches the existing order, applies updates, and saves it.
     * Returns the updated {@link OrderDTO}.
     */
    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, String orderId) {
        logger.info("Attempting to update order with ID: {}", orderId);

        if (orderId == null || orderId.trim().isEmpty()) {
            logger.error("Invalid or null order ID: {}", orderId);
            throw new InvalidOrderIdException("Invalid or null order ID: " + orderId);
        }
        if (orderDTO == null) {
            logger.error("OrderDTO cannot be null");
            throw new InvalidOrderDataException("OrderDTO cannot be null");
        }

        try {
            // Fetch the existing order
            Optional<Order> existingOrder = orderRepository.findById(orderId);
            if (!existingOrder.isPresent()) {
                logger.error("Order with ID: {} not found", orderId);
                throw new OrderNotFoundException("Order id : " + orderId + " does not exist");
            }
            Order order = existingOrder.get();
            order.setUpdatedDate(Instant.now());

            // Update fields of the existing order
            if (orderDTO.getOrderDate() != null) {
                logger.debug("Updating orderDate to: {}", orderDTO.getOrderDate());
                order.setOrderDate(orderDTO.getOrderDate());
            }
            if (orderDTO.getTotalAmount() > 0) {
                logger.debug("Updating totalAmount to: {}", orderDTO.getTotalAmount());
                order.setTotalAmount(orderDTO.getTotalAmount());
            }
            if (orderDTO.getCreatedDate() != null) {
                logger.debug("Updating createdDate to: {}", orderDTO.getCreatedDate());
                order.setCreatedDate(orderDTO.getCreatedDate());
            }
            if (orderDTO.getTotalQuantity() > 0) {
                logger.debug("Updating totalQuantity to: {}", orderDTO.getTotalQuantity());
                order.setTotalQuantity(orderDTO.getTotalQuantity());
            }
            if (orderDTO.getEstimated_delivery_date() != null) {
                logger.debug("Updating estimated_delivery_date to: {}", orderDTO.getEstimated_delivery_date());
                order.setEstimated_delivery_date(orderDTO.getEstimated_delivery_date());
            }
            if (orderDTO.getShippingzipCode() != null) {
                logger.debug("Updating shippingzipCode to: {}", orderDTO.getShippingzipCode());
                order.setShippingzipCode(orderDTO.getShippingzipCode());
            }
            if (orderDTO.getShippingAddress() != null) {
                logger.debug("Updating shippingAddress to: {}", orderDTO.getShippingAddress());
                Address shippingAddress = new Address();
                shippingAddress.setAddress(orderDTO.getShippingAddress().getAddress());
                shippingAddress.setCity(orderDTO.getShippingAddress().getCity());
                shippingAddress.setState(orderDTO.getShippingAddress().getState());
                shippingAddress.setZipcode(orderDTO.getShippingAddress().getZipcode());

                order.setShippingAddress(shippingAddress);
            }

            if (orderDTO.getBillingAddress() != null) {
                logger.debug("Updating billingAddress to: {}", orderDTO.getBillingAddress());
                Address billingAddress = new Address();
                billingAddress.setAddress(orderDTO.getBillingAddress().getAddress());
                billingAddress.setCity(orderDTO.getBillingAddress().getCity());
                billingAddress.setState(orderDTO.getBillingAddress().getState());
                billingAddress.setZipcode(orderDTO.getBillingAddress().getZipcode());

                order.setBillingAddress(billingAddress);
            }
            if (orderDTO.getOrderStatus() != null) {
                logger.debug("Updating orderStatus to: {}", orderDTO.getOrderStatus());
                if (orderDTO.getOrderStatus() == OrderStatus.CANCELLED) {
                    String warningMsg = "Order ID: " + orderId + " is already cancelled.";
                    logger.warn(warningMsg);
                    throw new OrderCancellationException(warningMsg);
                }
                if (orderDTO.getOrderStatus() == OrderStatus.COMPLETED) {
                    String warningMsg = "Order ID: " + orderId + " is already completed and cannot be cancelled.";
                    logger.warn(warningMsg);
                    throw new OrderCancellationException(warningMsg);
                }
                order.setOrderStatus(orderDTO.getOrderStatus());
                if (orderDTO.getOrderStatus() == OrderStatus.COMPLETED) {
                    order.setPaymentStatus(PaymentStatus.COMPLETED);
                } else {
                    order.setPaymentStatus(PaymentStatus.PENDING);
                }
            }
            if (orderDTO.getPaymentMethod() != null) {
                logger.debug("Updating paymentMethod to: {}", orderDTO.getPaymentMethod());
                order.setPaymentMethod(orderDTO.getPaymentMethod());
            }
            if (orderDTO.getOrderItems() != null) {
                logger.debug("Updating orderItems to: {}", order.getOrderItems());
                order.setOrderItems(orderDTO.getOrderItems().stream().map(OrderItemMapper::convertDTOtoEntity).collect(Collectors.toList()));
            }

            OrderDTO validateOrder = OrderMapper.convertEntitytoDTO(order);
            // Validate input
            OrderValidator.validateOrder(validateOrder);

            // Save updated order
            Order updatedOrder = orderRepository.save(order);

            logger.info("Order with ID: {} updated successfully", orderId);

            return OrderMapper.convertEntitytoDTO(updatedOrder);
        } catch (OrderNotFoundException e) {
            logger.error("OrderNotFoundException occurred: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("OrderUpdateException occurred: {}", e.getMessage(), e);
            throw new OrderUpdateException("Failed to update this order with ID: " + orderId, e);
        }
    }

    /**
     * Cancels an order with the given ID.
     * Validates the order status and performs the cancellation.
     * Returns a success message upon successful cancellation.
     */
    @Override
    public String cancelOrder(String orderId) throws InventoryNotFoundException, WarehouseNotFoundException {
        logger.info("Attempting to cancel order with ID: {}", orderId);

        if (orderId == null || orderId.trim().isEmpty()) {
            String errorMessage = "Invalid or null order ID: " + orderId;
            logger.error(errorMessage);
            throw new InvalidOrderIdException(errorMessage);
        }

        try {
            // Check if the order exists
            Order existingOrder = orderRepository.findById(orderId).orElseThrow(() -> {
                String errorMsg = "Order ID: " + orderId + " does not exist";
                logger.error(errorMsg);
                return new OrderNotFoundException(errorMsg);
            });

            // Check if the order is already cancelled or completed
            if (existingOrder.getOrderStatus() == OrderStatus.CANCELLED) {
                String warningMsg = "Order ID: " + orderId + " is already cancelled.";
                logger.warn(warningMsg);
                throw new OrderCancellationException(warningMsg);
            }
            if (existingOrder.getOrderStatus() == OrderStatus.COMPLETED) {
                String warningMsg = "Order ID: " + orderId + " is already completed and cannot be cancelled.";
                logger.warn(warningMsg);
                throw new OrderCancellationException(warningMsg);
            }

            // Allow cancellation only if the order is in CONFIRMED status
            if (existingOrder.getOrderStatus() == OrderStatus.CONFIRMED || existingOrder.getOrderStatus() == OrderStatus.DISPATCHED) {
                existingOrder.setOrderStatus(OrderStatus.CANCELLED);
                existingOrder.setUpdatedDate(Instant.now());
                orderRepository.save(existingOrder);

                List<CustomerOrderItemDTO> customerOrderItemDTOS = existingOrder.getOrderItems().stream().map(orderItem -> new CustomerOrderItemDTO(orderItem.getSkuId(), orderItem.getQuantity())).collect(Collectors.toList());

                Long customerZipcode = existingOrder.getShippingzipCode();

                inventoryService.revertStockIfOrderCancel(customerOrderItemDTOS, customerZipcode);

                String successMessage = "Order with ID " + orderId + " cancelled successfully.";
                logger.info(successMessage);
                return successMessage;
            } else {
                String errMessage = "Order with ID " + orderId + " has been shipped or is in a non-cancellable state.";
                logger.warn(errMessage);
                throw new OrderCancellationException(errMessage);
            }
        } catch (InvalidOrderIdException e) {
            throw e;
        } catch (OrderNotFoundException e) {
            throw e;
        } catch (OrderCancellationException e) {
            throw e;
        } catch (InventoryNotFoundException e) {
            throw e;
        } catch (WarehouseNotFoundException e) {
            throw e;
        }
    }

    /**
     * Estimates the delivery date based on the provided ZIP code.
     * Validates the ZIP code and retrieves the estimated delivery date.
     * Returns a message with the estimated delivery date or an error if not available.
     */
    @Override
    public String estimatedCompletionDeliveryDate(Long zipcode) {
        logger.info("Request received to estimate delivery date for ZIP code: {}", zipcode);

        if (zipcode <= 0) {
            String errorMessage = "Invalid or non-existent ZIP code: " + zipcode;
            logger.error(errorMessage);
            throw new InvalidZipCodeException(errorMessage);
        }

        try {
            Optional<Order> optionalOrder = orderRepository.findByShippingZipCode(zipcode);

            if (optionalOrder.isPresent()) {
                LocalDateTime estimatedDate = optionalOrder.get().getEstimated_delivery_date();
                String successMessage = "Estimated delivery date for ZIP code " + zipcode + " is " + estimatedDate + ".";
                logger.info(successMessage);
                return successMessage;
            } else {
                String errorMessage = "Delivery is not available for the provided ZIP code: " + zipcode;
                logger.warn(errorMessage);
                throw new DeliveryNotAvailableException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = "An unexpected error occurred while estimating delivery date for ZIP code: " + zipcode;
            logger.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * Tracks the status of an order based on the provided order ID.
     * Retrieves the status of the order and returns a descriptive message.
     * Returns a message about the current status or an error if the order is not found.
     */
    @Override
    public String trackOrderStatus(String orderId) {
        logger.info("Tracking status for order with ID: {}", orderId);

        if (orderId == null || orderId.trim().isEmpty()) {
            String errorMessage = "Invalid or null order ID: " + orderId;
            logger.error(errorMessage);
            throw new InvalidOrderIdException(errorMessage);
        }

        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);

            if (optionalOrder.isPresent()) {
                OrderStatus status = optionalOrder.get().getOrderStatus();
                String statusMessage;

                switch (status) {
                    case SHIPPED:
                        statusMessage = "Your order has been shipped from logistics.";
                        break;
                    case CONFIRMED:
                        statusMessage = "Your order has been confirmed and is being prepared.";
                        break;
                    case DISPATCHED:
                        statusMessage = "Your order has been dispatched and is on its way to the courier.";
                        break;
                    case PICKUP_COURIER:
                        statusMessage = "Your order is with the courier for pickup.";
                        break;
                    case ON_THE_WAY:
                        statusMessage = "Your order is on the way.";
                        break;
                    case OUT_FOR_DELIVERY:
                        statusMessage = "Your order is out for delivery.";
                        break;
                    case DELIVERED:
                        statusMessage = "Your order has been delivered.";
                        break;
                    case CANCELLED:
                        statusMessage = "Your order has been canceled.";
                        break;
                    default:
                        statusMessage = "Your order is in an undefined state.";
                }

                logger.info("Order ID: {} - Status: {}", orderId, status);
                return statusMessage;
            } else {
                String errorMessage = "Order status is undefined for order ID: " + orderId;
                logger.error(errorMessage);
                throw new OrderTrackingException(errorMessage);
            }
        } catch (Exception e) {
            String errorMessage = "An unexpected error occurred while tracking the order with ID: " + orderId;
            logger.error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * Reserves stock for a list of customer order items based on the customer's ZIP code.
     * Delegates the stock reservation logic to the inventory service.
     *
     * @param customerOrderItemDTOS List of customer order items to reserve.
     * @param customerZipcode       The customer's ZIP code used to find the appropriate warehouse.
     * @return A map containing item IDs and their reservation status.
     */
    @Override
    public Map<Long, String> stockReservationCall(List<CustomerOrderItemDTO> customerOrderItemDTOS, Long customerZipcode) throws InventoryNotFoundException, WarehouseNotFoundException {

        return inventoryService.stockReservationCall(customerOrderItemDTOS, customerZipcode);
    }

    public void updateOfferUsage(Order order) {
        try {
            logger.info("Offer external service call");
            OfferUsageDTO offerUsageDTO = new OfferUsageDTO();
            offerUsageDTO.setOfferId(order.getOfferId());
            offerUsageDTO.setOrderId(order.getOrderId());
            offerUsageDTO.setEmail(order.getCustomer().getEmail());
            offerUsageDTO.setCustomerId(1111L);
            HttpEntity<OfferUsageDTO> requestEntity = new HttpEntity<>(offerUsageDTO);

            ResponseEntity<OfferUsageDTO> response = restTemplate.exchange(OFFER_SERVICE_URL + order.getOfferId(), HttpMethod.PUT, requestEntity, new ParameterizedTypeReference<OfferUsageDTO>() {
            });

            logger.info("Offer external service completed {}", Objects.requireNonNull(response.getBody()).getOfferUsageId());

        } catch (RestClientException e) {
            logger.error("Offer related error");
        }
    }
}



