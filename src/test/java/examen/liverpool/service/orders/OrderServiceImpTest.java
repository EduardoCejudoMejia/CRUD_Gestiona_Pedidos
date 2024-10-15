package examen.liverpool.service.orders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import examen.liverpool.database.service.order.OrderRepository;
import examen.liverpool.dto.Orders;
import examen.liverpool.tools.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderValidator orderValidator;

    @InjectMocks
    private OrderServiceImp orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllOrders() {
        Orders order1 = new Orders();
        Orders order2 = new Orders();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Orders> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void testGetOrderById_ValidId() {
        Orders order = new Orders();
        when(orderValidator.validateOrderId(1L)).thenReturn(null); // Simula que la validación es exitosa
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Orders foundOrder = orderService.getOrderById(1L);

        assertNotNull(foundOrder);
        assertEquals(order, foundOrder);
    }

    @Test
    public void testGetOrderById_InvalidId() {
        when(orderValidator.validateOrderId(1L)).thenThrow(new IllegalArgumentException("Invalid ID"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.getOrderById(1L);
        });

        assertEquals("Invalid ID", exception.getMessage());
    }

    @Test
    public void testCreateOrder_ValidOrder() {
        Orders order = new Orders();
        when(orderValidator.validateOrder(order)).thenReturn(null); // Simula que la validación es exitosa
        when(orderRepository.save(order)).thenReturn(order);

        Orders createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder);
        assertEquals(order, createdOrder);
        verify(orderRepository).save(order);
    }

    @Test
    public void testUpdateOrder_ValidIdAndOrder() {
        Orders existingOrder = new Orders();
        existingOrder.setId(1L);
        Orders orderDetails = new Orders();
        orderDetails.setProductCode("P123");
        orderDetails.setAmount(10);
        orderDetails.setPrice(29.99);

        when(orderValidator.validateOrderId(1L)).thenReturn(null);
        when(orderValidator.validateOrder(orderDetails)).thenReturn(null);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(existingOrder)).thenReturn(existingOrder);

        Orders updatedOrder = orderService.updateOrder(1L, orderDetails);

        assertNotNull(updatedOrder);
        assertEquals(existingOrder.getProductCode(), orderDetails.getProductCode());
        assertEquals(existingOrder.getAmount(), orderDetails.getAmount());
        assertEquals(existingOrder.getPrice(), orderDetails.getPrice());
    }

    @Test
    public void testUpdateOrder_OrderNotFound() {
        Orders orderDetails = new Orders();
        when(orderValidator.validateOrderId(1L)).thenReturn(null);
        when(orderValidator.validateOrder(orderDetails)).thenReturn(null);
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        Orders updatedOrder = orderService.updateOrder(1L, orderDetails);

        assertNull(updatedOrder);
    }

    @Test
    public void testDeleteOrder_ValidId() {
        when(orderValidator.validateOrderId(1L)).thenReturn(null);

        orderService.deleteOrder(1L);

        verify(orderRepository).deleteById(1L);
    }

    @Test
    public void testDeleteOrder_InvalidId() {
        when(orderValidator.validateOrderId(1L)).thenThrow(new IllegalArgumentException("Invalid ID"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.deleteOrder(1L);
        });

        assertEquals("Invalid ID", exception.getMessage());
    }
}