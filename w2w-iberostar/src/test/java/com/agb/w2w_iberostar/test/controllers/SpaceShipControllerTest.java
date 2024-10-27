package com.agb.w2w_iberostar.test.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.agb.w2w_iberostar.controller.SpaceShipController;
import com.agb.w2w_iberostar.dto.MovieDTO;
import com.agb.w2w_iberostar.dto.SerieDTO;
import com.agb.w2w_iberostar.dto.SpaceShipDTO;
import com.agb.w2w_iberostar.service.ISpaceShip;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpaceShipControllerTest {

    @Mock
    private ISpaceShip spaceShipService;

    @InjectMocks
    private SpaceShipController spaceShipController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(spaceShipController).build();
    }

    @Test
    void testGetAllSpaceships() throws Exception {
        List<SpaceShipDTO> spaceshipList = new ArrayList<>();
        spaceshipList.add(new SpaceShipDTO("Spaceship One", null, null));
        spaceshipList.add(new SpaceShipDTO("Spaceship Two", null, null));

        Page<SpaceShipDTO> page = new PageImpl<>(spaceshipList, PageRequest.of(0, 10), spaceshipList.size());

        when(spaceShipService.getAllSpaceShips(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/spaceships"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(page)));
    }

    @Test
    void testGetSpaceShipById_Found() throws Exception {

        Long spaceShipId = 1L;
        SpaceShipDTO spaceShipDTO = new SpaceShipDTO("X-Wing", List.of(
                new SerieDTO("Star Wars", "Epic space opera")),
                List.of(new MovieDTO("Star Wars: A New Hope", "The first Star Wars movie")));

        when(spaceShipService.getSpaceShipById(spaceShipId)).thenReturn(Optional.of(spaceShipDTO));

        mockMvc.perform(get("/spaceships/{id}", spaceShipId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(spaceShipDTO)));
    }

    @Test
    void testGetSpaceshipById_Found() throws Exception {
        Long spaceshipId = 1L;
        SpaceShipDTO spaceshipDTO = new SpaceShipDTO("Spaceship One", null, null);
        when(spaceShipService.getSpaceShipById(spaceshipId)).thenReturn(Optional.of(spaceshipDTO));

        mockMvc.perform(MockMvcRequestBuilders.get("/spaceships/{id}", spaceshipId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(spaceshipDTO)));
    }

    @Test
    void testAddSpaceShip() throws Exception {

        SpaceShipDTO spaceshipDTO = new SpaceShipDTO("Spaceship Title", null, null);
        doNothing().when(spaceShipService).addSpaceShip(any(SpaceShipDTO.class));

        ResponseEntity<Void> response = spaceShipController.addSpaceShip(spaceshipDTO);

        verify(spaceShipService).addSpaceShip(spaceshipDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testUpdateSpaceShip() throws Exception {

        Long spaceshipId = 1L;
        SpaceShipDTO spaceshipDTO = new SpaceShipDTO("Updated Spaceship Title", null, null);

        doNothing().when(spaceShipService).updateSpaceShip(eq(spaceshipId), any(SpaceShipDTO.class));

        ResponseEntity<Void> response = spaceShipController.updateSpaceShip(spaceshipId, spaceshipDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(spaceShipService).updateSpaceShip(spaceshipId, spaceshipDTO);
    }

    @Test
    void testDeleteSpaceShip() throws Exception {

        Long spaceshipId = 1L;
        doNothing().when(spaceShipService).deleteSpaceShip(spaceshipId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/spaceships/{id}", spaceshipId))
                .andExpect(status().isOk());

        verify(spaceShipService).deleteSpaceShip(spaceshipId);
    }
}