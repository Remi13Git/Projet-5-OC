package com.openclassrooms.starterjwt;

import org.junit.jupiter.api.Test;

import com.openclassrooms.starterjwt.models.Teacher;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherModelTest {

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
        assertEquals(teacher1, teacher2);
        assertNotEquals(teacher1, teacher3);
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
}
