package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        // Initialiser le mapper
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void testUserToUserDto() {
        // Créer un User
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .build();

        // Convertir en UserDto
        UserDto userDto = userMapper.toDto(user);

        // Vérifier que la conversion s'est bien déroulée
        assertNotNull(userDto);
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.isAdmin(), userDto.isAdmin());
    }

    @Test
    public void testUserDtoToUser() {
        // Créer un UserDto
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setPassword("password123");
        userDto.setAdmin(true);

        // Convertir en User
        User user = userMapper.toEntity(userDto);

        // Vérifier que la conversion s'est bien déroulée
        assertNotNull(user);
        assertEquals(userDto.getId(), user.getId());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.isAdmin(), user.isAdmin());
    }
}
