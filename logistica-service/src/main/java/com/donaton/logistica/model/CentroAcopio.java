package com.donaton.logistica.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "centros_acopio")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CentroAcopio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
