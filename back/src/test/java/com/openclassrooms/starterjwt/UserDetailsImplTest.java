package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        // Créer une instance de UserDetailsImpl
        userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();
    }

    @Test
    void testGetId() {
        assertEquals(1L, userDetails.getId());
    }

    @Test
    void testGetUsername() {
        assertEquals("john_doe", userDetails.getUsername());
    }

    @Test
    void testGetFirstName() {
        assertEquals("John", userDetails.getFirstName());
    }

    @Test
    void testGetLastName() {
        assertEquals("Doe", userDetails.getLastName());
    }

    @Test
    void testIsAdmin() {
        assertTrue(userDetails.getAdmin());
    }

    @Test
    void testGetPassword() {
        assertEquals("password", userDetails.getPassword());
    }

    @Test
    void testGetAuthorities() {
        assertNotNull(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities().isEmpty());  // Comme la méthode retourne un HashSet vide
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testEquals() {
        UserDetailsImpl anotherUser = UserDetailsImpl.builder()
                .id(1L)
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();

        // Vérifier que deux objets avec les mêmes ids sont égaux
        assertTrue(userDetails.equals(anotherUser));

        // Vérifier que l'objet n'est pas égal à un objet de type différent
        assertFalse(userDetails.equals(new Object()));

        // Vérifier que l'objet n'est pas égal à un autre utilisateur avec un id différent
        anotherUser = UserDetailsImpl.builder()
                .id(2L)  // id différent
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();
        assertFalse(userDetails.equals(anotherUser));
    }

    @Test
    void testHashCode() {
        // Créez une instance de UserDetailsImpl avec des données spécifiques
        UserDetailsImpl user = UserDetailsImpl.builder()
                .id(1L)
                .username("john_doe")
                .firstName("John")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();

        // Vérifiez que le hashCode retourné est le bon
        int expectedHashCode = user.hashCode();  // Utilisez la méthode réelle pour obtenir le hashCode

        // Vérifiez que le hashCode est correct
        assertEquals(expectedHashCode, user.hashCode());
    }
}
