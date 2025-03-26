package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @Test
    void testValidSignupRequest() {
        // Créer un SignupRequest valide
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        // Valider l'objet
        BindingResult result = validate(signupRequest);

        // Vérifier qu'il n'y a pas d'erreurs de validation
        assertFalse(result.hasErrors());
    }

    @Test
    void testInvalidSignupRequest_EmailInvalid() {
        // Créer un SignupRequest avec un email invalide
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("invalid-email");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        // Valider l'objet
        BindingResult result = validate(signupRequest);

        // Vérifier qu'il y a des erreurs de validation
        assertTrue(result.hasErrors());

        // Vérifier l'erreur sur l'email
        FieldError emailError = result.getFieldError("email");
        assertNotNull(emailError);
        assertEquals("email", emailError.getField());
    }

    @Test
    void testInvalidSignupRequest_FirstNameTooShort() {
        // Créer un SignupRequest avec un prénom trop court
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("J");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password123");

        // Valider l'objet
        BindingResult result = validate(signupRequest);

        // Vérifier qu'il y a des erreurs de validation
        assertTrue(result.hasErrors());

        // Vérifier l'erreur sur le prénom
        FieldError firstNameError = result.getFieldError("firstName");
        assertNotNull(firstNameError);
        assertEquals("firstName", firstNameError.getField());
    }

    @Test
    void testInvalidSignupRequest_PasswordTooShort() {
        // Créer un SignupRequest avec un mot de passe trop court
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("test@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("pass");

        // Valider l'objet
        BindingResult result = validate(signupRequest);

        // Vérifier qu'il y a des erreurs de validation
        assertTrue(result.hasErrors());

        // Vérifier l'erreur sur le mot de passe
        FieldError passwordError = result.getFieldError("password");
        assertNotNull(passwordError);
        assertEquals("password", passwordError.getField());
    }

    private BindingResult validate(Object object) {
        BindingResult result = new org.springframework.validation.BeanPropertyBindingResult(object, "signupRequest");
        validator.validate(object, result);
        return result;
    }
}
