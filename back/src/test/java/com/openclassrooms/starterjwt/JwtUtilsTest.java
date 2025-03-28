package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
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
        String token = jwtUtils.generateJwtToken(new MyAuthenticationMock());

        assertNotNull(token);
        assertFalse(token.isEmpty());

        String username = jwtUtils.getUserNameFromJwtToken(token);
        assertEquals("john_doe", username);
    }

    @Test
    void testValidateJwtToken() {
        // Génère un token valide
        String token = jwtUtils.generateJwtToken(new MyAuthenticationMock());
        assertTrue(jwtUtils.validateJwtToken(token));  // Vérifie que le token valide retourne true

        // Teste un token invalide (SignatureException)
        String invalidToken = "invalidToken";
        assertFalse(jwtUtils.validateJwtToken(invalidToken));

        // Teste un token expiré (ExpiredJwtException)
        String expiredToken = jwtUtils.generateJwtToken(new MyAuthenticationMock());
        String expiredTokenSimulated = simulateExpiredToken(expiredToken);
        assertFalse(jwtUtils.validateJwtToken(expiredTokenSimulated));

        // Teste un token malformé (MalformedJwtException)
        String malformedToken = "malformed.token";
        assertFalse(jwtUtils.validateJwtToken(malformedToken));

        // Teste un token avec une signature invalide (SignatureException)
        String signatureInvalidToken = "signature.invalid.token";
        assertFalse(jwtUtils.validateJwtToken(signatureInvalidToken));

        // Teste un token non supporté (UnsupportedJwtException)
        String unsupportedToken = "unsupported.jwt.token";
        assertFalse(jwtUtils.validateJwtToken(unsupportedToken));

        // Teste un token avec un argument illégal (IllegalArgumentException)
        String illegalArgumentToken = "illegal.argument.token";
        assertFalse(jwtUtils.validateJwtToken(illegalArgumentToken));
    }

    private String simulateExpiredToken(String validToken) {
        // Simule un jeton expiré en modifiant la date d'expiration du jeton
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("mySecretKey")
                    .parseClaimsJws(validToken)
                    .getBody();

            claims.setExpiration(new Date(0));  // Expire instantanément

            return Jwts.builder()
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS512, "mySecretKey")
                    .compact();
        } catch (JwtException e) {
            throw new RuntimeException("Error while simulating expired token", e);
        }
    }

    // Cette classe simule un utilisateur authentifié
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
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.emptyList();
        }

        @Override
        public Object getDetails() {
            return null;
        }
    }
}
