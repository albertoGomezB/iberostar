package com.agb.w2w_iberostar.dto;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SpaceShipDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Series are mandatory")
    private List<SerieDTO> series;
    @NotNull(message = "Movies are mandatory")
    private List<MovieDTO> movies;

}
