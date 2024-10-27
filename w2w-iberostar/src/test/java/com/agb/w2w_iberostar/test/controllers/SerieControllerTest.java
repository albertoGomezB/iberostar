package com.agb.w2w_iberostar.test.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.agb.w2w_iberostar.controller.SerieController;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agb.w2w_iberostar.dto.SerieDTO;
import com.agb.w2w_iberostar.service.ISerie;

public class SerieControllerTest {

    @Mock
    private ISerie seriesService;

    @InjectMocks
    private SerieController seriesController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(seriesController).build();
    }

    @Test
    void testGetAllSeries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/series"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetSeriesById_Found() throws Exception {

        Long seriesId = 1L;
        SerieDTO serieDTO = new SerieDTO("Serie Title", "Series Description");
        when(seriesService.getSerieById(seriesId)).thenReturn(Optional.of(serieDTO));

        mockMvc.perform(get("/series/{id}", seriesId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(new ObjectMapper().writeValueAsString(serieDTO)));
    }

    @Test
    void testGetSeriesById_NotFound() throws Exception {

        Long serieId = 1L;

        when(seriesService.getSerieById(serieId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies/{id}", serieId))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddSerie() throws Exception {

        SerieDTO serieDTO = new SerieDTO("Serie Title", "This is a description");

        doNothing().when(seriesService).addSerie(any(SerieDTO.class));

        mockMvc.perform(post("/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Serie Title\", \"description\": \"This is a description\"}"))
                .andExpect(status().isCreated());

        verify(seriesService, times(1)).addSerie(argThat(
                arg -> arg.getName().equals(serieDTO.getName())
                        && arg.getDescription().equals(serieDTO.getDescription())));
    }

    @Test
    void testUpdateSerie() {

        Long serieId = 1L;
        SerieDTO serieDTO = new SerieDTO("Updated Title", "Updated Description");

        doNothing().when(seriesService).updateSerie(eq(serieId), any(SerieDTO.class));
    }

    @Test
    void testDeleteSerie() {

        Long serieId = 1L;
        doNothing().when(seriesService).deleteSerie(serieId);

    }

}