package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        // Initialiser le mapper
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void testToDtoList() {
        // Créer une liste de Users à convertir
        User user1 = User.builder().id(1L).email("test1@example.com").firstName("John").lastName("Doe").password("password123").admin(true).build();
        User user2 = User.builder().id(2L).email("test2@example.com").firstName("Jane").lastName("Smith").password("password123").admin(false).build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        // Convertir la liste de Users en liste de UserDtos
        List<UserDto> userDtos = userMapper.toDto(users);

        // Vérifier que la conversion a bien fonctionné
        assertEquals(users.size(), userDtos.size());
        assertEquals(users.get(0).getId(), userDtos.get(0).getId());
        assertEquals(users.get(1).getId(), userDtos.get(1).getId());
    }

    @Test
    public void testToEntityList() {
        // Créer une liste de UserDtos à convertir
        UserDto userDto1 = new UserDto();
        userDto1.setId(1L);
        userDto1.setEmail("test1@example.com");
        userDto1.setFirstName("John");
        userDto1.setLastName("Doe");
        userDto1.setPassword("password123");
        userDto1.setAdmin(true);

        UserDto userDto2 = new UserDto();
        userDto2.setId(2L);
        userDto2.setEmail("test2@example.com");
        userDto2.setFirstName("Jane");
        userDto2.setLastName("Smith");
        userDto2.setPassword("password123");
        userDto2.setAdmin(false);

        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto1);
        userDtos.add(userDto2);

        // Convertir la liste de UserDtos en liste de Users
        List<User> users = userMapper.toEntity(userDtos);

        // Vérifier que la conversion a bien fonctionné
        assertEquals(userDtos.size(), users.size());
        assertEquals(userDtos.get(0).getId(), users.get(0).getId());
        assertEquals(userDtos.get(1).getId(), users.get(1).getId());
    }
}
