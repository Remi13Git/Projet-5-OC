package com.openclassrooms.starterjwt.security;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void testIsAdmin() {
        assertTrue(userDetails.getAdmin());
    }

    @Test
    void testGetAuthorities() {
        assertNotNull(userDetails.getAuthorities());
        assertTrue(userDetails.getAuthorities().isEmpty());
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
}
