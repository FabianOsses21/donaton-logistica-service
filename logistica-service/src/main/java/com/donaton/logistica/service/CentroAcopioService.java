package com.donaton.logistica.service;

import com.donaton.logistica.dto.request.CentroAcopioRequestDTO;
import com.donaton.logistica.dto.response.CentroAcopioResponseDTO;
import com.donaton.logistica.mapper.CentroAcopioMapper;
import com.donaton.logistica.model.CentroAcopio;
import com.donaton.logistica.repository.CentroAcopioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CentroAcopioService {
    private final CentroAcopioRepository centroAcopioRepository;

    public List<CentroAcopioResponseDTO> listar() {
        return centroAcopioRepository.findAll().stream().map(CentroAcopioMapper::toResponseDTO).toList();
    }

    public List<CentroAcopioResponseDTO> listarActivos() {
        return centroAcopioRepository.findByActivoTrue().stream().map(CentroAcopioMapper::toResponseDTO).toList();
    }

    public CentroAcopioResponseDTO buscarPorId(Long id) {
        return CentroAcopioMapper.toResponseDTO(obtenerEntidadPorId(id));
    }

    public CentroAcopioResponseDTO guardar(CentroAcopioRequestDTO dto) {
        CentroAcopio centro = CentroAcopioMapper.toEntity(dto);
        centro.setActivo(true);
        centro.setFechaCreacion(LocalDateTime.now());
        return CentroAcopioMapper.toResponseDTO(centroAcopioRepository.save(centro));
    }

    public CentroAcopioResponseDTO actualizar(Long id, CentroAcopioRequestDTO dto) {
        CentroAcopio centro = obtenerEntidadPorId(id);
        centro.setNombre(dto.getNombre());
        centro.setDireccion(dto.getDireccion());
        centro.setComuna(dto.getComuna());
        centro.setCapacidadMaxima(dto.getCapacidadMaxima());
        centro.setResponsable(dto.getResponsable());
        centro.setTelefono(dto.getTelefono());
        return CentroAcopioMapper.toResponseDTO(centroAcopioRepository.save(centro));
    }

    public CentroAcopioResponseDTO cambiarActivo(Long id, Boolean activo) {
        CentroAcopio centro = obtenerEntidadPorId(id);
        centro.setActivo(activo);
        return CentroAcopioMapper.toResponseDTO(centroAcopioRepository.save(centro));
    }

    public void eliminar(Long id) {
        CentroAcopio centro = obtenerEntidadPorId(id);
        centroAcopioRepository.delete(centro);
    }

    public CentroAcopio obtenerEntidadPorId(Long id) {
        return centroAcopioRepository.findById(id).orElseThrow(() -> new RuntimeException("Centro de acopio no encontrado"));
    }
}
