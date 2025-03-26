package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;  // Assurez-vous d'importer cette classe

@ExtendWith(MockitoExtension.class)  // Cette annotation active l'intégration avec Mockito
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;  // Mock du repository

    @InjectMocks
    private TeacherService teacherService;  // Service à tester

    private Teacher teacher;  // Objet Teacher fictif

    @BeforeEach
    void setUp() {
        // Initialisation d'un teacher fictif pour les tests
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
    }

    @Test
    void testFindAll() {
        // Arrange: Simuler la méthode findAll() du repository
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));

        // Act: Appeler la méthode findAll() du service
        var teachers = teacherService.findAll();

        // Assert: Vérifier que la méthode retourne bien la liste des enseignants
        assertNotNull(teachers);  // Vérifie que la liste n'est pas nulle
        assertFalse(teachers.isEmpty());  // Vérifie que la liste n'est pas vide
        assertEquals(1, teachers.size());  // Vérifie qu'il y a 1 enseignant dans la liste
        assertEquals(teacher.getId(), teachers.get(0).getId());  // Vérifie que l'ID correspond
    }

    @Test
    void testFindById_Found() {
        // Arrange: Simuler la méthode findById() du repository pour renvoyer un enseignant
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        // Act: Appeler la méthode findById() du service
        Teacher result = teacherService.findById(1L);

        // Assert: Vérifier que l'enseignant a bien été retrouvé
        assertNotNull(result);  // Vérifie que l'enseignant n'est pas nul
        assertEquals(teacher.getId(), result.getId());  // Vérifie que l'ID correspond
        assertEquals("John", result.getFirstName());  // Vérifie que le prénom est correct
        assertEquals("Doe", result.getLastName());  // Vérifie que le nom est correct
    }

    @Test
    void testFindById_NotFound() {
        // Arrange: Simuler la méthode findById() du repository pour renvoyer un Optional vide
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        // Act: Appeler la méthode findById() du service
        Teacher result = teacherService.findById(1L);

        // Assert: Vérifier que l'enseignant n'a pas été trouvé
        assertNull(result);  // Vérifie que le résultat est nul
    }

    @Test
    void testFindById_Exception() {
        // Arrange: Simuler une exception levée par le repository
        when(teacherRepository.findById(1L)).thenThrow(new RuntimeException("Database error"));

        // Act & Assert: Vérifier qu'une exception est bien lancée
        assertThrows(RuntimeException.class, () -> teacherService.findById(1L));
    }
}
