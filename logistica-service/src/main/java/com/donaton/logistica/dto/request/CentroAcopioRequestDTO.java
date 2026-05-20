package com.donaton.logistica.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CentroAcopioRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;
    @NotBlank(message = "La comuna es obligatoria")
    private String comuna;
    @NotNull(message = "La capacidad maxima es obligatoria")
    @Min(value = 1, message = "La capacidad maxima debe ser mayor a cero")
    private Integer capacidadMaxima;
    private String responsable;
    private String telefono;
}
