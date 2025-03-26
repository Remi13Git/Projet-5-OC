package com.openclassrooms.starterjwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.openclassrooms.starterjwt.payload.request.LoginRequest;

class LoginRequestTest {

    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setUp() {
        // Initialisation du validateur
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();  // Initialisation des propriétés
    }

    @Test
    void testValidLoginRequest() {
        // Créer un LoginRequest valide
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        // Valider l'objet
        BindingResult result = validate(loginRequest);

        // Vérifier qu'il n'y a pas d'erreurs de validation
        assertFalse(result.hasErrors());
    }

    @Test
    void testInvalidLoginRequest() {
        // Créer un LoginRequest avec des champs vides
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("");
        loginRequest.setPassword("");

        // Valider l'objet
        BindingResult result = validate(loginRequest);

        // Vérifier qu'il y a des erreurs de validation
        assertTrue(result.hasErrors());

        // Vérifier que les erreurs sont bien sur les bons champs
        FieldError emailError = result.getFieldError("email");
        FieldError passwordError = result.getFieldError("password");

        assertNotNull(emailError);
        assertNotNull(passwordError);
        assertEquals("email", emailError.getField());
        assertEquals("password", passwordError.getField());
    }

    private BindingResult validate(Object object) {
        BindingResult result = new org.springframework.validation.BeanPropertyBindingResult(object, "loginRequest");
        validator.validate(object, result);
        return result;
    }
}
