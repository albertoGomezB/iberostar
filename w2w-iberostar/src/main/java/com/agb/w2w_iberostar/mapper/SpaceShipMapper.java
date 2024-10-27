package com.agb.w2w_iberostar.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.agb.w2w_iberostar.dto.MovieDTO;
import com.agb.w2w_iberostar.dto.SerieDTO;
import com.agb.w2w_iberostar.dto.SpaceShipDTO;
import com.agb.w2w_iberostar.model.Movie;
import com.agb.w2w_iberostar.model.Serie;
import com.agb.w2w_iberostar.model.SpaceShip;

@Component
public class SpaceShipMapper {

    private final MovieMapper movieMapper;
    private final SerieMapper serieMapper;

    public SpaceShipMapper(MovieMapper movieMapper, SerieMapper serieMapper) {
        this.movieMapper = movieMapper;
        this.serieMapper = serieMapper;
    }

    /**
     * Transform a SpaceShip into a SpaceShipDTO
     * 
     * @param spaceShip
     * @return
     */
    public SpaceShipDTO toDTO(SpaceShip spaceShip) {

        if (spaceShip == null) {
            return null;
        }

        SpaceShipDTO spaceShipDTO = new SpaceShipDTO();

        // Mapear los atributos simples
        spaceShipDTO.setName(spaceShip.getName());

        // Mapear las listas de movies y series
        List<MovieDTO> moviesDTO = new ArrayList<>();
        for (Movie movie : spaceShip.getMovies()) {
            moviesDTO.add(movieMapper.toDTO(movie));
        }
        spaceShipDTO.setMovies(moviesDTO);

        List<SerieDTO> seriesDTO = new ArrayList<>();
        for (Serie serie : spaceShip.getSeries()) {
            seriesDTO.add(serieMapper.toDTO(serie));
        }
        spaceShipDTO.setSeries(seriesDTO);

        return spaceShipDTO;
    }

    /**
     * Transform a SpaceShipDTO into a SpaceShip
     * 
     * @param spaceShipDTO
     * @return SpaceShip
     */
    public SpaceShip toEntity(SpaceShipDTO spaceShipDTO) {

        if (spaceShipDTO == null) {
            return null;
        }

        SpaceShip spaceShip = new SpaceShip();

        // Mapear los atributos simples
        spaceShip.setName(spaceShipDTO.getName());

        // Mapear las listas de movies y series
        List<Movie> movies = new ArrayList<>();
        for (MovieDTO movieDTO : spaceShipDTO.getMovies()) {
            movies.add(movieMapper.toEntity(movieDTO));
        }
        spaceShip.setMovies(movies);

        List<Serie> series = new ArrayList<>();
        for (SerieDTO serieDTO : spaceShipDTO.getSeries()) {
            series.add(serieMapper.toEntity(serieDTO));
        }
        spaceShip.setSeries(series);

        return spaceShip;
    }

}