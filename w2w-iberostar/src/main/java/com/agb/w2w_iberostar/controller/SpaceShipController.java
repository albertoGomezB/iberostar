package com.agb.w2w_iberostar.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agb.w2w_iberostar.dto.SpaceShipDTO;
import com.agb.w2w_iberostar.exception.EntityNotFoundException;
import com.agb.w2w_iberostar.service.ISpaceShip;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/spaceships")
@RequiredArgsConstructor
public class SpaceShipController {

    private final ISpaceShip spaceShipService;
    private static final Logger logger = LoggerFactory.getLogger(SpaceShipController.class);

    @Operation(summary = "Get all spaceships", description = "Retrieve a paginated list of all spaceships.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceships retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<?>> getAllSpaceships() {
        logger.info("Fetching all spaceships");
        Pageable pageable = PageRequest.of(0, 10);
        Page<SpaceShipDTO> spaceships = spaceShipService.getAllSpaceShips(pageable);
        return ResponseEntity.ok(spaceships);
    }

    @Operation(summary = "Get a spaceship by ID", description = "Retrieve a specific spaceship by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceship retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getSpaceshipById(@PathVariable Long id) {
        if (id < 0) {
            logger.warn("This request has a negative ID: {}", id);
            throw new EntityNotFoundException("The ID of the spaceship must be positive.");
        }
        logger.info("Fetching spaceship with ID: {}", id);
        Optional<SpaceShipDTO> spaceship = spaceShipService.getSpaceShipById(id);
        return spaceship.map(ResponseEntity::ok)
                .orElseThrow(() -> {
                    logger.warn("Spaceship with ID {} not found", id);
                    return new EntityNotFoundException("The spaceship with id " + id + " was not found.");
                });
    }

    @Operation(summary = "Search spaceships by name", description = "Retrieve spaceships whose names contain the specified string.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceships found successfully"),
            @ApiResponse(responseCode = "404", description = "No spaceships found with that name")
    })
    @GetMapping("/search")
    public ResponseEntity<List<?>> getSpaceShipByName(@RequestParam String name) {
        logger.info("Fetching spaceship with name: {}", name);
        List<SpaceShipDTO> spaceships = spaceShipService.getSpaceShipsByNameContaining(name);
        if (spaceships.isEmpty()) {
            logger.warn("No spaceships found with the name: {}", name);
            throw new EntityNotFoundException("No spaceships found with that name.");
        }
        return ResponseEntity.ok(spaceships);
    }

    @Operation(summary = "Add a new spaceship", description = "Create a new spaceship entry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Spaceship created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Void> addSpaceShip(@RequestBody @Valid SpaceShipDTO spaceshipDTO) {

        spaceShipService.addSpaceShip(spaceshipDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a spaceship", description = "Update an existing spaceship by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceship updated successfully"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSpaceShip(@PathVariable Long id, @RequestBody @Valid SpaceShipDTO spaceshipDTO) {
        try {
            spaceShipService.updateSpaceShip(id, spaceshipDTO);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            logger.warn("Update failed for spaceship with ID: {}", id);
            throw new EntityNotFoundException("Error updating the spaceship with ID " + id + ".");
        }
    }

    @Operation(summary = "Delete a spaceship", description = "Delete a spaceship by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaceship deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Spaceship not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceShip(@PathVariable Long id) {
        try {
            spaceShipService.deleteSpaceShip(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            logger.warn("Delete failed for spaceship with ID: {}", id);
            throw new EntityNotFoundException("No se pudo encontrar la nave espacial con ID " + id + " para eliminar.");
        }
    }
}