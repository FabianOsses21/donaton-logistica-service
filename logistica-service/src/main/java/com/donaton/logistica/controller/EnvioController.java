package com.donaton.logistica.controller;

import com.donaton.logistica.dto.request.EnvioRequestDTO;
import com.donaton.logistica.dto.response.EnvioResponseDTO;
import com.donaton.logistica.enums.EstadoEnvio;
import com.donaton.logistica.service.EnvioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/logistica/envios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EnvioController {
    private final EnvioService envioService;

    @GetMapping
    public ResponseEntity<List<EnvioResponseDTO>> listar() {
        return ResponseEntity.ok(envioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioResponseDTO> buscarPorId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(envioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EnvioResponseDTO> guardar(@Valid @RequestBody EnvioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(envioService.guardar(dto));
    }

    @PatchMapping("/{id}/estado/{estado}")
    public ResponseEntity<EnvioResponseDTO> cambiarEstado(@PathVariable("id") Long id, @PathVariable("estado") EstadoEnvio estado) {
        return ResponseEntity.ok(envioService.cambiarEstado(id, estado));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EnvioResponseDTO>> buscarPorEstado(@PathVariable("estado") EstadoEnvio estado) {
        return ResponseEntity.ok(envioService.buscarPorEstado(estado));
    }

    @GetMapping("/donacion/{donacionId}")
    public ResponseEntity<List<EnvioResponseDTO>> buscarPorDonacion(@PathVariable("donacionId") Long donacionId) {
        return ResponseEntity.ok(envioService.buscarPorDonacion(donacionId));
    }

    @GetMapping("/necesidad/{necesidadId}")
    public ResponseEntity<List<EnvioResponseDTO>> buscarPorNecesidad(@PathVariable("necesidadId") Long necesidadId) {
        return ResponseEntity.ok(envioService.buscarPorNecesidad(necesidadId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {
        envioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
