package examen.liverpool.service.users;

import examen.liverpool.database.service.user.UserRepository;
import examen.liverpool.dto.Users;

import java.util.List;

import examen.liverpool.tools.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Override
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getUserById(Long id) {
        userValidator.validateUserId(id); // Validar el ID
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Users createUser(Users user) {
        userValidator.validateUser(user); // Validar el usuario
        return userRepository.save(user);
    }

    @Override
    public Users updateUser(Long id, Users userDetails) {
        userValidator.validateUserId(id); // Validar el ID
        userValidator.validateUser(userDetails); // Validar los detalles del usuario

        // Buscar el usuario por ID
        Users user = getUserById(id);
        if (user != null) {
            // Actualizar los campos necesarios
            user.setName(userDetails.getName());
            user.setPaternalSurname(userDetails.getPaternalSurname());
            user.setMaternalSurname(userDetails.getMaternalSurname());
            user.setEmail(userDetails.getEmail());
            user.setAddress(userDetails.getAddress());
            return userRepository.save(user);
        }
        return null; // Devuelve null si el usuario no se encuentra
    }

    @Override
    public void deleteUser(Long id) {
        userValidator.validateUserId(id); // Validar el ID
        userRepository.deleteById(id);
    }
}
