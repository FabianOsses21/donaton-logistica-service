package com.donaton.logistica.repository;

import com.donaton.logistica.enums.EstadoEnvio;
import com.donaton.logistica.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    List<Envio> findByEstado(EstadoEnvio estado);
    List<Envio> findByDonacionId(Long donacionId);
    List<Envio> findByNecesidadId(Long necesidadId);
    List<Envio> findByCentroAcopioId(Long centroAcopioId);
}
