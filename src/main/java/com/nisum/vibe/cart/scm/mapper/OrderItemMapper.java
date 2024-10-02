package com.nisum.vibe.cart.scm.mapper;


import com.nisum.vibe.cart.scm.dao.OrderItem;
import com.nisum.vibe.cart.scm.exception.OrderItemConversionException;
import com.nisum.vibe.cart.scm.exception.OrderNotFoundException;
import com.nisum.vibe.cart.scm.model.OrderItemDTO;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between {@link OrderItem} entities and {@link OrderItemDTO} data transfer objects.
 * <p>
 * This class provides methods to map between the {@link OrderItem} entity and its corresponding
 * {@link OrderItemDTO}. It handles the conversion of fields and includes error handling for
 * cases where the conversion fails.
 * </p>
 */
@Component
public class OrderItemMapper {

    /**
     * Converts an OrderItem entity to an OrderItemDTO.
     *
     * @param orderItem the Order entity
     * @return the OrderItemDTO
     */
    public static OrderItemDTO convertEntitytoDTO(OrderItem orderItem) {
        if (orderItem == null) {
            throw new OrderNotFoundException("Order cannot be null");
        }
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        try {
            orderItemDTO.setOrderItemId(orderItem.getOrderItemId());
            orderItemDTO.setItemId(orderItem.getItemId());
            orderItemDTO.setSkuId(orderItem.getSkuId());
            orderItemDTO.setItemName(orderItem.getItemName());
            orderItemDTO.setCategory(orderItem.getCategory());
            orderItemDTO.setSelectedSize(orderItem.getSelectedSize());
            orderItemDTO.setSelectedColor(orderItem.getSelectedColor());
            orderItemDTO.setQuantity(orderItem.getQuantity());
            orderItemDTO.setPrice(orderItem.getPrice());
            orderItemDTO.setTotalPrice(orderItem.getTotalPrice());
        } catch (Exception e) {
            throw new OrderItemConversionException("Failed to convert OrderItem entity to DTO", e);
        }

        return orderItemDTO;
    }

    /**
     * Converts an OrderItemDTO entity to an OrderItem.
     *
     * @param orderItemDTO the Order entity
     * @return the OrderItem
     */
    public static OrderItem convertDTOtoEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        try {
            orderItem.setOrderItemId(orderItemDTO.getOrderItemId());
            orderItem.setItemId(orderItemDTO.getItemId());
            orderItem.setSkuId(orderItemDTO.getSkuId());
            orderItem.setItemName(orderItemDTO.getItemName());
            orderItem.setCategory(orderItemDTO.getCategory());
            orderItem.setSelectedSize(orderItemDTO.getSelectedSize());
            orderItem.setSelectedColor(orderItemDTO.getSelectedColor());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(orderItemDTO.getPrice());
            orderItem.setTotalPrice(orderItemDTO.getTotalPrice());
        } catch (Exception e) {
            throw new OrderItemConversionException("Failed to convert OrderItem DTO to entity", e);
        }

        return orderItem;
    }
}

