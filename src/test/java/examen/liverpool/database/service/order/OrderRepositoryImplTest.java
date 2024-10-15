package examen.liverpool.database.service.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import examen.liverpool.dto.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private OrderRepositoryImpl orderRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_NewOrder() {
        Orders order = new Orders(); // Suponiendo que Orders tiene un constructor vacío
        when(entityManager.persist(order)).thenReturn(order);

        Orders savedOrder = orderRepository.save(order);

        verify(entityManager).persist(order);
        assertEquals(order, savedOrder);
    }

    @Test
    public void testSave_ExistingOrder() {
        Orders order = new Orders();
        order.setId(1L); // Simula que la orden ya existe
        when(entityManager.merge(order)).thenReturn(order);

        Orders savedOrder = orderRepository.save(order);

        verify(entityManager).merge(order);
        assertEquals(order, savedOrder);
    }

    @Test
    public void testFindById_Found() {
        Orders order = new Orders();
        when(entityManager.find(Orders.class, 1L)).thenReturn(order);

        Optional<Orders> foundOrder = orderRepository.findById(1L);

        assertTrue(foundOrder.isPresent());
        assertEquals(order, foundOrder.get());
    }

    @Test
    public void testFindById_NotFound() {
        when(entityManager.find(Orders.class, 1L)).thenReturn(null);

        Optional<Orders> foundOrder = orderRepository.findById(1L);

        assertFalse(foundOrder.isPresent());
    }

    @Test
    public void testFindAll() {
        Orders order1 = new Orders();
        Orders order2 = new Orders();
        when(entityManager.createQuery("SELECT * FROM Order", Orders.class)).thenReturn(mock(TypedQuery.class));
        when(entityManager.createQuery("SELECT * FROM Order", Orders.class).getResultList()).thenReturn(Arrays.asList(order1, order2));

        List<Orders> orders = orderRepository.findAll();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }
}