package com.agb.w2w_iberostar.test.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.agb.w2w_iberostar.controller.MovieController;
import com.agb.w2w_iberostar.dto.MovieDTO;
import com.agb.w2w_iberostar.service.IMovie;

public class MovieControllerTest {

    @Mock
    private IMovie movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void testGetAllMovies() throws Exception {

        when(movieService.getAllMovies()).thenReturn(List.of(new MovieDTO("Movie Title", "Description")));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetMovieById_Success() throws Exception {

        Long movieId = 1L;
        MovieDTO movie = new MovieDTO("Movie Title", "Comedy");

        when(movieService.getMovieById(movieId)).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/movies/{id}", movieId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Movie Title"))
                .andExpect(jsonPath("$.genre").value("Comedy"));
    }

    @Test
    public void testGetMovieById_NotFound() throws Exception {

        Long movieId = 1L;

        when(movieService.getMovieById(movieId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies/{id}", movieId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddMovie() throws Exception {

        MovieDTO movieDTO = new MovieDTO("Movie Title", "Comedy");

        doNothing().when(movieService).addMovie(any(MovieDTO.class));

        mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Movie Title\", \"genre\": \"Comedy\"}"))
                .andExpect(status().isCreated());

        verify(movieService, times(1)).addMovie(argThat(
                arg -> arg.getName().equals(movieDTO.getName()) && arg.getGenre().equals(movieDTO.getGenre())));
    }

    @Test
    public void testUpdateMovie() throws Exception {

        Long movieId = 1L;
        MovieDTO movieDTO = new MovieDTO("Movie Title", "Comedy");

        doNothing().when(movieService).updateMovie(eq(movieId), any(MovieDTO.class));
    }

    @Test
    public void testDeleteMovie() throws Exception {

        Long movieId = 1L;

        mockMvc.perform(delete("/movies/{id}", movieId))
                .andExpect(status().isOk());

        verify(movieService, times(1)).deleteMovie(movieId);
    }
}