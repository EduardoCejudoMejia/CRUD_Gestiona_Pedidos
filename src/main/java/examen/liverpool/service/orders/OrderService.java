package examen.liverpool.service.orders;

import examen.liverpool.dto.Orders;

import java.util.List;

public interface OrderService {

    List<Orders> getAllOrders();
    Orders getOrderById(Long id);
    Orders createOrder(Orders order);
    Orders updateOrder(Long id, Orders orderDetails);
    void deleteOrder(Long id);

}
