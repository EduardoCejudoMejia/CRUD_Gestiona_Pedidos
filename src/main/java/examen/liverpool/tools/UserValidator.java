package examen.liverpool.tools;

import examen.liverpool.dto.Users;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public void validateUser(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        // Puedes agregar más validaciones según sea necesario
    }

    public void validateUserId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
    }
}
