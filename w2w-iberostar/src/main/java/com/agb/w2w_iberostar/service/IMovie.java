package com.agb.w2w_iberostar.service;

import java.util.List;
import java.util.Optional;

import com.agb.w2w_iberostar.dto.MovieDTO;

public interface IMovie {

    List<MovieDTO> getAllMovies();

    Optional<MovieDTO> getMovieById(Long id);

    Optional<MovieDTO> getMovieByName(String name);

    void addMovie(MovieDTO movieDTO);

    void updateMovie(Long id, MovieDTO movieDTO);

    void deleteMovie(Long id);

}
