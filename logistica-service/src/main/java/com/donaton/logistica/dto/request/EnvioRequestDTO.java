package com.donaton.logistica.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EnvioRequestDTO {
    @NotNull(message = "El id de la donacion es obligatorio")
    private Long donacionId;
    @NotNull(message = "El id de la necesidad es obligatorio")
    private Long necesidadId;
    @NotNull(message = "El id del centro de acopio es obligatorio")
    private Long centroAcopioId;
    @NotBlank(message = "El destino es obligatorio")
    private String destino;
    @NotBlank(message = "El transporte es obligatorio")
    private String transporte;
    private String observaciones;
}
