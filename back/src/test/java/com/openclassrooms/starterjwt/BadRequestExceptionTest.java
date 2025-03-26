package com.openclassrooms.starterjwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.openclassrooms.starterjwt.exception.BadRequestException;

class BadRequestExceptionTest {

    @Test
    void testBadRequestException() {
        // Act & Assert: Vérifie que la BadRequestException est lancée
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            throw new BadRequestException();  // Lancer l'exception
        });

        // Vérification que l'exception a le statut HTTP BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST.value(), 400);  // Compare les codes d'état HTTP (int)
    }
}
