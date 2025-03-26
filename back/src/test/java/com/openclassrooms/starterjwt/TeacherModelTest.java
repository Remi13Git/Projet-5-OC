package com.openclassrooms.starterjwt;

import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.models.Teacher;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherModelTest {  // Modification du nom de la classe ici

    private Validator validator;

    // Initialiser le validateur pour tester les contraintes
    public TeacherModelTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testTeacherValid() {
        // Créer un Teacher valide
        Teacher teacher = Teacher.builder()
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Vérifier que l'objet n'a pas de violation de contrainte
        Set violations = validator.validate(teacher);
        assertTrue(violations.isEmpty(), "Le teacher ne doit pas violer de contraintes");
    }

    @Test
    public void testTeacherInvalidFirstName() {
        // Créer un Teacher avec un prénom invalide (trop court)
        Teacher teacher = Teacher.builder()
                .firstName("")  // Ne respecte pas @NotBlank
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Vérifier que l'objet a des violations de contrainte
        Set violations = validator.validate(teacher);
        assertFalse(violations.isEmpty(), "Le prénom ne doit pas être vide");
    }

    @Test
    public void testTeacherToString() {
        Teacher teacher = Teacher.builder()
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Tester la méthode toString()
        String expectedString = "Teacher(id=null, firstName=John, lastName=Doe, createdAt=" + teacher.getCreatedAt() + ", updatedAt=" + teacher.getUpdatedAt() + ")";
        assertTrue(teacher.toString().contains("John"));
        assertTrue(teacher.toString().contains("Doe"));
    }

    @Test
    public void testTeacherEquality() {
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Teacher teacher2 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Teacher teacher3 = Teacher.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Vérifier l'égalité des objets
        assertEquals(teacher1, teacher2);  // Ils doivent être égaux car leurs ID sont les mêmes
        assertNotEquals(teacher1, teacher3);  // Différents car leurs IDs sont différents
    }

    @Test
    public void testTeacherHashCode() {
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Teacher teacher2 = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Vérifier que les objets ayant les mêmes valeurs de champ ont le même hashcode
        assertEquals(teacher1.hashCode(), teacher2.hashCode());
    }

    @Test
    public void testIdGeneration() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        
        // L'ID devrait être nul avant la persistance en base de données
        assertNull(teacher.getId());

        // Après la persistance, l'ID devrait être généré automatiquement par la base de données.
        // Cela ne peut pas être testé ici directement sans interaction avec la base de données.
    }
}
