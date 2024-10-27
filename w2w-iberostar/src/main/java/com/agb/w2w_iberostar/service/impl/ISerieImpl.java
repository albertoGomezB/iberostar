package com.agb.w2w_iberostar.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agb.w2w_iberostar.dto.SerieDTO;
import com.agb.w2w_iberostar.exception.DuplicateEntityException;
import com.agb.w2w_iberostar.mapper.SerieMapper;
import com.agb.w2w_iberostar.model.Serie;
import com.agb.w2w_iberostar.repository.SerieRepository;
import com.agb.w2w_iberostar.service.ISerie;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ISerieImpl implements ISerie {

    private final SerieRepository serieRepository;
    private final SerieMapper serieMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "series")
    public List<SerieDTO> getAllSeries() {

        return serieRepository.findAll().stream()
                .map(serieMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "series", key = "#id")
    public Optional<SerieDTO> getSerieById(Long id) {

        return serieRepository.findById(id)
                .map(serieMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "series", key = "#name")
    public Optional<SerieDTO> getSerieName(String name) {

        return serieRepository.findByName(name)
                .map(serieMapper::toDTO);
    }

    @Override
    @Transactional
    @CacheEvict(value = "series", key = "#addedSerie")
    public void addSerie(SerieDTO serieDTO) {

        Optional<SerieDTO> existingSerie = getSerieName(serieDTO.getName());

        if (existingSerie.isPresent()) {

            throw new DuplicateEntityException("The serie with name " + serieDTO.getName() + " already exists.");
        }
        serieRepository.save(serieMapper.toEntity(serieDTO));
    }

    @Override
    @Transactional
    @CacheEvict(value = "series", key = "#id")
    public void updateSerie(Long id, SerieDTO serieDTO) {

        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isEmpty()) {
            throw new RuntimeException("Serie not found with id: " + id);
        }
        Serie updatedSerie = serieMapper.toEntity(serieDTO);
        updatedSerie.setId(id);
        serieRepository.save(updatedSerie);

    }

    @Override
    @Transactional
    @CacheEvict(value = "series", key = "#id")
    public void deleteSerie(Long id) {

        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isEmpty()) {
            throw new RuntimeException("Serie not found with id: " + id);
        }

        serieRepository.deleteById(id);
    }

}
