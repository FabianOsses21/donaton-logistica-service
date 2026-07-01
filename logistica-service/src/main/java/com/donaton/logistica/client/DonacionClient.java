package com.donaton.logistica.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class DonacionClient {
    private final @LoadBalanced RestClient.Builder restClientBuilder;

    @Value("${donaciones.service.url}")
    private String donacionesServiceUrl;

    public void validarDonacionExiste(Long donacionId) {
        try {
            restClientBuilder.build().get().uri(donacionesServiceUrl + "/" + donacionId).retrieve().toBodilessEntity();
        } catch (Exception exception) {
            throw new RuntimeException("No se pudo validar la donacion con id: " + donacionId);
        }
    }

    public void cambiarEstadoDonacion(Long donacionId, String estado) {
        try {
            restClientBuilder.build().patch().uri(donacionesServiceUrl + "/" + donacionId + "/estado/" + estado).retrieve().toBodilessEntity();
        } catch (Exception exception) {
            throw new RuntimeException("No se pudo actualizar el estado de la donacion con id: " + donacionId);
        }
    }
}
