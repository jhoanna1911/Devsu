# Prueba Técnica - Arquitectura Microservicios

## Arquitectura

- **clientes-service** (puerto 8081): CRUD de Personas y Clientes
- **cuentas-service** (puerto 8082): CRUD de Cuentas, Movimientos y Reportes
- **RabbitMQ**: Comunicación asincrónica entre microservicios
- **PostgreSQL**: Base de datos relacional compartida (esquemas separados)

## Despliegue con Docker

```bash
# Clonar el repositorio y desde la raíz ejecutar:
docker-compose up --build

# Los servicios estarán disponibles en:
# clientes-service: http://localhost:8081
# cuentas-service:  http://localhost:8082
# RabbitMQ UI:      http://localhost:15672 (guest/guest)
```

## Endpoints principales

### clientes-service (puerto 8081)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /clientes | Listar todos |
| GET | /clientes/{id} | Obtener por ID |
| POST | /clientes | Crear cliente |
| PUT | /clientes/{id} | Actualizar completo |
| PATCH | /clientes/{id} | Actualizar parcial |
| DELETE | /clientes/{id} | Eliminar |

### cuentas-service (puerto 8082)
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /cuentas | Listar todas |
| GET | /cuentas/{id} | Obtener por ID |
| POST | /cuentas | Crear cuenta |
| PUT | /cuentas/{id} | Actualizar |
| PATCH | /cuentas/{id} | Actualizar parcial |
| GET | /movimientos | Listar movimientos |
| POST | /movimientos | Registrar movimiento |
| GET | /reportes | Reporte por fechas y cliente |

### Ejemplo reporte
```
GET /reportes?fechaInicio=2022-02-01&fechaFin=2022-02-28&clienteId=1
```

## Pruebas

```bash
# Ejecutar pruebas unitarias
cd clientes-service && mvn test
cd cuentas-service && mvn test
```
