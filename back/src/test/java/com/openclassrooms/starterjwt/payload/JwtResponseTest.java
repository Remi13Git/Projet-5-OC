package com.openclassrooms.starterjwt.payload;

import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtResponseTest {

    @Test
    void testSetters() {
        // Créez un objet JwtResponse vide
        JwtResponse jwtResponse = new JwtResponse(null, null, null, null, null, null);

        // Utilisez les setters pour affecter les valeurs
        jwtResponse.setToken("newToken");
        jwtResponse.setId(2L);
        jwtResponse.setUsername("jane_doe");
        jwtResponse.setFirstName("Jane");
        jwtResponse.setLastName("Doe");
        jwtResponse.setAdmin(false);

        // Vérifiez que les setters affectent correctement les valeurs
        assertEquals("newToken", jwtResponse.getToken());
        assertEquals(2L, jwtResponse.getId());
        assertEquals("jane_doe", jwtResponse.getUsername());
        assertEquals("Jane", jwtResponse.getFirstName());
        assertEquals("Doe", jwtResponse.getLastName());
        assertFalse(jwtResponse.getAdmin());
    }
}
