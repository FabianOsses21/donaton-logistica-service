package com.donaton.logistica.mapper;

import com.donaton.logistica.dto.request.EnvioRequestDTO;
import com.donaton.logistica.dto.response.EnvioResponseDTO;
import com.donaton.logistica.model.Envio;

public class EnvioMapper {
    private EnvioMapper() {}

    public static Envio toEntity(EnvioRequestDTO dto) {
        return Envio.builder()
                .donacionId(dto.getDonacionId())
                .necesidadId(dto.getNecesidadId())
                .centroAcopioId(dto.getCentroAcopioId())
                .destino(dto.getDestino())
                .transporte(dto.getTransporte())
                .observaciones(dto.getObservaciones())
                .build();
    }

    public static EnvioResponseDTO toResponseDTO(Envio envio) {
        return EnvioResponseDTO.builder()
                .id(envio.getId())
                .donacionId(envio.getDonacionId())
                .necesidadId(envio.getNecesidadId())
                .centroAcopioId(envio.getCentroAcopioId())
                .destino(envio.getDestino())
                .transporte(envio.getTransporte())
                .estado(envio.getEstado())
                .observaciones(envio.getObservaciones())
                .fechaPlanificacion(envio.getFechaPlanificacion())
                .fechaSalida(envio.getFechaSalida())
                .fechaEntrega(envio.getFechaEntrega())
                .build();
    }
}
