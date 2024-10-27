package com.agb.w2w_iberostar.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agb.w2w_iberostar.dto.SpaceShipDTO;
import com.agb.w2w_iberostar.mapper.SpaceShipMapper;
import com.agb.w2w_iberostar.model.SpaceShip;
import com.agb.w2w_iberostar.repository.SpaceShipRepository;
import com.agb.w2w_iberostar.service.ISpaceShip;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ISpaceShipImpl implements ISpaceShip {

    private final SpaceShipRepository spaceShipRepository;
    private final SpaceShipMapper spaceShipMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "spaceShips", key = "#pageable")
    public Page<SpaceShipDTO> getAllSpaceShips(Pageable pageable) {

        return spaceShipRepository.findAll(pageable)
                .map(spaceShipMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "spaceShips", key = "#id")
    public Optional<SpaceShipDTO> getSpaceShipById(Long id) {

        return spaceShipRepository.findById(id)
                .map(spaceShipMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "spaceShips", key = "#name")
    public Optional<SpaceShipDTO> getSpaceShipName(String name) {

        return spaceShipRepository.findByName(name)
                .map(spaceShipMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "spaceShips", key = "#namecontains")
    public List<SpaceShipDTO> getSpaceShipsByNameContaining(String name) {

        return spaceShipRepository.findByNameContaining(name).stream()
                .map(spaceShipMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = "spaceShips", key = "#spaceShipDTO.name")
    public void addSpaceShip(SpaceShipDTO spaceShipDTO) {

        Optional<SpaceShipDTO> existingSpaceShip = getSpaceShipName(spaceShipDTO.getName());

        if (existingSpaceShip.isPresent()) {

            throw new RuntimeException("The spaceShip with name " + spaceShipDTO.getName() + " already exists.");
        }
        spaceShipRepository.save(spaceShipMapper.toEntity(spaceShipDTO));
    }

    @Override
    @Transactional
    @CacheEvict(value = "spaceShips", key = "#id")
    public void updateSpaceShip(Long id, SpaceShipDTO spaceShipDTO) {

        Optional<SpaceShip> spaceShip = spaceShipRepository.findById(id);

        if (spaceShip.isEmpty()) {
            throw new RuntimeException("SpaceShip not found");
        }
        SpaceShip spaceShipEntity = spaceShipMapper.toEntity(spaceShipDTO);
        spaceShipEntity.setId(id);
        spaceShipRepository.save(spaceShipEntity);
    }

    @Override
    @Transactional
    @CacheEvict(value = "spaceShips", key = "#id")
    public void deleteSpaceShip(Long id) {

        Optional<SpaceShip> spaceShip = spaceShipRepository.findById(id);

        if (spaceShip.isEmpty()) {
            throw new RuntimeException("SpaceShip not found");
        }
        spaceShipRepository.deleteById(id);
    }

}
