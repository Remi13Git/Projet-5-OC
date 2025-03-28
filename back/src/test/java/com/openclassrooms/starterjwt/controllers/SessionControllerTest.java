package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Date;
import java.util.Arrays;

class SessionControllerTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionController sessionController;

    private MockMvc mockMvc;

    private SessionDto sessionDto;
    private Session session;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks avec Mockito
        MockitoAnnotations.openMocks(this);

        // Initialisation des objets à utiliser dans les tests
        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Session 1");
        sessionDto.setDescription("Session Description");
        sessionDto.setTeacher_id(2L);
        
        session = new Session();
        session.setId(1L);
        session.setName("Session 1");
        session.setDescription("Session Description");

        // Configurer MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(sessionController).build();
    }

    @Test
    void testCreateSession() throws Exception {
        // Création d'un DTO de session avec des valeurs valides
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("New Session");
        sessionDto.setDescription("This is a new session description");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L); // Un ID d'enseignant valide

        // Création de l'objet Session avec le Builder
        Session session = Session.builder()
                .id(1L)
                .name("New Session")
                .date(new Date())
                .teacher(new Teacher())
                .description("This is a new session description")
                .users(null)
                .build();

        // Simulation du comportement du service
        when(sessionService.create(any(Session.class))).thenReturn(session);

        // Appel du contrôleur
        mockMvc.perform(post("/api/session")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(sessionDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSession() throws Exception {
        // Création d'un DTO de session avec des valeurs mises à jour
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Updated Session");
        sessionDto.setDescription("This is an updated session description");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L); // Un ID d'enseignant valide

        // Création de l'objet Session avec le Builder pour simuler la session mise à jour
        Session updatedSession = Session.builder()
                .id(1L)
                .name("Updated Session")
                .date(new Date())
                .teacher(new Teacher())
                .description("This is an updated session description")
                .users(null)
                .build();

        // Simulation du comportement du service pour la mise à jour
        when(sessionService.update(eq(1L), any(Session.class))).thenReturn(updatedSession);

        // Appel du contrôleur pour mettre à jour la session
        mockMvc.perform(put("/api/session/1") // Remarque l'URL avec l'ID de la session à mettre à jour
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(sessionDto)))
                .andExpect(status().isOk()); // Vérifie que la réponse est 200 OK
    }

    @Test
    void testFindAllSessions() throws Exception {
        // Création de quelques sessions pour simuler la liste retournée par le service
        List<Session> sessions = Arrays.asList(
                Session.builder()
                        .id(1L)
                        .name("Session 1")
                        .date(new Date())
                        .teacher(new Teacher())
                        .description("Description for Session 1")
                        .users(null)
                        .build(),
                Session.builder()
                        .id(2L)
                        .name("Session 2")
                        .date(new Date())
                        .teacher(new Teacher())
                        .description("Description for Session 2")
                        .users(null)
                        .build()
        );

        // Simulation du comportement du service
        when(sessionService.findAll()).thenReturn(sessions);

        // Simulation du comportement du mapper avec l'ordre correct des paramètres
        List<SessionDto> sessionDtos = Arrays.asList(
                new SessionDto(1L, "Session 1", new Date(), 1L, "Description for Session 1", null, null, null),
                new SessionDto(2L, "Session 2", new Date(), 2L, "Description for Session 2", null, null, null)
        );
        when(sessionMapper.toDto(sessions)).thenReturn(sessionDtos);

        // Appel du contrôleur pour récupérer toutes les sessions
        mockMvc.perform(get("/api/session")
                .contentType("application/json"))
                .andExpect(status().isOk()) // Vérifie que le statut HTTP est 200 OK
                .andExpect(jsonPath("$.length()").value(2)) // Vérifie qu'on reçoit bien 2 sessions
                .andExpect(jsonPath("$[0].name").value("Session 1")) // Vérifie le nom de la première session
                .andExpect(jsonPath("$[1].name").value("Session 2")); // Vérifie le nom de la deuxième session

        // Vérifie que le service a bien été appelé une fois
        verify(sessionService, times(1)).findAll();
    }

    @Test
    void testFindById() throws Exception {
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Session 1"))
                .andExpect(jsonPath("$.description").value("Session Description"));
    }

    @Test
    void testDeleteSession() throws Exception {
        when(sessionService.getById(1L)).thenReturn(session);
        doNothing().when(sessionService).delete(1L);

        mockMvc.perform(delete("/api/session/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testParticipate() throws Exception {
        doNothing().when(sessionService).participate(1L, 2L);

        mockMvc.perform(post("/api/session/1/participate/2"))
                .andExpect(status().isOk());
    }

    @Test
    void testNoLongerParticipate() throws Exception {
        doNothing().when(sessionService).noLongerParticipate(1L, 2L);

        mockMvc.perform(delete("/api/session/1/participate/2"))
                .andExpect(status().isOk());
    }

    @Test
    void testBadRequestOnInvalidId() throws Exception {
        mockMvc.perform(get("/api/session/invalid"))
                .andExpect(status().isBadRequest());
    }
}
