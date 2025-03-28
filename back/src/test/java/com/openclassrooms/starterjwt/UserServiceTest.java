package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialiser les mocks

        // Créer un utilisateur de test
        user = new User(1L, "test@example.com", "Doe", "John", "password123", true, null, null);
    }

    @Test
    void testFindById_UserExists() {
        // Préparer le comportement du mock
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Appeler la méthode à tester
        User result = userService.findById(1L);

        // Vérifications
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void testDelete_UserExists() {
        // Préparer le comportement du mock
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Appeler la méthode à tester
        userService.delete(1L);

        // Vérification si deleteById a été appelé avec l'ID correct
        verify(userRepository, times(1)).deleteById(1L);
    }
}
