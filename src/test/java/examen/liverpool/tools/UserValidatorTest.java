package examen.liverpool.tools;

import static org.junit.jupiter.api.Assertions.*;

import examen.liverpool.dto.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    public void setUp() {
        userValidator = new UserValidator();
    }

    @Test
    public void testValidateUser_NullUser() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userValidator.validateUser(null);
        });

        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    public void testValidateUser_EmptyName() {
        Users user = new Users();
        user.setName("");
        user.setEmail("user@example.com");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userValidator.validateUser(user);
        });

        assertEquals("Name is required", exception.getMessage());
    }

    @Test
    public void testValidateUser_EmptyEmail() {
        Users user = new Users();
        user.setName("John Doe");
        user.setEmail("");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userValidator.validateUser(user);
        });

        assertEquals("Email is required", exception.getMessage());
    }

    @Test
    public void testValidateUser_ValidUser() {
        Users user = new Users();
        user.setName("John Doe");
        user.setEmail("user@example.com");

        assertDoesNotThrow(() -> userValidator.validateUser(user));
    }

    @Test
    public void testValidateUserId_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userValidator.validateUserId(null);
        });

        assertEquals("Invalid user ID", exception.getMessage());
    }

    @Test
    public void testValidateUserId_NegativeId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userValidator.validateUserId(-1L);
        });

        assertEquals("Invalid user ID", exception.getMessage());
    }

    @Test
    public void testValidateUserId_ValidId() {
        assertDoesNotThrow(() -> userValidator.validateUserId(1L));
    }
}