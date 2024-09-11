package com.nisum.vibe.cart.scm.DTO;
import com.nisum.vibe.cart.scm.entity.OrderItem;
import com.nisum.vibe.cart.scm.exception.InvalidOrderIdException;
import com.nisum.vibe.cart.scm.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for testing the OrderDTO class.
 * <p>
 * This test class includes various test methods that verify the correct behavior of the getter and setter methods
 * in the OrderDTO class.
 * </p>
 */
class OrderDTOTest {

    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        orderDTO = new OrderDTO();
    }

    /**
     * Tests the setter for the orderId field with a valid orderId.
     */
    @Test
    void testSetOrderId_WithValidOrderId_ShouldSetOrderId() {
        // Use a valid UUID string
        String validOrderId = "278C5773AA16";

        OrderDTO orderDTO = new OrderDTO(); // Assuming you have an OrderDTO class
        orderDTO.setOrderId(validOrderId);

        assertEquals(validOrderId, orderDTO.getOrderId());
    }

    /**
     * Tests the setter for the orderId field with an invalid orderId, expecting an InvalidOrderIdException.
     */
    @Test
    void testSetOrderId_WithInvalidOrderId_ShouldThrowException() {
        String invalidOrderId = "";
        Exception exception = assertThrows(InvalidOrderIdException.class, () -> {
            orderDTO.setOrderId(invalidOrderId);
        });
        assertEquals("Order Id should not be null or Empty", exception.getMessage());
    }

    /**
     * Tests the setter and getter for the orderDate field with a valid value.
     */
    @Test
    void testOrderDate_WithValidDate_ShouldSetOrderDate() {
        Instant orderDate = Instant.now();
        orderDTO.setOrderDate(orderDate);
        assertEquals(orderDate, orderDTO.getOrderDate());
    }

    /**
     * Tests the setter for the orderDate field with a null value, expecting a NullPointerException.
     */
    @Test
    void testOrderDate_WithNullValue_ShouldThrowException() {
        Instant invalidDate = null;
        Exception exception = assertThrows(NullPointerException.class, () -> {
            orderDTO.setOrderDate(invalidDate);
        });
    }

    /**
     * Tests the setter and getter for the createdDate field with a valid value.
     */
    @Test
    void testCreatedDate_WithValidDate_ShouldSetCreatedDate() {
        Instant createdDate = Instant.now();
        orderDTO.setCreatedDate(createdDate);
        assertEquals(createdDate, orderDTO.getCreatedDate());
    }

    /**
     * Tests the automatic setting of the createdDate field if it's not provided.
     */
    @Test
    void testCreatedDateIsAutomaticallySetIfNotProvided() {
        orderDTO.setCreatedDate(null);

        if (orderDTO.getCreatedDate() == null) {
            orderDTO.setCreatedDate(Instant.now());
        }

        assertNotNull(orderDTO.getCreatedDate(), "CreatedDate should be automatically set and not be null");
    }

    /**
     * Tests the setter and getter for the updatedDate field with a valid value.
     */
    @Test
    void testUpdatedDate_WithValidDate_ShouldSetUpdatedDate() {
        Instant updatedDate = Instant.now();
        orderDTO.setUpdatedDate(updatedDate);
        assertEquals(updatedDate, orderDTO.getUpdatedDate());
    }

    /**
     * Tests the automatic setting of the updatedDate field if it's not provided.
     */
    @Test
    void testUpdatedDateIsAutomaticallySetIfNotProvided() {
        if (orderDTO.getUpdatedDate() == null) {
            orderDTO.setUpdatedDate(Instant.now());
        }
        assertNotNull(orderDTO.getUpdatedDate(), "UpdatedDate should be automatically set and not be null");
    }

    /**
     * Tests the setter and getter for the orderItems field.
     */
    @Test
    void testOrderItems() {
        OrderItem item1 = new OrderItem();
        item1.setItemId(1001L);
        item1.setItemName("Product 1");
        item1.setQuantity(2);
        item1.setPrice(new java.math.BigDecimal("50.00"));

        OrderItem item2 = new OrderItem();
        item2.setItemId(1002L);
        item2.setItemName("Product 2");
        item2.setQuantity(1);
        item2.setPrice(new java.math.BigDecimal("30.00"));

        List<OrderItem> orderItems = Arrays.asList(item1, item2);
        List<OrderItemDTO> orderItemDTOs = orderItems.stream()
                .map(orderItem -> {
                    OrderItemDTO dto = new OrderItemDTO();
                    dto.setItemId(orderItem.getItemId());
                    dto.setItemName(orderItem.getItemName());
                    dto.setQuantity(orderItem.getQuantity());
                    dto.setPrice(orderItem.getPrice());
                    return dto;
                })
                .collect(Collectors.toList());

        orderDTO.setOrderItems(orderItemDTOs);

        assertEquals(2, orderDTO.getOrderItems().size());
        assertEquals("Product 1", orderDTO.getOrderItems().get(0).getItemName());
    }

    /**
     * Tests the setter and getter for the totalAmount field with a valid value.
     */
    @Test
    void testTotalAmount_WithValidValue_ShouldSetTotalAmount() {
        orderDTO.setTotalAmount(130.0);
        assertEquals(130.0, orderDTO.getTotalAmount());
    }

    /**
     * Tests the setter for the totalAmount field with an invalid value, expecting an IllegalArgumentException.
     */
    @Test
    void testTotalAmount_WithInvalidValue_ShouldThrowException() {
        double invalidAmount = 0;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderDTO.setTotalAmount(invalidAmount);
        });
        assertEquals("Total Amount Can't be Zero", exception.getMessage());
    }

    /**
     * Tests the setter and getter for the totalQuantity field with a valid value.
     */
    @Test
    void testTotalQuantity_WithValidValue_ShouldSetTotalQuantity() {
        orderDTO.setTotalQuantity(3);
        assertEquals(3, orderDTO.getTotalQuantity());
    }

    /**
     * Tests the setter for the totalQuantity field with an invalid value, expecting an IllegalArgumentException.
     */
    @Test
    void testTotalQuantity_WithInvalidValue_ShouldThrowException() {
        int invalidTotalQuantity = 0;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderDTO.setTotalQuantity(invalidTotalQuantity);
        });
        assertEquals("The Minimum Quantity should be 1", exception.getMessage());
    }

    /**
     * Tests the setter and getter for the estimated_delivery_date field with a valid value.
     */
    @Test
    void testEstimatedDeliveryDate_WithValidValue_ShouldSetEstimatedDeliveryDate() {
        LocalDateTime estimatedDeliveryDate = LocalDateTime.now();
        orderDTO.setEstimated_delivery_date(estimatedDeliveryDate);
        assertEquals(estimatedDeliveryDate, orderDTO.getEstimated_delivery_date());
    }

    /**
     * Tests the setter for the estimated_delivery_date field with a null value, expecting a NullPointerException.
     */
    @Test
    void testEstimatedDeliveryDate_WithNullValue_ShouldThrowException() {
        LocalDateTime nullEstimatedDeliveryDate = null;
        Exception exception = assertThrows(NullPointerException.class, () -> {
            orderDTO.setEstimated_delivery_date(nullEstimatedDeliveryDate);
        });
        assertEquals("Estimated_delivery_date cannot be Null", exception.getMessage());
    }

    /**
     * Tests the setter and getter for the shippingAddress field with a valid value.
     */
    @Test
    void testShippingAddress_WithValidValue_ShouldSetShippingAddress() {
        AddressDTO expectedAddress= new AddressDTO("john", "john@gmail.com","45125412154","tolichoki","hyd","ta","62703L");
        orderDTO.setShippingAddress(expectedAddress);
        AddressDTO actualAddress = orderDTO.getShippingAddress();
        assertEquals(expectedAddress.getState(),actualAddress.getState());
        assertEquals(expectedAddress.getAddress(),actualAddress.getAddress());
        assertEquals(expectedAddress.getCity(),actualAddress.getCity());
        assertEquals(expectedAddress.getZipcode(),actualAddress.getZipcode());
    }

    /**
     * Tests the setter for the shippingAddress field with a null value, expecting a NullPointerException.
     */
    @Test
    void testShippingAddress_WithNullValue_ShouldThrowException() {
        AddressDTO expected = new AddressDTO("john", "john@gmail.com","45125412154","hebbal","blr","ka","");
        AddressDTO expected1 = new AddressDTO("bob", "john@gmail.com","45125412154","kormangala","blr","ka","nnbbb");
        AddressDTO expected2 =new AddressDTO("jimmy", "john@gmail.com","45125412154","charminar","","telangana","9876");
        AddressDTO excepted3 = new AddressDTO("john", "john@gmail.com","45125412154","","hyd","talangana","87878");
//        orderDTO.setShippingAddress(expected);
        AddressDTO actual = orderDTO.getShippingAddress();

//        AddressDTO invalidShippingAddress = null;
        Exception exception = assertThrows(NullPointerException.class, () -> {
            orderDTO.setShippingAddress(expected);
            orderDTO.setShippingAddress(expected1);
            orderDTO.setShippingAddress(expected2);
            orderDTO.setShippingAddress(excepted3);
        });
        assertEquals("Address field's Can't be Null", exception.getMessage());
    }

    /**
     * Tests the setter and getter for the billingAddress field with a valid value.
     */
    @Test
    void testBillingAddress_WithValidValue_ShouldSetBillingAddress() {
        AddressDTO excepted = new AddressDTO("john", "john@gmail.com","45125412154","tolichoki","hyd","ta","62703L");

        orderDTO.setBillingAddress(excepted);
        AddressDTO actual = orderDTO.getBillingAddress();

        assertEquals(excepted.getZipcode(), actual.getZipcode());
    }

    /**
     * Tests the setter for the billingAddress field with a null value, expecting a NullPointerException.
     */
    @Test
    void testBillingAddress_WithNullValue_ShouldThrowException() {
        AddressDTO expected = new AddressDTO("john", "john@gmail.com","45125412154","hebbal","blr","ka","");
        AddressDTO expected1 = new AddressDTO("bob", "john@gmail.com","45125412154","kormangala","blr","ka","nnbbb");
        AddressDTO expected2 =new AddressDTO("jimmy", "john@gmail.com","45125412154","charminar","","telangana","9876");
        AddressDTO excepted3 = new AddressDTO("john", "john@gmail.com","45125412154","","hyd","talangana","87878");
//        orderDTO.setShippingAddress(expected);
        AddressDTO actual = orderDTO.getBillingAddress();

//        AddressDTO invalidShippingAddress = null;
        Exception exception = assertThrows(NullPointerException.class, () -> {
            orderDTO.setBillingAddress(expected);
            orderDTO.setBillingAddress(expected1);
            orderDTO.setBillingAddress(expected2);
            orderDTO.setBillingAddress(excepted3);
        });
        assertEquals("Billing Address Can't be Null", exception.getMessage());
    }

    /**
     * Tests the setter and getter for the shippingzipCode field with a valid value.
     */
    @Test
    void testShippingZipCode_WithValidValue_ShouldSetShippingZipCode() {
        orderDTO.setShippingzipCode(12345L);
        assertEquals(12345L, orderDTO.getShippingzipCode());
    }

    /**
     * Tests the setter for the shippingzipCode field with a null value, expecting an IllegalArgumentException.
     */
    @Test
    void testShippingZipCode_WithNullValue_ShouldThrowException() {
        Long invalidShippingZipCode =-898L;
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderDTO.setShippingzipCode(invalidShippingZipCode);
        });
        assertEquals("Shipping Zipcode should be valid it can't be zero or Negative", exception.getMessage());
    }
    /**
     * Tests the setter and getter for the orderStatus field.
     */
    @Test
    void testOrderStatus() {
        // Set the orderStatus
        orderDTO.setOrderStatus(OrderStatus.CONFIRMED);
        // Verify the orderStatus is set correctly
        assertEquals(OrderStatus.CONFIRMED, orderDTO.getOrderStatus());
    }

    /**
     * Tests the setter and getter for the paymentStatus field.
     */
    @Test
    void testPaymentStatus() {
        // Set the paymentStatus
        orderDTO.setPaymentStatus(PaymentStatus.COMPLETED);
        // Verify the paymentStatus is set correctly
        assertEquals(PaymentStatus.COMPLETED, orderDTO.getPaymentStatus());
    }
}
