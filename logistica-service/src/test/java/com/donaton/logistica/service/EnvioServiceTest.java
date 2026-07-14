package com.donaton.logistica.service;

import com.donaton.logistica.client.DonacionClient;
import com.donaton.logistica.client.NecesidadClient;
import com.donaton.logistica.dto.request.EnvioRequestDTO;
import com.donaton.logistica.dto.response.EnvioResponseDTO;
import com.donaton.logistica.enums.EstadoEnvio;
import com.donaton.logistica.model.CentroAcopio;
import com.donaton.logistica.model.Envio;
import com.donaton.logistica.repository.EnvioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class EnvioServiceTest {

    @Mock
    private EnvioRepository envioRepository;

    @Mock
    private CentroAcopioService centroAcopioService;

    @Mock
    private DonacionClient donacionClient;

    @Mock
    private NecesidadClient necesidadClient;

    @InjectMocks
    private EnvioService envioService;

    @Test
    void deberiaGuardarEnvioCorrectamente() {
        EnvioRequestDTO request = crearRequest();
        Envio envioGuardado = crearEnvio();

        Mockito.when(centroAcopioService.obtenerEntidadPorId(20L))
                .thenReturn(crearCentroAcopio());

        Mockito.when(envioRepository.save(any(Envio.class)))
                .thenReturn(envioGuardado);

        EnvioResponseDTO resultado = envioService.guardar(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getDonacionId());
        assertEquals(30L, resultado.getNecesidadId());
        assertEquals(EstadoEnvio.PLANIFICADO, resultado.getEstado());

        Mockito.verify(donacionClient).validarDonacionExiste(10L);
        Mockito.verify(necesidadClient).validarNecesidadExiste(30L);
        Mockito.verify(donacionClient).cambiarEstadoDonacion(10L, "ASIGNADA");
        Mockito.verify(necesidadClient).cambiarEstadoNecesidad(30L, "EN_PROCESO");
    }

    @Test
    void deberiaListarEnvios() {
        Mockito.when(envioRepository.findAll())
                .thenReturn(List.of(crearEnvio()));

        List<EnvioResponseDTO> resultado = envioService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Camión", resultado.get(0).getTransporte());
    }

    @Test
    void deberiaBuscarEnvioPorId() {
        Mockito.when(envioRepository.findById(1L))
                .thenReturn(Optional.of(crearEnvio()));

        EnvioResponseDTO resultado = envioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Santiago Centro", resultado.getDestino());
    }

    @Test
    void deberiaCambiarEstadoAEnTransito() {
        Envio envio = crearEnvio();
        envio.setFechaSalida(null);

        Mockito.when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        Mockito.when(envioRepository.save(any(Envio.class)))
                .thenReturn(envio);

        EnvioResponseDTO resultado = envioService.cambiarEstado(1L, EstadoEnvio.EN_TRANSITO);

        assertNotNull(resultado);
        assertEquals(EstadoEnvio.EN_TRANSITO, resultado.getEstado());
        assertNotNull(resultado.getFechaSalida());

        Mockito.verify(donacionClient).cambiarEstadoDonacion(10L, "ENVIADA");
    }

    @Test
    void deberiaCambiarEstadoAEntregado() {
        Envio envio = crearEnvio();
        envio.setFechaEntrega(null);

        Mockito.when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        Mockito.when(envioRepository.save(any(Envio.class)))
                .thenReturn(envio);

        EnvioResponseDTO resultado = envioService.cambiarEstado(1L, EstadoEnvio.ENTREGADO);

        assertNotNull(resultado);
        assertEquals(EstadoEnvio.ENTREGADO, resultado.getEstado());
        assertNotNull(resultado.getFechaEntrega());

        Mockito.verify(donacionClient).cambiarEstadoDonacion(10L, "ENTREGADA");
        Mockito.verify(necesidadClient).cambiarEstadoNecesidad(30L, "CUBIERTA");
    }

    @Test
    void deberiaBuscarPorEstado() {
        Mockito.when(envioRepository.findByEstado(EstadoEnvio.PLANIFICADO))
                .thenReturn(List.of(crearEnvio()));

        List<EnvioResponseDTO> resultado = envioService.buscarPorEstado(EstadoEnvio.PLANIFICADO);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(EstadoEnvio.PLANIFICADO, resultado.get(0).getEstado());
    }

    @Test
    void deberiaBuscarPorDonacion() {
        Mockito.when(envioRepository.findByDonacionId(10L))
                .thenReturn(List.of(crearEnvio()));

        List<EnvioResponseDTO> resultado = envioService.buscarPorDonacion(10L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getDonacionId());
    }

    @Test
    void deberiaBuscarPorNecesidad() {
        Mockito.when(envioRepository.findByNecesidadId(30L))
                .thenReturn(List.of(crearEnvio()));

        List<EnvioResponseDTO> resultado = envioService.buscarPorNecesidad(30L);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(30L, resultado.get(0).getNecesidadId());
    }

    @Test
    void deberiaEliminarEnvio() {
        Envio envio = crearEnvio();

        Mockito.when(envioRepository.findById(1L))
                .thenReturn(Optional.of(envio));

        envioService.eliminar(1L);

        Mockito.verify(envioRepository).delete(envio);
    }

    @Test
    void deberiaLanzarErrorCuandoNoExisteEnvio() {
        Mockito.when(envioRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> envioService.buscarPorId(99L)
        );

        assertEquals("Envio no encontrado", exception.getMessage());
    }

    private EnvioRequestDTO crearRequest() {
        EnvioRequestDTO request = new EnvioRequestDTO();
        request.setDonacionId(10L);
        request.setNecesidadId(30L);
        request.setCentroAcopioId(20L);
        request.setDestino("Santiago Centro");
        request.setTransporte("Camión");
        request.setObservaciones("Entrega prioritaria");
        return request;
    }

    private Envio crearEnvio() {
        Envio envio = new Envio();
        envio.setId(1L);
        envio.setDonacionId(10L);
        envio.setNecesidadId(30L);
        envio.setCentroAcopioId(20L);
        envio.setDestino("Santiago Centro");
        envio.setTransporte("Camión");
        envio.setEstado(EstadoEnvio.PLANIFICADO);
        envio.setObservaciones("Entrega prioritaria");
        envio.setFechaPlanificacion(LocalDateTime.now());
        return envio;
    }

    private CentroAcopio crearCentroAcopio() {
        CentroAcopio centro = new CentroAcopio();
        centro.setId(20L);
        centro.setNombre("Centro Norte");
        centro.setActivo(true);
        return centro;
    }
}