package com.donaton.logistica.mapper;

import com.donaton.logistica.dto.request.CentroAcopioRequestDTO;
import com.donaton.logistica.dto.response.CentroAcopioResponseDTO;
import com.donaton.logistica.model.CentroAcopio;

public class CentroAcopioMapper {
    private CentroAcopioMapper() {}

    public static CentroAcopio toEntity(CentroAcopioRequestDTO dto) {
        return CentroAcopio.builder()
                .nombre(dto.getNombre())
                .direccion(dto.getDireccion())
                .comuna(dto.getComuna())
                .capacidadMaxima(dto.getCapacidadMaxima())
                .responsable(dto.getResponsable())
                .telefono(dto.getTelefono())
                .build();
    }

    public static CentroAcopioResponseDTO toResponseDTO(CentroAcopio centro) {
        return CentroAcopioResponseDTO.builder()
                .id(centro.getId())
                .nombre(centro.getNombre())
                .direccion(centro.getDireccion())
                .comuna(centro.getComuna())
                .capacidadMaxima(centro.getCapacidadMaxima())
                .responsable(centro.getResponsable())
                .telefono(centro.getTelefono())
                .activo(centro.getActivo())
                .fechaCreacion(centro.getFechaCreacion())
                .build();
    }
}
