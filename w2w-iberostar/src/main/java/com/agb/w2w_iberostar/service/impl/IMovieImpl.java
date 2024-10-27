package com.agb.w2w_iberostar.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agb.w2w_iberostar.dto.MovieDTO;
import com.agb.w2w_iberostar.exception.DuplicateEntityException;
import com.agb.w2w_iberostar.mapper.MovieMapper;
import com.agb.w2w_iberostar.model.Movie;
import com.agb.w2w_iberostar.repository.MovieRepository;
import com.agb.w2w_iberostar.service.IMovie;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IMovieImpl implements IMovie {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "movies")
    public List<MovieDTO> getAllMovies() {

        return movieRepository.findAll().stream()
                .map(movieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "movies", key = "#id")
    public Optional<MovieDTO> getMovieById(Long id) {

        return movieRepository.findById(id)
                .map(movieMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "movies", key = "#name")
    public Optional<MovieDTO> getMovieByName(String name) {

        return movieRepository.findByName(name)
                .map(movieMapper::toDTO);
    }

    @Override
    @Transactional
    @CacheEvict(value = "movies", allEntries = true)
    public void addMovie(MovieDTO movieDTO) {

        Optional<MovieDTO> existingMovie = getMovieByName(movieDTO.getName());

        if (existingMovie.isPresent()) {

            throw new DuplicateEntityException("The movie with name " + movieDTO.getName() + " already exists.");
        }
        movieRepository.save(movieMapper.toEntity(movieDTO));
    }

    @Override
    @Transactional
    @CacheEvict(value = "movies", key = "#id")
    public void updateMovie(Long id, MovieDTO movieDTO) {

        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isEmpty()) {
            throw new RuntimeException("Movie not found with id: " + id);
        }

        Movie updatedMovie = movieMapper.toEntity(movieDTO);
        updatedMovie.setId(id);
        movieRepository.save(updatedMovie);
    }

    @Override
    @Transactional
    @CacheEvict(value = "movies", key = "#id")
    public void deleteMovie(Long id) {

        Optional<Movie> movie = movieRepository.findById(id);

        if (movie.isEmpty()) {
            throw new RuntimeException("Movie not found with id: " + id);
        }
        movieRepository.deleteById(id);
    }

}