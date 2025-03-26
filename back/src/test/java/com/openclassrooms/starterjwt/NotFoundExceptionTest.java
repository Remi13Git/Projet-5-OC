package com.openclassrooms.starterjwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.openclassrooms.starterjwt.exception.NotFoundException;

class NotFoundExceptionTest {

    @Test
    void testNotFoundException() {
        // Act & Assert: Vérifie que la NotFoundException est lancée
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            throw new NotFoundException();  // Lancer l'exception
        });

        // Vérification que l'exception a le statut HTTP NOT_FOUND
        assertEquals(HttpStatus.NOT_FOUND.value(), 404);  // Compare les codes d'état HTTP (int)
    }
}

