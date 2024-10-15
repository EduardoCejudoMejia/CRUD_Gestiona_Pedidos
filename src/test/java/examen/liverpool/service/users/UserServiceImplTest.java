package examen.liverpool.service.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import examen.liverpool.database.service.user.UserRepository;
import examen.liverpool.dto.Users;
import examen.liverpool.tools.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        Users user1 = new Users();
        Users user2 = new Users();
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<Users> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    public void testGetUserById_ValidId() {
        Users user = new Users();
        when(userValidator.validateUserId(1L)).thenReturn(null); // Simula que la validación es exitosa
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Users foundUser = userService.getUserById(1L);

        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }

    @Test
    public void testGetUserById_InvalidId() {
        when(userValidator.validateUserId(1L)).thenThrow(new IllegalArgumentException("Invalid ID"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("Invalid ID", exception.getMessage());
    }

    @Test
    public void testCreateUser_ValidUser() {
        Users user = new Users();
        when(userValidator.validateUser(user)).thenReturn(null); // Simula que la validación es exitosa
        when(userRepository.save(user)).thenReturn(user);

        Users createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals(user, createdUser);
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateUser_ValidIdAndUser() {
        Users existingUser = new Users();
        existingUser.setId(1L);
        Users userDetails = new Users();
        userDetails.setName("Eduardo");
        userDetails.setPaternalSurname("Cejudo");
        userDetails.setMaternalSurname("Mejia");
        userDetails.setEmail("eduardo_cejudo@outlook.com");
        userDetails.setAddress("Toluca");

        when(userValidator.validateUserId(1L)).thenReturn(null);
        when(userValidator.validateUser(userDetails)).thenReturn(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        Users updatedUser = userService.updateUser(1L, userDetails);

        assertNotNull(updatedUser);
        assertEquals(existingUser.getName(), userDetails.getName());
        assertEquals(existingUser.getPaternalSurname(), userDetails.getPaternalSurname());
        assertEquals(existingUser.getMaternalSurname(), userDetails.getMaternalSurname());
        assertEquals(existingUser.getEmail(), userDetails.getEmail());
        assertEquals(existingUser.getAddress(), userDetails.getAddress());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        Users userDetails = new Users();
        when(userValidator.validateUserId(1L)).thenReturn(null);
        when(userValidator.validateUser(userDetails)).thenReturn(null);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Users updatedUser = userService.updateUser(1L, userDetails);

        assertNull(updatedUser);
    }

    @Test
    public void testDeleteUser_ValidId() {
        when(userValidator.validateUserId(1L)).thenReturn(null);

        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    @Test
    public void testDeleteUser_InvalidId() {
        when(userValidator.validateUserId(1L)).thenThrow(new IllegalArgumentException("Invalid ID"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(1L);
        });

        assertEquals("Invalid ID", exception.getMessage());
    }
}