package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.User;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UserModelTest {

    @Test
    public void testUserToString() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("password123");
        user.setAdmin(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        String userString = user.toString();
        assertTrue(userString.contains("John"));
        assertTrue(userString.contains("Doe"));
    }

    @Test
    public void testUserEquality() {
        User user1 = new User(1L, "test@example.com", "John", "Doe", "password123", true, LocalDateTime.now(), LocalDateTime.now());
        User user2 = new User(1L, "test@example.com", "John", "Doe", "password123", true, LocalDateTime.now(), LocalDateTime.now());
        User user3 = new User(2L, "another@example.com", "Jane", "Doe", "password123", true, LocalDateTime.now(), LocalDateTime.now());

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
    }

    @Test
    public void testUserHashCode() {
        User user1 = new User(1L, "test@example.com", "John", "Doe", "password123", true, LocalDateTime.now(), LocalDateTime.now());
        User user2 = new User(1L, "test@example.com", "John", "Doe", "password123", true, LocalDateTime.now(), LocalDateTime.now());

        assertEquals(user1.hashCode(), user2.hashCode());
    }
}