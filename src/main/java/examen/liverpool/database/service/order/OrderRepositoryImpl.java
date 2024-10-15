package examen.liverpool.database.service.order;

import examen.liverpool.dto.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Orders save(Orders order) {
        if (order.getId() == null) {
            // Nueva orden, se persiste
            entityManager.persist(order);
        } else {
            // Orden existente, se actualiza
            entityManager.merge(order);
        }
        return order;
    }

    @Override
    public Optional<Orders> findById(Long id) {
        Orders order = entityManager.find(Orders.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Orders> findAll() {
        TypedQuery<Orders> query = entityManager.createQuery("SELECT * FROM Order", Orders.class);
        return query.getResultList();
    }
}
