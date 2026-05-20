package com.donaton.logistica.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CentroAcopioResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String comuna;
    private Integer capacidadMaxima;
    private String responsable;
    private String telefono;
    private Boolean activo;
    private LocalDateTime fechaCreacion;
}
