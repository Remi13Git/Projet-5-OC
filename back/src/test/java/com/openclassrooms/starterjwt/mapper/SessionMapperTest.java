package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SessionMapperTest {

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @InjectMocks
    private SessionMapperImpl sessionMapper;

    private SessionDto sessionDto;
    private Session session;
    private Teacher teacher;
    private User user;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);

        user = new User();
        user.setId(2L);

        sessionDto = new SessionDto(1L, "Test Session", new Date(), 1L, "Description", Arrays.asList(2L), null, null);
        session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setTeacher(teacher);
        session.setDescription("Description");
        session.setUsers(Collections.singletonList(user));

        // S'assurer que ces appels sont nécessaires
        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(2L)).thenReturn(user);
    }

    @Test
    void testToEntity() {
        Session result = sessionMapper.toEntity(sessionDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(sessionDto.getId());
        assertThat(result.getName()).isEqualTo(sessionDto.getName());
        assertThat(result.getDescription()).isEqualTo(sessionDto.getDescription());
        assertThat(result.getTeacher()).isEqualTo(teacher);
        assertThat(result.getUsers()).containsExactly(user);
    }

    @Test
    void testToEntityList() {
        List<Session> result = sessionMapper.toEntity(Arrays.asList(sessionDto));

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(sessionDto.getName());
    }

    @Test
    void testToDtoList() {
        List<SessionDto> result = sessionMapper.toDto(Arrays.asList(session));

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(session.getName());
    }
}

