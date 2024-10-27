package com.agb.w2w_iberostar.service;

import java.util.List;
import java.util.Optional;

import com.agb.w2w_iberostar.dto.SerieDTO;

public interface ISerie {

    List<SerieDTO> getAllSeries();

    Optional<SerieDTO> getSerieById(Long id);

    Optional<SerieDTO> getSerieName(String name);

    void addSerie(SerieDTO serieDTO);

    void updateSerie(Long id, SerieDTO serieDTO);

    void deleteSerie(Long id);

}
