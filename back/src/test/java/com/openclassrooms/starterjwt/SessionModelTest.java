package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SessionModelTest {

    private Session session;
    private Session anotherSession;

    @BeforeEach
    void setUp() {
        session = new Session();
        session.setId(1L);
        session.setName("Math Session");
        session.setDate(new Date());
        session.setDescription("A session about advanced mathematics.");
        session.setTeacher(new Teacher());
        session.setUsers(Collections.emptyList());

        anotherSession = new Session();
        anotherSession.setId(1L);
        anotherSession.setName("Math Session");
        anotherSession.setDate(session.getDate());
        anotherSession.setDescription("A session about advanced mathematics.");
        anotherSession.setTeacher(session.getTeacher());
        anotherSession.setUsers(session.getUsers());
    }

    @Test
    void testToString() {
        String result = session.toString();
        assertNotNull(result);
        assertTrue(result.contains("Math Session"));
    }

    @Test
    void testEquals() {
        assertEquals(session, anotherSession);
        anotherSession.setId(2L);
        assertNotEquals(session, anotherSession);
    }

    @Test
    void testHashCode() {
        assertEquals(session.hashCode(), anotherSession.hashCode());
        anotherSession.setId(2L);
        assertNotEquals(session.hashCode(), anotherSession.hashCode());
    }

    @Test
    void testGettersAndSetters() {
        session.setCreatedAt(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());

        assertNotNull(session.getId());
        assertEquals("Math Session", session.getName());
        assertNotNull(session.getDate());
        assertEquals("A session about advanced mathematics.", session.getDescription());
        assertNotNull(session.getTeacher());
        assertNotNull(session.getUsers());
        assertNotNull(session.getCreatedAt());
        assertNotNull(session.getUpdatedAt());
    }
}
