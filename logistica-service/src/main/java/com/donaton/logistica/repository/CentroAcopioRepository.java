package com.donaton.logistica.repository;

import com.donaton.logistica.model.CentroAcopio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CentroAcopioRepository extends JpaRepository<CentroAcopio, Long> {
    List<CentroAcopio> findByActivoTrue();
    List<CentroAcopio> findByComunaContainingIgnoreCase(String comuna);
}
