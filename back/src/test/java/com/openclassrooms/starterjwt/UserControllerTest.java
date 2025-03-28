package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .admin(false)
                .build();

        userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAdmin(user.isAdmin());
    }

    @Test
    public void testFindById_UserExists() {
        // Simuler que l'utilisateur est trouvé
        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        // Appeler la méthode findById
        ResponseEntity<?> response = userController.findById("1");

        // Vérifications
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(userDto, response.getBody());
    }

    @Test
    public void testFindById_UserNotFound() {
        // Simuler que l'utilisateur n'est pas trouvé
        when(userService.findById(1L)).thenReturn(null);

        // Appeler la méthode findById
        ResponseEntity<?> response = userController.findById("1");

        // Vérifications
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testFindById_InvalidId() {
        // Appeler la méthode findById avec un ID non valide
        ResponseEntity<?> response = userController.findById("abc");

        // Vérifications
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testDelete_UserExistsAndAuthorized() {
        // Simuler que l'utilisateur est trouvé
        when(userService.findById(1L)).thenReturn(user);

        // Simuler l'authentification
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(user.getEmail());
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Appeler la méthode delete
        ResponseEntity<?> response = userController.save("1");

        // Vérifications
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).delete(1L);
    }

    @Test
    public void testDelete_UserNotFound() {
        // Simuler que l'utilisateur n'est pas trouvé
        when(userService.findById(1L)).thenReturn(null);

        // Appeler la méthode delete
        ResponseEntity<?> response = userController.save("1");

        // Vérifications
        assertEquals(404, response.getStatusCodeValue());
        verify(userService, never()).delete(anyLong());
    }

    @Test
    public void testDelete_UnauthorizedUser() {
        // Simuler que l'utilisateur est trouvé
        when(userService.findById(1L)).thenReturn(user);

        // Simuler l'authentification d'un autre utilisateur
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("other@example.com"); // Email différent
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Appeler la méthode delete
        ResponseEntity<?> response = userController.save("1");

        // Vérifications
        assertEquals(401, response.getStatusCodeValue()); // Unauthorized
        verify(userService, never()).delete(anyLong());
    }

    @Test
    public void testDelete_InvalidId() {
        // Appeler la méthode delete avec un ID non valide
        ResponseEntity<?> response = userController.save("abc");

        // Vérifications
        assertEquals(400, response.getStatusCodeValue());
        verify(userService, never()).delete(anyLong());
    }
}
