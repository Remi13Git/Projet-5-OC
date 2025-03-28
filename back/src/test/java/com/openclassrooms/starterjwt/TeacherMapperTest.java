package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TeacherMapperTest {

    @Autowired
    private TeacherMapper teacherMapper;

    @Test
    void testToDtoList() {
        // Arrange : Crée une liste de Teachers à convertir
        Teacher teacher1 = Teacher.builder().id(1L).firstName("John").lastName("Doe").build();
        Teacher teacher2 = Teacher.builder().id(2L).firstName("Jane").lastName("Smith").build();
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher1);
        teachers.add(teacher2);

        // Act : Utilise le mapper pour convertir la liste de Teachers en liste de TeacherDtos
        List<TeacherDto> teacherDtos = teacherMapper.toDto(teachers);

        // Assert : Vérifie que la conversion a bien fonctionné
        assertEquals(teachers.size(), teacherDtos.size());
        assertEquals(teachers.get(0).getId(), teacherDtos.get(0).getId());
        assertEquals(teachers.get(1).getId(), teacherDtos.get(1).getId());
    }

    @Test
    void testToEntityList() {
        // Arrange : Crée une liste de TeacherDtos à convertir
        TeacherDto teacherDto1 = new TeacherDto();
        teacherDto1.setId(1L);
        teacherDto1.setFirstName("John");
        teacherDto1.setLastName("Doe");

        TeacherDto teacherDto2 = new TeacherDto();
        teacherDto2.setId(2L);
        teacherDto2.setFirstName("Jane");
        teacherDto2.setLastName("Smith");

        List<TeacherDto> teacherDtos = new ArrayList<>();
        teacherDtos.add(teacherDto1);
        teacherDtos.add(teacherDto2);

        // Act : Utilise le mapper pour convertir la liste de TeacherDtos en liste de Teachers
        List<Teacher> teachers = teacherMapper.toEntity(teacherDtos);

        // Assert : Vérifie que la conversion a bien fonctionné
        assertEquals(teacherDtos.size(), teachers.size());
        assertEquals(teacherDtos.get(0).getId(), teachers.get(0).getId());
        assertEquals(teacherDtos.get(1).getId(), teachers.get(1).getId());
    }
}
