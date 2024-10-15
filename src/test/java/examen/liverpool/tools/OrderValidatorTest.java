package examen.liverpool.tools;

import static org.junit.jupiter.api.Assertions.*;

import examen.liverpool.dto.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderValidatorTest {

    private OrderValidator orderValidator;

    @BeforeEach
    public void setUp() {
        orderValidator = new OrderValidator();
    }

    @Test
    public void testValidateOrder_NullOrder() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderValidator.validateOrder(null);
        });

        assertEquals("Order cannot be null", exception.getMessage());
    }

    @Test
    public void testValidateOrder_EmptyProductCode() {
        Orders order = new Orders();
        order.setProductCode("");
        order.setAmount(1);
        order.setPrice(10.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderValidator.validateOrder(order);
        });

        assertEquals("Product code is required", exception.getMessage());
    }

    @Test
    public void testValidateOrder_NegativeAmount() {
        Orders order = new Orders();
        order.setProductCode("P123");
        order.setAmount(0); // Invalid amount
        order.setPrice(10.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderValidator.validateOrder(order);
        });

        assertEquals("Amount must be greater than zero", exception.getMessage());
    }

    @Test
    public void testValidateOrder_NegativePrice() {
        Orders order = new Orders();
        order.setProductCode("P123");
        order.setAmount(1);
        order.setPrice(-5.0); // Invalid price

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderValidator.validateOrder(order);
        });

        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    public void testValidateOrder_ValidOrder() {
        Orders order = new Orders();
        order.setProductCode("P123");
        order.setAmount(1);
        order.setPrice(10.0);

        assertDoesNotThrow(() -> orderValidator.validateOrder(order));
    }

    @Test
    public void testValidateOrderId_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderValidator.validateOrderId(null);
        });

        assertEquals("Invalid order ID", exception.getMessage());
    }

    @Test
    public void testValidateOrderId_NegativeId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderValidator.validateOrderId(-1L);
        });

        assertEquals("Invalid order ID", exception.getMessage());
    }

    @Test
    public void testValidateOrderId_ValidId() {
        assertDoesNotThrow(() -> orderValidator.validateOrderId(1L));
    }
}