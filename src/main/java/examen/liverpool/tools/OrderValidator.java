package examen.liverpool.tools;

import examen.liverpool.dto.Orders;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void validateOrder(Orders order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (order.getProductCode() == null || order.getProductCode().isEmpty()) {
            throw new IllegalArgumentException("Product code is required");
        }
        if (order.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (order.getPrice() < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public void validateOrderId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }
    }
}
