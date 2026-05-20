package com.donaton.logistica.dto.response;

import com.donaton.logistica.enums.EstadoEnvio;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EnvioResponseDTO {
    private Long id;
    private Long donacionId;
    private Long necesidadId;
    private Long centroAcopioId;
    private String destino;
    private String transporte;
    private EstadoEnvio estado;
    private String observaciones;
    private LocalDateTime fechaPlanificacion;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaEntrega;
}
