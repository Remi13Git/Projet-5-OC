package com.openclassrooms.starterjwt.security;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Créer un utilisateur fictif
        user = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .build();
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        // Simuler le comportement du UserRepository
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        // Appel à la méthode loadUserByUsername
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("john.doe@example.com");

        // Cast de UserDetails en UserDetailsImpl pour accéder aux méthodes spécifiques
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;

        // Vérifications
        assertNotNull(userDetailsImpl);
        assertEquals("john.doe@example.com", userDetailsImpl.getUsername());
        assertEquals("John", userDetailsImpl.getFirstName());
        assertEquals("Doe", userDetailsImpl.getLastName());
        assertEquals("password", userDetailsImpl.getPassword());

        // Vérifier que la méthode findByEmail a été appelée une fois
        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Simuler un utilisateur introuvable dans le UserRepository
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.empty());

        // Vérifier que l'exception UsernameNotFoundException est levée
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername("john.doe@example.com");
        });

        // Vérifier que la méthode findByEmail a été appelée une fois
        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
    }
}
