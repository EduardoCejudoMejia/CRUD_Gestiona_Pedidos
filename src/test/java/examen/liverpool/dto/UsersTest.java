package examen.liverpool.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UsersTest {

    @Test
    public void testGettersAndSetters() {
        Users user = new Users();

        // Test setId and getId
        user.setId(1L);
        assertEquals(1L, user.getId());

        // Test setName and getName
        String name = "Eduardo";
        user.setName(name);
        assertEquals(name, user.getName());

        // Test setPaternalSurname and getPaternalSurname
        String paternalSurname = "Cejudo";
        user.setPaternalSurname(paternalSurname);
        assertEquals(paternalSurname, user.getPaternalSurname());

        // Test setMaternalSurname and getMaternalSurname
        String maternalSurname = "Mejia";
        user.setMaternalSurname(maternalSurname);
        assertEquals(maternalSurname, user.getMaternalSurname());

        // Test setEmail and getEmail
        String email = "eduardo_cejudo@outlook.com";
        user.setEmail(email);
        assertEquals(email, user.getEmail());

        // Test setAddress and getAddress
        String address = "Toluca";
        user.setAddress(address);
        assertEquals(address, user.getAddress());
    }
}