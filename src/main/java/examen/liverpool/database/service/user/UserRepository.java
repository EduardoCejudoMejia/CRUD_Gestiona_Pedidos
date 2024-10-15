package examen.liverpool.database.service.user;

import examen.liverpool.dto.Users;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Users save(Users user);
    Optional<Users> findById(Long id);
    List<Users> findAll();
}
