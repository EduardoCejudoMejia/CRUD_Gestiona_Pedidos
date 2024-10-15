package examen.liverpool.database.service.order;

import examen.liverpool.dto.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Orders save(Orders order);
    Optional<Orders> findById(Long id);
    List<Orders> findAll();
}
