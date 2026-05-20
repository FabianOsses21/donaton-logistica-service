package com.donaton.logistica.controller;

import com.donaton.logistica.dto.request.CentroAcopioRequestDTO;
import com.donaton.logistica.dto.response.CentroAcopioResponseDTO;
import com.donaton.logistica.service.CentroAcopioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/logistica/centros-acopio")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CentroAcopioController {
    private final CentroAcopioService centroAcopioService;

    @GetMapping
    public ResponseEntity<List<CentroAcopioResponseDTO>> listar() {
        return ResponseEntity.ok(centroAcopioService.listar());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<CentroAcopioResponseDTO>> listarActivos() {
        return ResponseEntity.ok(centroAcopioService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroAcopioResponseDTO> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(centroAcopioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CentroAcopioResponseDTO> guardar(@Valid @RequestBody CentroAcopioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(centroAcopioService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroAcopioResponseDTO> actualizar(@PathVariable("id") Long id, @Valid @RequestBody CentroAcopioRequestDTO dto) {
        return ResponseEntity.ok(centroAcopioService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/activo/{activo}")
    public ResponseEntity<CentroAcopioResponseDTO> cambiarActivo(@PathVariable("id") Long id, @PathVariable("activo") Boolean activo) {
        return ResponseEntity.ok(centroAcopioService.cambiarActivo(id, activo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        centroAcopioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
