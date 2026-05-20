package com.donaton.logistica.service;

import com.donaton.logistica.client.DonacionClient;
import com.donaton.logistica.client.NecesidadClient;
import com.donaton.logistica.dto.request.EnvioRequestDTO;
import com.donaton.logistica.dto.response.EnvioResponseDTO;
import com.donaton.logistica.enums.EstadoEnvio;
import com.donaton.logistica.mapper.EnvioMapper;
import com.donaton.logistica.model.Envio;
import com.donaton.logistica.repository.EnvioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnvioService {
    private final EnvioRepository envioRepository;
    private final CentroAcopioService centroAcopioService;
    private final DonacionClient donacionClient;
    private final NecesidadClient necesidadClient;

    public List<EnvioResponseDTO> listar() {
        return envioRepository.findAll().stream().map(EnvioMapper::toResponseDTO).toList();
    }

    public EnvioResponseDTO buscarPorId(Long id) {
        return EnvioMapper.toResponseDTO(obtenerEntidadPorId(id));
    }

    public EnvioResponseDTO guardar(EnvioRequestDTO dto) {
        centroAcopioService.obtenerEntidadPorId(dto.getCentroAcopioId());
        donacionClient.validarDonacionExiste(dto.getDonacionId());
        necesidadClient.validarNecesidadExiste(dto.getNecesidadId());

        Envio envio = EnvioMapper.toEntity(dto);
        envio.setEstado(EstadoEnvio.PLANIFICADO);
        envio.setFechaPlanificacion(LocalDateTime.now());
        Envio envioGuardado = envioRepository.save(envio);

        donacionClient.cambiarEstadoDonacion(dto.getDonacionId(), "ASIGNADA");
        necesidadClient.cambiarEstadoNecesidad(dto.getNecesidadId(), "EN_PROCESO");

        return EnvioMapper.toResponseDTO(envioGuardado);
    }

    public EnvioResponseDTO cambiarEstado(Long id, EstadoEnvio estado) {
        Envio envio = obtenerEntidadPorId(id);
        envio.setEstado(estado);

        if (estado == EstadoEnvio.EN_TRANSITO && envio.getFechaSalida() == null) {
            envio.setFechaSalida(LocalDateTime.now());
            donacionClient.cambiarEstadoDonacion(envio.getDonacionId(), "ENVIADA");
        }

        if (estado == EstadoEnvio.ENTREGADO && envio.getFechaEntrega() == null) {
            envio.setFechaEntrega(LocalDateTime.now());
            donacionClient.cambiarEstadoDonacion(envio.getDonacionId(), "ENTREGADA");
            necesidadClient.cambiarEstadoNecesidad(envio.getNecesidadId(), "CUBIERTA");
        }

        return EnvioMapper.toResponseDTO(envioRepository.save(envio));
    }

    public List<EnvioResponseDTO> buscarPorEstado(EstadoEnvio estado) {
        return envioRepository.findByEstado(estado).stream().map(EnvioMapper::toResponseDTO).toList();
    }

    public List<EnvioResponseDTO> buscarPorDonacion(Long donacionId) {
        return envioRepository.findByDonacionId(donacionId).stream().map(EnvioMapper::toResponseDTO).toList();
    }

    public List<EnvioResponseDTO> buscarPorNecesidad(Long necesidadId) {
        return envioRepository.findByNecesidadId(necesidadId).stream().map(EnvioMapper::toResponseDTO).toList();
    }

    public void eliminar(Long id) {
        Envio envio = obtenerEntidadPorId(id);
        envioRepository.delete(envio);
    }

    private Envio obtenerEntidadPorId(Long id) {
        return envioRepository.findById(id).orElseThrow(() -> new RuntimeException("Envio no encontrado"));
    }
}
