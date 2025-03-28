package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionBuilderTest {
    @Test
    void testToString() {
        Session.SessionBuilder builder = Session.builder()
                .id(1L)
                .name("Physics Session");

        String result = builder.toString();
        assertNotNull(result);
        assertTrue(result.contains("Physics Session"));
    }
}
