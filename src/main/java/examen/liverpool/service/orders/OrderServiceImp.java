package examen.liverpool.service.orders;

import examen.liverpool.database.service.order.OrderRepository;
import examen.liverpool.dto.Orders;
import examen.liverpool.tools.OrderValidator;

import java.util.List;

@Service
public class OrderServiceImp {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderValidator orderValidator;

    @Override
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Orders getOrderById(Long id) {
        orderValidator.validateOrderId(id); // Validar el ID
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Orders createOrder(Orders order) {
        orderValidator.validateOrder(order); // Validar la orden
        return orderRepository.save(order);
    }

    @Override
    public Orders updateOrder(Long id, Orders orderDetails) {
        orderValidator.validateOrderId(id); // Validar el ID
        orderValidator.validateOrder(orderDetails); // Validar los detalles de la orden

        // Buscar la orden por ID
        Orders order = getOrderById(id);
        if (order != null) {
            // Actualizar los campos necesarios
            order.setProductCode(orderDetails.getProductCode());
            order.setAmount(orderDetails.getAmount());
            order.setPrice(orderDetails.getPrice());
            // Guardar la orden actualizada
            return orderRepository.save(order);
        }
        return null; // Devuelve null si la orden no se encuentra
    }

    @Override
    public void deleteOrder(Long id) {
        orderValidator.validateOrderId(id); // Validar el ID
        orderRepository.deleteById(id);
    }

}
