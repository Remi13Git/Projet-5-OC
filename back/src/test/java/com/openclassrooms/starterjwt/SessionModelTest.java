package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Date;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SessionModelTest {

    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setUp() {
        // Initialisation du validateur
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();  // Initialisation des propriétés
    }

    @Test
    void testValidSession() {
        // Créer une instance valide de Session
        Session session = new Session();
        session.setName("Session 1")
               .setDate(new Date())
               .setDescription("A valid session description")
               .setTeacher(new Teacher())  // Assumer que l'entité Teacher existe et est valide
               .setUsers(Collections.emptyList());  // Assumer qu'il peut être vide pour ce test

        // Valider l'objet
        BindingResult result = validate(session);

        // Vérifier qu'il n'y a pas d'erreurs de validation
        assertFalse(result.hasErrors());
    }

    @Test
    void testInvalidSession_NameBlank() {
        // Créer une instance de Session avec un nom vide
        Session session = new Session();
        session.setName("")  // Nom vide
               .setDate(new Date())
               .setDescription("A session with blank name")
               .setTeacher(new Teacher())
               .setUsers(Collections.emptyList());

        // Valider l'objet
        BindingResult result = validate(session);

        // Vérifier qu'il y a des erreurs de validation
        assertTrue(result.hasErrors());

        // Vérifier l'erreur sur le champ "name"
        FieldError nameError = result.getFieldError("name");
        assertNotNull(nameError);
        assertEquals("name", nameError.getField());
    }

    @Test
    void testInvalidSession_DateNull() {
        // Créer une instance de Session avec une date null
        Session session = new Session();
        session.setName("Valid Session")
               .setDate(null)  // Date null
               .setDescription("A session with null date")
               .setTeacher(new Teacher())
               .setUsers(Collections.emptyList());

        // Valider l'objet
        BindingResult result = validate(session);

        // Vérifier qu'il y a des erreurs de validation
        assertTrue(result.hasErrors());

        // Vérifier l'erreur sur le champ "date"
        FieldError dateError = result.getFieldError("date");
        assertNotNull(dateError);
        assertEquals("date", dateError.getField());
    }

    @Test
    void testInvalidSession_DescriptionTooLong() {
        // Créer une instance de Session avec une description trop longue
        Session session = new Session();
        session.setName("Valid Session")
               .setDate(new Date())
               .setDescription("A".repeat(2501))  // Description trop longue (plus de 2500 caractères)
               .setTeacher(new Teacher())
               .setUsers(Collections.emptyList());

        // Valider l'objet
        BindingResult result = validate(session);

        // Vérifier qu'il y a des erreurs de validation
        assertTrue(result.hasErrors());

        // Vérifier l'erreur sur le champ "description"
        FieldError descriptionError = result.getFieldError("description");
        assertNotNull(descriptionError);
        assertEquals("description", descriptionError.getField());
    }

    private BindingResult validate(Object object) {
        BindingResult result = new org.springframework.validation.BeanPropertyBindingResult(object, "session");
        validator.validate(object, result);
        return result;
    }
}
