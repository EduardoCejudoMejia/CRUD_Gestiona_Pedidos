package examen.liverpool.database.service.user;

import examen.liverpool.dto.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Users save(Users user) {
        if (user.getId() == null) {
            // Nueva usuario, se persiste
            entityManager.persist(user);
        } else {
            // Usuario existente, se actualiza
            entityManager.merge(user);
        }
        return user;
    }

    @Override
    public Optional<Users> findById(Long id) {
        Users user = entityManager.find(Users.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<Users> findAll() {
        TypedQuery<Users> query = entityManager.createQuery("SELECT * FROM User", Users.class);
        return query.getResultList();
    }
}
