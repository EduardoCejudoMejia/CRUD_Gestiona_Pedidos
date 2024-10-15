package examen.liverpool.service.users;

import examen.liverpool.dto.Users;

import java.util.List;

public interface UserService {

    List<Users> getAllUsers();
    Users getUserById(Long id);
    Users createUser(Users user);
    Users updateUser(Long id, Users userDetails);
    void deleteUser(Long id);

}
