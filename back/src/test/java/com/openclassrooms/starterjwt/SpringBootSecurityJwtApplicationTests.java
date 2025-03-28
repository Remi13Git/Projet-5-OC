package com.openclassrooms.starterjwt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
public class SpringBootSecurityJwtApplicationTests {

    @Test
    void testMainMethod() {
        // Vérifie que l'exécution de main ne lève pas d'exception
        assertDoesNotThrow(() -> SpringBootSecurityJwtApplication.main(new String[]{}));
    }
}
