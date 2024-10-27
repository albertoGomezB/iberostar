package com.agb.w2w_iberostar.mapper;

import org.springframework.stereotype.Component;

import com.agb.w2w_iberostar.dto.SerieDTO;
import com.agb.w2w_iberostar.model.Serie;

@Component
public class SerieMapper {

    public SerieDTO toDTO(Serie serie) {
        if (serie == null) {
            return null;
        }

        return SerieDTO.builder()
                .name(serie.getName())
                .description(serie.getDescription())
                .build();
    }

    public Serie toEntity(SerieDTO serieDTO) {
        if (serieDTO == null) {
            return null;
        }

        return new Serie(null, serieDTO.getName(), serieDTO.getDescription(), null);
    }

}
