package examen.liverpool.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class OrdersTest {

    @Test
    public void testGettersAndSetters() {
        Orders order = new Orders();

        // Test setId and getId
        order.setId(1L);
        assertEquals(1L, order.getId());

        // Test setProductCode and getProductCode
        String productCode = "pedido13";
        order.setProductCode(productCode);
        assertEquals(productCode, order.getProductCode());

        // Test setAmount and getAmount
        int amount = 5;
        order.setAmount(amount);
        assertEquals(amount, order.getAmount());

        // Test setPrice and getPrice
        double price = 19.99;
        order.setPrice(price);
        assertEquals(price, order.getPrice());
    }
}