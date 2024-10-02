package com.nisum.vibe.cart.scm.mapper;

import com.nisum.vibe.cart.scm.dao.Address;
import com.nisum.vibe.cart.scm.dao.Customer;
import com.nisum.vibe.cart.scm.dao.Order;
import com.nisum.vibe.cart.scm.exception.OrderConversionException;
import com.nisum.vibe.cart.scm.exception.OrderNotFoundException;
import com.nisum.vibe.cart.scm.model.AddressDTO;
import com.nisum.vibe.cart.scm.model.CustomerDTO;
import com.nisum.vibe.cart.scm.model.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper class for converting between {@link Order} entities and {@link OrderDTO} data transfer objects.
 * <p>
 * This class provides methods to map between the {@link Order} entity and its corresponding
 * {@link OrderDTO}. It handles the conversion of fields and includes error handling for
 * cases where the conversion fails.
 * </p>
 */
@Component
public class OrderMapper {

    private static final Logger logger = LoggerFactory.getLogger(OrderMapper.class);

    /**
     * Converts an Order entity to an OrderDTO.
     *
     * @param order the Order entity
     * @return the OrderDTO
     */
    public static OrderDTO convertEntitytoDTO(Order order) {
        if (order == null) {
            logger.error("Order cannot be null");
            throw new OrderNotFoundException("Order cannot be null");
        }
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO.setOrderId(order.getOrderId());
            orderDTO.setOrderDate(order.getOrderDate());
            orderDTO.setCreatedDate(order.getCreatedDate());
            orderDTO.setUpdatedDate(order.getUpdatedDate());
            orderDTO.setTotalAmount(order.getTotalAmount());
            orderDTO.setSubTotal(order.getSubTotal());
            orderDTO.setDiscountPrice(order.getDiscountPrice());
            orderDTO.setOfferId(order.getOfferId());
            orderDTO.setTotalQuantity(order.getTotalQuantity());
            orderDTO.setShippingzipCode(order.getShippingzipCode());
            orderDTO.setEstimated_delivery_date(order.getEstimated_delivery_date());
            orderDTO.setOrderStatus(order.getOrderStatus());
            orderDTO.setPaymentStatus(order.getPaymentStatus());
            orderDTO.setPaymentMethod(order.getPaymentMethod());

            if (order.getOrderItems() != null)
                orderDTO.setOrderItems(order.getOrderItems().stream().map(OrderItemMapper::convertEntitytoDTO).collect(Collectors.toList()));


            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerName(order.getCustomer().getCustomerName());
            customerDTO.setEmail(order.getCustomer().getEmail());
            customerDTO.setPhoneNumber(order.getCustomer().getPhoneNumber());

            orderDTO.setCustomer(customerDTO);

            AddressDTO shippingAddressDTO = new AddressDTO();
            shippingAddressDTO.setName(order.getShippingAddress().getName());
            shippingAddressDTO.setEmail(order.getShippingAddress().getEmail());
            shippingAddressDTO.setPhoneNumber(order.getShippingAddress().getPhoneNumber());
            shippingAddressDTO.setAddress(order.getShippingAddress().getAddress());
            shippingAddressDTO.setCity(order.getShippingAddress().getCity());
            shippingAddressDTO.setState(order.getShippingAddress().getState());
            shippingAddressDTO.setZipcode(order.getShippingAddress().getZipcode());

            AddressDTO billingAddressDTO = new AddressDTO();
            billingAddressDTO.setName(order.getShippingAddress().getName());
            billingAddressDTO.setEmail(order.getShippingAddress().getEmail());
            billingAddressDTO.setPhoneNumber(order.getShippingAddress().getPhoneNumber());
            billingAddressDTO.setAddress(order.getBillingAddress().getAddress());
            billingAddressDTO.setCity(order.getBillingAddress().getCity());
            billingAddressDTO.setState(order.getBillingAddress().getState());
            billingAddressDTO.setZipcode(order.getBillingAddress().getZipcode());

            orderDTO.setShippingAddress(shippingAddressDTO);
            orderDTO.setBillingAddress(billingAddressDTO);
        } catch (Exception e) {
            logger.error("Failed to convert Order entity to DTO", e);
            throw new OrderConversionException("Failed to convert Order entity to DTO", e);
        }
        logger.info("Successfully converted Order entity to DTO");
        return orderDTO;
    }

    /**
     * Converts an OrderDTO to an Order entity.
     *
     * @param orderDTO the OrderDTO
     * @return the Order entity
     */
    public static Order convertDTOtoEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            logger.error("OrderDTO cannot be null");
            throw new OrderNotFoundException("OrderDTO cannot be null");
        }

        Order order = new Order();
        try {
            order.setOrderId(orderDTO.getOrderId());
            order.setOrderDate(orderDTO.getOrderDate());
            order.setCreatedDate(orderDTO.getCreatedDate());
            order.setUpdatedDate(orderDTO.getUpdatedDate());
            order.setTotalAmount(orderDTO.getTotalAmount());
            order.setSubTotal(orderDTO.getSubTotal());
            order.setDiscountPrice(orderDTO.getDiscountPrice());
            order.setOfferId(orderDTO.getOfferId());
            order.setTotalQuantity(orderDTO.getTotalQuantity());
            order.setEstimated_delivery_date(orderDTO.getEstimated_delivery_date());
            order.setOrderStatus(orderDTO.getOrderStatus());
            order.setShippingzipCode(orderDTO.getShippingzipCode());
            order.setPaymentStatus(orderDTO.getPaymentStatus());
            order.setPaymentMethod(orderDTO.getPaymentMethod());

            if (orderDTO.getOrderItems() != null) {
                order.setOrderItems(orderDTO.getOrderItems().stream().map(OrderItemMapper::convertDTOtoEntity).collect(Collectors.toList()));
            }

            Customer customer = new Customer();
            customer.setCustomerName(orderDTO.getCustomer().getCustomerName());
            customer.setEmail(orderDTO.getCustomer().getEmail());
            customer.setPhoneNumber(orderDTO.getCustomer().getPhoneNumber());

            order.setCustomer(customer);

            Address shippingAddress = new Address();
            shippingAddress.setName(orderDTO.getShippingAddress().getName());
            shippingAddress.setEmail(orderDTO.getShippingAddress().getEmail());
            shippingAddress.setPhoneNumber(orderDTO.getShippingAddress().getPhoneNumber());
            shippingAddress.setAddress(orderDTO.getShippingAddress().getAddress());
            shippingAddress.setCity(orderDTO.getShippingAddress().getCity());
            shippingAddress.setState(orderDTO.getShippingAddress().getState());
            shippingAddress.setZipcode(orderDTO.getShippingAddress().getZipcode());

            Address billingAddress = new Address();
            billingAddress.setName(orderDTO.getShippingAddress().getName());
            billingAddress.setEmail(orderDTO.getShippingAddress().getEmail());
            billingAddress.setPhoneNumber(orderDTO.getShippingAddress().getPhoneNumber());
            billingAddress.setAddress(orderDTO.getBillingAddress().getAddress());
            billingAddress.setCity(orderDTO.getBillingAddress().getCity());
            billingAddress.setState(orderDTO.getBillingAddress().getState());
            billingAddress.setZipcode(orderDTO.getBillingAddress().getZipcode());

            order.setShippingAddress(shippingAddress);
            order.setBillingAddress(billingAddress);
        } catch (Exception e) {
            logger.error("Failed to convert Order DTO to entity", e);
            throw new OrderConversionException("Failed to convert Order DTO to entity", e);
        }
        logger.info("Successfully converted Order DTO to entity");
        return order;
    }
}

