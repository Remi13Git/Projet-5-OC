package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // Créez une instance de JwtUtils
        jwtUtils = new JwtUtils();

        // Utilisez la réflexion pour accéder aux champs privés
        Field jwtSecretField = JwtUtils.class.getDeclaredField("jwtSecret");
        Field jwtExpirationMsField = JwtUtils.class.getDeclaredField("jwtExpirationMs");

        // Permettre d'accéder aux champs privés
        jwtSecretField.setAccessible(true);
        jwtExpirationMsField.setAccessible(true);

        // Modifiez les valeurs des champs privés
        jwtSecretField.set(jwtUtils, "mySecretKey");
        jwtExpirationMsField.set(jwtUtils, 3600000);  // 1 heure pour l'expiration du JWT
    }

    @Test
    void testGenerateJwtToken() {
        // Simulez l'authentification ou passez un objet Authentication valide si nécessaire
        // Exemple pour générer un token et tester sa validité
        String token = jwtUtils.generateJwtToken(new MyAuthenticationMock()); // MyAuthenticationMock serait un mock de votre Authentication

        // Vérifiez que le token généré n'est pas nul ou vide
        assertNotNull(token);
        assertFalse(token.isEmpty());

        // Testez que le token contient bien le nom d'utilisateur ou d'autres éléments que vous attendez
        String username = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals("john_doe", username);
    }

    @Test
    void testValidateJwtToken() {
        String token = jwtUtils.generateJwtToken(new MyAuthenticationMock());  // Utilisez un mock ou un utilisateur authentifié pour générer un token

        // Testez la validation du token
        assertTrue(jwtUtils.validateJwtToken(token));

        // Testez un token expiré ou invalide en le modifiant manuellement ou en simulant une exception
        String invalidToken = "invalidToken";
        assertFalse(jwtUtils.validateJwtToken(invalidToken));
    }

    // Cette classe peut simuler un utilisateur authentifié pour l'exemple
    private static class MyAuthenticationMock implements Authentication {
        @Override
        public String getName() {
            return "john_doe";
        }
    
        @Override
        public Object getCredentials() {
            return "password";
        }
    
        @Override
        public Object getPrincipal() {
            return new UserDetailsImpl(1L, "john_doe", "John", "Doe", true, "password");
        }
    
        @Override
        public boolean isAuthenticated() {
            return true;
        }
    
        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            // Laissez cette méthode vide pour le mock
        }
    
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.emptyList();
        }
    
        // Implémentation de la méthode manquante getDetails() de l'interface Authentication
        @Override
        public Object getDetails() {
            return null;  // Vous pouvez retourner null ou un objet spécifique si nécessaire
        }
    }    
}
