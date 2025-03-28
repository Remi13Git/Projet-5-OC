package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@SpringBootTest
public class SessionServiceTest {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Session session;

    @BeforeEach
    public void setUp() {
        // Créer un utilisateur pour les tests
        user = new User();
        user.setEmail("testuser@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");
        user.setAdmin(true);
        user = userRepository.save(user);

        // Créer une session pour les tests
        session = new Session();
        session.setName("Test Session");
        session.setDescription("Test Description");
        session.setDate(new java.util.Date());
        session = sessionRepository.save(session);
    }

    @Test
    public void testFindAllSessions() {
        // Tester la récupération de toutes les sessions
        List<Session> sessions = sessionService.findAll();
        assertTrue(sessions.size() > 0, "There should be at least one session in the database");
    }

    @Test
    public void testUpdateSession() {
        // Tester la mise à jour d'une session
        session.setName("Updated Session");
        session.setDescription("Updated Description");
        sessionService.update(session.getId(), session);

        // Utilisation de orElseThrow() avec une exception
        Session updatedSession = sessionRepository.findById(session.getId())
                                                .orElseThrow(() -> new RuntimeException("Session not found"));
        assertEquals("Updated Session", updatedSession.getName(), "Session name should be updated");
    }

    @Test
    public void testNoLongerParticipateInSession() {
        // Ajouter l'utilisateur à la session d'abord
        sessionService.participate(session.getId(), user.getId());

        // Tester la suppression de l'utilisateur de la session
        sessionService.noLongerParticipate(session.getId(), user.getId());

        // Utilisation de orElseThrow() avec une exception
        Session updatedSession = sessionRepository.findById(session.getId())
                                                .orElseThrow(() -> new RuntimeException("Session not found"));
        assertFalse(updatedSession.getUsers().contains(user), "User should no longer participate in the session");
    }

    @Test
    public void testParticipateAlreadyParticipating() {
        // Teste le cas où un utilisateur essaie de participer à une session déjà remplie
        sessionService.participate(session.getId(), user.getId());  // L'utilisateur participe

        // Tentative de ré-participation (devrait lever une exception)
        assertThrows(BadRequestException.class, () -> {
            sessionService.participate(session.getId(), user.getId());
        });
    }

    @Test
    public void testGetByIdSessionExists() {
        // Crée une session et l'enregistre
        sessionService.create(session);
        
        // Récupère la session par son ID
        Session retrievedSession = sessionService.getById(session.getId());
        
        // Vérifie que la session récupérée n'est pas nulle et que les informations correspondent
        assertNotNull(retrievedSession);
        assertEquals(session.getId(), retrievedSession.getId());
        assertEquals(session.getName(), retrievedSession.getName());
    }

    @Test
    public void testParticipateNonExistingUser() {
        // Utilise un identifiant d'utilisateur inexistant
        Long invalidUserId = -1L; // ou tout autre identifiant invalide
        // Vérifie qu'une NotFoundException est lancée
        assertThrows(NotFoundException.class, () -> {
            sessionService.participate(session.getId(), invalidUserId);
        });
    }

    @Test
    public void testNoLongerParticipateNonExistingSession() {
        // Utilise un identifiant de session inexistant
        Long invalidSessionId = -1L; // ou tout autre identifiant invalide
        // Vérifie qu'une NotFoundException est lancée
        assertThrows(NotFoundException.class, () -> {
            sessionService.noLongerParticipate(invalidSessionId, user.getId());
        });
    }
}
