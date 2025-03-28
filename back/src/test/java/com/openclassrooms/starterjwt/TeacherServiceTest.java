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
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
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
        // Arrange
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher));

        // Act
        List<Teacher> teachers = teacherService.findAll();

        // Assert
        assertNotNull(teachers);
        assertFalse(teachers.isEmpty());
        assertEquals(1, teachers.size());
        assertEquals(teacher.getId(), teachers.get(0).getId());
    }

    @Test
    void testFindById_Found() {
        // Arrange
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        // Act
        Teacher result = teacherService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(teacher.getId(), result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }
}
