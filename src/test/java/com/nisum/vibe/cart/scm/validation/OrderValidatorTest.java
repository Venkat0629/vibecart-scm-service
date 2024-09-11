package com.nisum.vibe.cart.scm.validation;
import com.nisum.vibe.cart.scm.Validation.OrderValidator;
import com.nisum.vibe.cart.scm.exception.ValidationException;
import com.nisum.vibe.cart.scm.model.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the OrderValidator class.
 * This class validates OrderDTO objects to ensure that all required fields are present and correctly formatted.
 */
class OrderValidatorTest {

    /**
     * Tests that the validation fails when the OrderDTO object is null.
     */
    @Test
    void validateOrder_nullOrder_shouldThrowException() {
        assertThrows(ValidationException.class, () -> OrderValidator.validateOrder(null),
                "OrderDTO cannot be null");
    }

    /**
     * Tests that the validation fails when the OrderItems list is empty.
     */
    @Test
    void validateOrder_emptyOrderItems_shouldThrowException() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("0CC27E82A4DF");
        orderDTO.setOrderItems(new ArrayList<>());
        assertThrows(ValidationException.class, () -> OrderValidator.validateOrder(orderDTO),
                "Order items cannot be empty");
    }

    /**
     * Tests that the validation fails when the OrderDate is null.
     * This test directly verifies that the OrderDTO class throws a NullPointerException
     * when setting a null OrderDate.
     */
    @Test
    void validateOrder_nullOrderDate_shouldThrowException() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("0CC27E82A4DF");
        Exception exceptionNull = assertThrows(NullPointerException.class, () -> {
            orderDTO.setOrderDate(null);
        });
        assertEquals("OrderDate Cannot be Null", exceptionNull.getMessage());
    }
}
