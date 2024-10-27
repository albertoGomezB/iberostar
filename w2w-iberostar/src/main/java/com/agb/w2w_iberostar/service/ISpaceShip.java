package com.agb.w2w_iberostar.service;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.agb.w2w_iberostar.dto.SpaceShipDTO;

public interface ISpaceShip {

    Page<SpaceShipDTO> getAllSpaceShips(Pageable pageable);

    Optional<SpaceShipDTO> getSpaceShipById(Long id);

    Optional<SpaceShipDTO> getSpaceShipName(String name);

    List<SpaceShipDTO> getSpaceShipsByNameContaining(String name);

    void addSpaceShip(SpaceShipDTO spaceShipDTO);

    void updateSpaceShip(Long id, SpaceShipDTO spaceShipDTO);

    void deleteSpaceShip(Long id);

}
