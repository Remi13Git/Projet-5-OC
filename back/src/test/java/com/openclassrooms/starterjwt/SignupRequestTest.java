package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestTest {

    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setUp() {
        // Initialisation du validateur
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();  // Initialisation des propriétés
    }

    // Test de la méthode equals
    @Test
    void testEquals() {
        // Créer deux objets SignupRequest identiques
        SignupRequest signupRequest1 = new SignupRequest();
        signupRequest1.setEmail("test@example.com");
        signupRequest1.setFirstName("John");
        signupRequest1.setLastName("Doe");
        signupRequest1.setPassword("password123");

        SignupRequest signupRequest2 = new SignupRequest();
        signupRequest2.setEmail("test@example.com");
        signupRequest2.setFirstName("John");
        signupRequest2.setLastName("Doe");
        signupRequest2.setPassword("password123");

        // Vérifier qu'ils sont égaux
        assertEquals(signupRequest1, signupRequest2);

        // Vérifier qu'un objet est égal à lui-même
        assertEquals(signupRequest1, signupRequest1);

        // Créer un objet avec des valeurs différentes
        SignupRequest signupRequest3 = new SignupRequest();
        signupRequest3.setEmail("different@example.com");
        signupRequest3.setFirstName("Jane");
        signupRequest3.setLastName("Smith");
        signupRequest3.setPassword("password456");

        // Vérifier qu'ils ne sont pas égaux
        assertNotEquals(signupRequest1, signupRequest3);
    }

    // Test de la méthode hashCode
    @Test
    void testHashCode() {
        // Créer deux objets SignupRequest identiques
        SignupRequest signupRequest1 = new SignupRequest();
        signupRequest1.setEmail("test@example.com");
        signupRequest1.setFirstName("John");
        signupRequest1.setLastName("Doe");
        signupRequest1.setPassword("password123");

        SignupRequest signupRequest2 = new SignupRequest();
        signupRequest2.setEmail("test@example.com");
        signupRequest2.setFirstName("John");
        signupRequest2.setLastName("Doe");
        signupRequest2.setPassword("password123");

        // Vérifier que leurs hashCodes sont identiques
        assertEquals(signupRequest1.hashCode(), signupRequest2.hashCode());

        // Créer un objet avec des valeurs différentes
        SignupRequest signupRequest3 = new SignupRequest();
        signupRequest3.setEmail("different@example.com");
        signupRequest3.setFirstName("Jane");
        signupRequest3.setLastName("Smith");
        signupRequest3.setPassword("password456");

        // Vérifier que leurs hashCodes sont différents
        assertNotEquals(signupRequest1.hashCode(), signupRequest3.hashCode());
    }

    // Test de la méthode toString
    @Test
    void testToString() {
        // Créer un SignupRequest
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        // Récupérer la sortie de la méthode toString()
        String toStringResult = signupRequest.toString();

        // Vérifier que la chaîne contient les informations attendues
        assertTrue(toStringResult.contains("email=test@example.com"));
        assertTrue(toStringResult.contains("firstName=John"));
        assertTrue(toStringResult.contains("lastName=Doe"));
        assertTrue(toStringResult.contains("password=password123"));
    }
}
