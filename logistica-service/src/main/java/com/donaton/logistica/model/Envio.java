package com.donaton.logistica.model;

import com.donaton.logistica.enums.EstadoEnvio;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long donacionId;
    private Long necesidadId;
    private Long centroAcopioId;
    private String destino;
    private String transporte;
    @Enumerated(EnumType.STRING)
    private EstadoEnvio estado;
    private String observaciones;
    private LocalDateTime fechaPlanificacion;
    private LocalDateTime fechaSalida;
    private LocalDateTime fechaEntrega;
}
