package com.agb.w2w_iberostar.repository;

import java.util.Optional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agb.w2w_iberostar.model.SpaceShip;

@Repository
public interface SpaceShipRepository extends JpaRepository<SpaceShip, Long> {

    Optional<SpaceShip> findByName(String name);

    List<SpaceShip> findByNameContaining(String name);

}
