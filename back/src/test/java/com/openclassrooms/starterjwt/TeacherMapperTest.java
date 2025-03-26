package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherMapperTest {

    // Récupération du mapper
    private final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    void testToDto() {
        // Arrange : Crée un Teacher à convertir
        Teacher teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .build();

        // Act : Utilise le mapper pour convertir Teacher en TeacherDto
        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        // Assert : Vérifie que les valeurs sont correctement transférées
        assertEquals(teacher.getId(), teacherDto.getId());
        assertEquals(teacher.getFirstName(), teacherDto.getFirstName());
        assertEquals(teacher.getLastName(), teacherDto.getLastName());
    }

    @Test
    void testToEntity() {
        // Arrange : Crée un TeacherDto à convertir
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");

        // Act : Utilise le mapper pour convertir TeacherDto en Teacher
        Teacher teacher = teacherMapper.toEntity(teacherDto);

        // Assert : Vérifie que les valeurs sont correctement transférées
        assertEquals(teacherDto.getId(), teacher.getId());
        assertEquals(teacherDto.getFirstName(), teacher.getFirstName());
        assertEquals(teacherDto.getLastName(), teacher.getLastName());
    }
}
