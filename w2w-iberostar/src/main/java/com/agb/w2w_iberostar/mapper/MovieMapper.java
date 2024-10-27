package com.agb.w2w_iberostar.mapper;

import org.springframework.stereotype.Component;

import com.agb.w2w_iberostar.dto.MovieDTO;
import com.agb.w2w_iberostar.model.Movie;

@Component
public class MovieMapper {

    public MovieDTO toDTO(Movie movie) {
        if (movie == null) {
            return null;
        }

        return MovieDTO.builder()
                .name(movie.getName())
                .genre(movie.getGenre())
                .build();
    }

    public Movie toEntity(MovieDTO movieDTO) {
        if (movieDTO == null) {
            return null;
        }

        return new Movie(null, movieDTO.getName(), movieDTO.getGenre(), null);
    }

}
