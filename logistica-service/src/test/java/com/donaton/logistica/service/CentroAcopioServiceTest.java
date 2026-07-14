package com.donaton.logistica.service;

import com.donaton.logistica.dto.request.CentroAcopioRequestDTO;
import com.donaton.logistica.dto.response.CentroAcopioResponseDTO;
import com.donaton.logistica.model.CentroAcopio;
import com.donaton.logistica.repository.CentroAcopioRepository;
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
class CentroAcopioServiceTest {

    @Mock
    private CentroAcopioRepository centroAcopioRepository;

    @InjectMocks
    private CentroAcopioService centroAcopioService;

    @Test
    void deberiaGuardarCentroAcopioCorrectamente() {
        CentroAcopioRequestDTO request = crearRequest();
        CentroAcopio centroGuardado = crearCentroAcopio();

        Mockito.when(centroAcopioRepository.save(any(CentroAcopio.class)))
                .thenReturn(centroGuardado);

        CentroAcopioResponseDTO resultado = centroAcopioService.guardar(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Centro Norte", resultado.getNombre());
        assertEquals("Santiago", resultado.getComuna());
        assertTrue(resultado.getActivo());
    }

    @Test
    void deberiaListarCentrosAcopio() {
        Mockito.when(centroAcopioRepository.findAll())
                .thenReturn(List.of(crearCentroAcopio()));

        List<CentroAcopioResponseDTO> resultado = centroAcopioService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Centro Norte", resultado.get(0).getNombre());
    }

    @Test
    void deberiaListarCentrosActivos() {
        Mockito.when(centroAcopioRepository.findByActivoTrue())
                .thenReturn(List.of(crearCentroAcopio()));

        List<CentroAcopioResponseDTO> resultado = centroAcopioService.listarActivos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getActivo());
    }

    @Test
    void deberiaBuscarCentroPorId() {
        Mockito.when(centroAcopioRepository.findById(1L))
                .thenReturn(Optional.of(crearCentroAcopio()));

        CentroAcopioResponseDTO resultado = centroAcopioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Centro Norte", resultado.getNombre());
    }

    @Test
    void deberiaActualizarCentroAcopio() {
        CentroAcopio centroExistente = crearCentroAcopio();
        CentroAcopioRequestDTO request = crearRequest();
        request.setNombre("Centro Sur");
        request.setComuna("Valparaíso");
        request.setCapacidadMaxima(200);

        Mockito.when(centroAcopioRepository.findById(1L))
                .thenReturn(Optional.of(centroExistente));

        Mockito.when(centroAcopioRepository.save(any(CentroAcopio.class)))
                .thenReturn(centroExistente);

        CentroAcopioResponseDTO resultado = centroAcopioService.actualizar(1L, request);

        assertNotNull(resultado);
        assertEquals("Centro Sur", resultado.getNombre());
        assertEquals("Valparaíso", resultado.getComuna());
        assertEquals(200, resultado.getCapacidadMaxima());
    }

    @Test
    void deberiaCambiarActivoCentroAcopio() {
        CentroAcopio centro = crearCentroAcopio();

        Mockito.when(centroAcopioRepository.findById(1L))
                .thenReturn(Optional.of(centro));

        Mockito.when(centroAcopioRepository.save(any(CentroAcopio.class)))
                .thenReturn(centro);

        CentroAcopioResponseDTO resultado = centroAcopioService.cambiarActivo(1L, false);

        assertNotNull(resultado);
        assertFalse(resultado.getActivo());
    }

    @Test
    void deberiaEliminarCentroAcopio() {
        CentroAcopio centro = crearCentroAcopio();

        Mockito.when(centroAcopioRepository.findById(1L))
                .thenReturn(Optional.of(centro));

        centroAcopioService.eliminar(1L);

        Mockito.verify(centroAcopioRepository).delete(centro);
    }

    @Test
    void deberiaLanzarErrorCuandoNoExisteCentroAcopio() {
        Mockito.when(centroAcopioRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> centroAcopioService.buscarPorId(99L)
        );

        assertEquals("Centro de acopio no encontrado", exception.getMessage());
    }

    private CentroAcopioRequestDTO crearRequest() {
        CentroAcopioRequestDTO request = new CentroAcopioRequestDTO();
        request.setNombre("Centro Norte");
        request.setDireccion("Av. Principal 123");
        request.setComuna("Santiago");
        request.setCapacidadMaxima(100);
        request.setResponsable("María López");
        request.setTelefono("912345678");
        return request;
    }

    private CentroAcopio crearCentroAcopio() {
        CentroAcopio centro = new CentroAcopio();
        centro.setId(1L);
        centro.setNombre("Centro Norte");
        centro.setDireccion("Av. Principal 123");
        centro.setComuna("Santiago");
        centro.setCapacidadMaxima(100);
        centro.setResponsable("María López");
        centro.setTelefono("912345678");
        centro.setActivo(true);
        centro.setFechaCreacion(LocalDateTime.now());
        return centro;
    }
}