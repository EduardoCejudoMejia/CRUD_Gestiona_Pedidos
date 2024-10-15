package examen.liverpool.database.service.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import examen.liverpool.dto.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_NewUser() {
        Users user = new Users(); // Suponiendo que Users tiene un constructor vacío
        when(entityManager.persist(user)).thenReturn(user);

        Users savedUser = userRepository.save(user);

        verify(entityManager).persist(user);
        assertEquals(user, savedUser);
    }

    @Test
    public void testSave_ExistingUser() {
        Users user = new Users();
        user.setId(1L); // Simula que el usuario ya existe
        when(entityManager.merge(user)).thenReturn(user);

        Users savedUser = userRepository.save(user);

        verify(entityManager).merge(user);
        assertEquals(user, savedUser);
    }

    @Test
    public void testFindById_Found() {
        Users user = new Users();
        when(entityManager.find(Users.class, 1L)).thenReturn(user);

        Optional<Users> foundUser = userRepository.findById(1L);

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    public void testFindById_NotFound() {
        when(entityManager.find(Users.class, 1L)).thenReturn(null);

        Optional<Users> foundUser = userRepository.findById(1L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    public void testFindAll() {
        Users user1 = new Users();
        Users user2 = new Users();
        when(entityManager.createQuery("SELECT * FROM User", Users.class)).thenReturn(mock(TypedQuery.class));
        when(entityManager.createQuery("SELECT * FROM User", Users.class).getResultList()).thenReturn(Arrays.asList(user1, user2));

        List<Users> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
}