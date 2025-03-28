package com.openclassrooms.starterjwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.security.jwt.AuthEntryPointJwt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthEntryPointJwtTest {

    private AuthEntryPointJwt authEntryPointJwt;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AuthenticationException authException;
    private ByteArrayOutputStream responseOutputStream;

    @BeforeEach
    void setUp() throws IOException {
        authEntryPointJwt = new AuthEntryPointJwt();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        authException = new AuthenticationException("Unauthorized access") {};

        // Simulating response output stream
        responseOutputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                responseOutputStream.write(b);
            }

            @Override
            public boolean isReady() {
                return true; // Required by ServletOutputStream
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
            }
        };

        when(response.getOutputStream()).thenReturn(servletOutputStream);
    }

    @Test
    void testCommence() throws Exception {
        // Mock request path
        when(request.getServletPath()).thenReturn("/some/path");

        // Invoke commence method
        authEntryPointJwt.commence(request, response, authException);

        // Verify response status and content type
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Parse JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(responseOutputStream.toString(), Map.class);

        // Assertions
        assertEquals(401, responseMap.get("status"));
        assertEquals("Unauthorized", responseMap.get("error"));
        assertEquals("Unauthorized access", responseMap.get("message"));
        assertEquals("/some/path", responseMap.get("path"));
    }
}
