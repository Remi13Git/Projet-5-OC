package com.openclassrooms.starterjwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.models.User;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserModelTest {

    private Validator validator;

    // Initialiser le validateur pour tester les contraintes
    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testUserValid() {
        // Créer un User valide
        User user = User.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Vérifier que l'objet n'a pas de violation de contrainte
        Set violations = validator.validate(user);
        assertTrue(violations.isEmpty(), "L'utilisateur ne doit pas violer de contraintes");
    }

    @Test
    public void testUserInvalidEmail() {
        // Créer un User avec un email invalide
        User user = User.builder()
                .email("invalid-email")  // Ne respecte pas @Email
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Vérifier que l'objet a des violations de contrainte
        Set violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "L'email doit être valide");
    }

    @Test
    public void testUserInvalidFirstName() {
        // Créer un User avec un prénom trop long
        User user = User.builder()
                .email("test@example.com")
                .firstName("JohnJohnJohnJohnJohnJohn")  // Trop long, plus de 20 caractères
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Vérifier que l'objet a des violations de contrainte
        Set violations = validator.validate(user);
        assertFalse(violations.isEmpty(), "Le prénom ne doit pas excéder 20 caractères");
    }

    @Test
    public void testUserToString() {
        User user = User.builder()
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Tester la méthode toString()
        assertTrue(user.toString().contains("John"));
        assertTrue(user.toString().contains("Doe"));
    }

    @Test
    public void testUserEquality() {
        User user1 = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User user3 = User.builder()
                .id(2L)
                .email("another@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Vérifier l'égalité des objets
        assertEquals(user1, user2);  // Ils doivent être égaux car leurs ID sont les mêmes
        assertNotEquals(user1, user3);  // Différents car leurs IDs sont différents
    }

    @Test
    public void testUserHashCode() {
        User user1 = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Vérifier que les objets ayant les mêmes valeurs de champ ont le même hashcode
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testIdGeneration() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");
        user.setAdmin(true);
        
        // L'ID devrait être nul avant la persistance en base de données
        assertNull(user.getId());

        // Après la persistance, l'ID devrait être généré automatiquement par la base de données.
        // Cela ne peut pas être testé ici directement sans interaction avec la base de données.
    }
}
