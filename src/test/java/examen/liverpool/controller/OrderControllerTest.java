package examen.liverpool.controller;

import static javax.swing.UIManager.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import examen.liverpool.dto.Orders;
import examen.liverpool.service.orders.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testGetAllOrders() throws Exception {
        Orders order1 = new Orders(); // Crea un objeto de prueba
        Orders order2 = new Orders();
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        mockMvc.perform(get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2))); // Verifica que el tamaño sea 2
    }

    @Test
    public void testGetOrderById_Found() throws Exception {
        Orders order = new Orders(); // Crea un objeto de prueba
        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetOrderById_NotFound() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOrder() throws Exception {
        Orders order = new Orders(); // Crea un objeto de prueba
        when(orderService.createOrder(any(Orders.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateOrder_Found() throws Exception {
        Orders order = new Orders(); // Crea un objeto de prueba
        when(orderService.updateOrder(eq(1L), any(Orders.class))).thenReturn(order);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testUpdateOrder_NotFound() throws Exception {
        when(orderService.updateOrder(eq(1L), any(Orders.class))).thenReturn(null);

        mockMvc.perform(put("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Orders())))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/api/orders/1"))
                .andExpect(status().isNoContent());
    }
}