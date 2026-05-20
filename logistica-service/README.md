# logistica-service

Microservicio de gestion logistica para Donaton.

## Ejecutar

```bash
mvn clean install -U
mvn spring-boot:run
```

Puerto: `8084`

## Importante

Antes de crear envios, deben estar corriendo:

- necesidades-service: `http://localhost:8083`
- donaciones-service: `http://localhost:8082`

El servicio valida que existan la donacion y la necesidad antes de crear un envio. Tambien actualiza estados:

- Al crear envio: donacion pasa a `ASIGNADA` y necesidad a `EN_PROCESO`.
- Al pasar envio a `EN_TRANSITO`: donacion pasa a `ENVIADA`.
- Al pasar envio a `ENTREGADO`: donacion pasa a `ENTREGADA` y necesidad a `CUBIERTA`.

## Crear centro de acopio

POST `http://localhost:8084/api/v1/logistica/centros-acopio`

```json
{
  "nombre": "Centro Acopio Santiago",
  "direccion": "Av. Principal 123",
  "comuna": "Santiago",
  "capacidadMaxima": 1000,
  "responsable": "Juan Perez",
  "telefono": "+56912345678"
}
```

## Crear envio

POST `http://localhost:8084/api/v1/logistica/envios`

```json
{
  "donacionId": 1,
  "necesidadId": 4,
  "centroAcopioId": 1,
  "destino": "Santiago",
  "transporte": "Camion municipal",
  "observaciones": "Envio coordinado con voluntarios"
}
```
