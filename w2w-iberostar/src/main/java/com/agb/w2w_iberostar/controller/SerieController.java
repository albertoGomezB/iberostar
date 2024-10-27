package com.agb.w2w_iberostar.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agb.w2w_iberostar.dto.SerieDTO;
import com.agb.w2w_iberostar.exception.EntityNotFoundException;
import com.agb.w2w_iberostar.exception.UpdateFailedException;
import com.agb.w2w_iberostar.service.ISerie;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/series")
@RequiredArgsConstructor
public class SerieController {

    private final ISerie serieService;

    @Operation(summary = "Get all series", description = "Retrieve a list of all series.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Series retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<SerieDTO>> getAllSeries() {
        List<SerieDTO> series = serieService.getAllSeries();
        return ResponseEntity.ok(series);
    }

    @Operation(summary = "Get a serie by ID", description = "Retrieve a specific serie by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Serie not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SerieDTO> getSerieById(@PathVariable Long id) {

        return serieService.getSerieById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Add a new serie", description = "Create a new serie entry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Serie created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Void> addSerie(@RequestBody @Valid SerieDTO serieDTO) {
        serieService.addSerie(serieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Update a serie", description = "Update an existing serie by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie updated successfully"),
            @ApiResponse(responseCode = "404", description = "Serie not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSerie(@PathVariable Long id, @RequestBody @Valid SerieDTO serieDTO) {
        try {
            serieService.updateSerie(id, serieDTO);
            return ResponseEntity.ok().build();
        } catch (UpdateFailedException e) {
            throw new EntityNotFoundException("Error updating the serie with ID " + id + ".");
        }
    }

    @Operation(summary = "Delete a serie", description = "Delete a serie by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Serie deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Serie not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable Long id) {
        try {
            serieService.deleteSerie(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("No se pudo encontrar la serie con ID " + id + " para eliminar.");
        }
    }
}