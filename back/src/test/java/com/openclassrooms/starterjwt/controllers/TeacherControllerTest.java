package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Arrays;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class) 
public class TeacherControllerTest {

    @Mock
    private TeacherService teacherService;  // Mocker le service

    @Mock
    private TeacherMapper teacherMapper;  // Mocker le mapper

    @InjectMocks
    private TeacherController teacherController;  // Injecter les mocks dans le contrôleur

    @Test
    public void testFindById() {
        Long teacherId = 1L;

        // Arrange: préparez un objet Teacher simulé
        Teacher mockTeacher = new Teacher();
        mockTeacher.setId(teacherId);
        mockTeacher.setFirstName("John");
        mockTeacher.setLastName("Doe");

        // Créez un objet TeacherDto simulé
        TeacherDto mockTeacherDto = new TeacherDto();
        mockTeacherDto.setId(teacherId);
        mockTeacherDto.setFirstName("John");
        mockTeacherDto.setLastName("Doe");

        // Simulez le comportement de la méthode findById() pour renvoyer un Teacher
        when(teacherService.findById(teacherId)).thenReturn(mockTeacher);

        // Simulez le comportement du mapper pour renvoyer un TeacherDto
        when(teacherMapper.toDto(mockTeacher)).thenReturn(mockTeacherDto);

        // Act: appelez la méthode du contrôleur
        ResponseEntity<?> response = teacherController.findById(teacherId.toString());

        // Assert: vérifiez que la réponse est correcte
        assertEquals(200, response.getStatusCodeValue());  // Vérifie que le statut est OK (200)
        assertNotNull(response.getBody());  // Vérifie que la réponse contient un corps
        assertTrue(response.getBody() instanceof TeacherDto);  // Vérifie que le corps est une instance de TeacherDto

        // Vérifiez que la méthode findById a été appelée avec le bon ID
        verify(teacherService, times(1)).findById(teacherId);
        verify(teacherMapper, times(1)).toDto(mockTeacher);
    }

    @Test
    public void testFindByIdNotFound() {
        Long teacherId = 1L;

        // Arrange: Simulez le cas où l'enseignant n'est pas trouvé (renvoi de null)
        when(teacherService.findById(teacherId)).thenReturn(null);

        // Act: appelez la méthode du contrôleur
        ResponseEntity<?> response = teacherController.findById(teacherId.toString());

        // Assert: vérifiez que la réponse est correcte
        assertEquals(404, response.getStatusCodeValue());  // Vérifie que le statut est NOT FOUND (404)
    }

    @Test
    public void testFindByIdBadRequest() {
        // Act: appelez la méthode du contrôleur avec un ID invalide
        ResponseEntity<?> response = teacherController.findById("invalidId");

        // Assert: vérifiez que la réponse est correcte
        assertEquals(400, response.getStatusCodeValue());  // Vérifie que le statut est BAD REQUEST (400)
    }

    @Test
    public void testFindAll() {
        // Arrange: Créez une liste simulée d'enseignants
        Teacher teacher1 = new Teacher(1L, "John", "Doe", null, null);
        Teacher teacher2 = new Teacher(2L, "Jane", "Smith", null, null);
        List<Teacher> mockTeachers = Arrays.asList(teacher1, teacher2);

        // Créez une liste simulée de TeacherDto
        TeacherDto teacherDto1 = new TeacherDto(1L, "John", "Doe", null, null);
        TeacherDto teacherDto2 = new TeacherDto(2L, "Jane", "Smith", null, null);
        List<TeacherDto> mockTeacherDtos = Arrays.asList(teacherDto1, teacherDto2);

        // Simulez le comportement de la méthode findAll() pour renvoyer la liste d'enseignants
        when(teacherService.findAll()).thenReturn(mockTeachers);

        // Simulez le comportement du mapper pour renvoyer une liste de TeacherDto
        when(teacherMapper.toDto(mockTeachers)).thenReturn(mockTeacherDtos);

        // Act: appelez la méthode du contrôleur
        ResponseEntity<?> response = teacherController.findAll();

        // Assert: vérifiez que la réponse est correcte
        assertEquals(200, response.getStatusCodeValue());  // Vérifie que le statut est OK (200)
        assertNotNull(response.getBody());  // Vérifie que la réponse contient un corps
        assertTrue(response.getBody() instanceof List);  // Vérifie que le corps est une instance de List
        assertEquals(2, ((List<?>) response.getBody()).size());  // Vérifie que la liste contient 2 éléments

        // Vérifiez que la méthode findAll a été appelée une seule fois
        verify(teacherService, times(1)).findAll();
        verify(teacherMapper, times(1)).toDto(mockTeachers);
    }

}
