package com.donaton.logistica.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class NecesidadClient {
    private final @LoadBalanced RestClient.Builder restClientBuilder;

    @Value("${necesidades.service.url}")
    private String necesidadesServiceUrl;

    public void validarNecesidadExiste(Long necesidadId) {
        try {
            restClientBuilder.build().get().uri(necesidadesServiceUrl + "/" + necesidadId).retrieve().toBodilessEntity();
        } catch (Exception exception) {
            throw new RuntimeException("No se pudo validar la necesidad con id: " + necesidadId);
        }
    }

    public void cambiarEstadoNecesidad(Long necesidadId, String estado) {
        try {
            restClientBuilder.build().patch().uri(necesidadesServiceUrl + "/" + necesidadId + "/estado/" + estado).retrieve().toBodilessEntity();
        } catch (Exception exception) {
            throw new RuntimeException("No se pudo actualizar el estado de la necesidad con id: " + necesidadId);
        }
    }
}
