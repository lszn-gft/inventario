# Prueba Técnica - Sistema de Inventario y Depreciación de Activos

## Descripción del Proyecto

Este proyecto consiste en el desarrollo de un servicio REST para el registro y gestión de activos (equipos), incluyendo el cálculo de su depreciación anual. La depreciación se calcula en base a un 4% anual, aunque este porcentaje puede ajustarse según normativas fiscales.

## Tecnologías Utilizadas

- Java 21  
- Spring Boot 3  
- Maven  
- H2 Database (en memoria)  
- OpenAPI 3 con Swagger UI  
- JUnit 5 (Pruebas unitarias)  
- Docker  
- Kubernetes  

## Estructura del Proyecto

```
inventario/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/lszn/inventario/
│   │   │       ├── controller/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       ├── service/
│   │   │       └── InventarioApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/lszn/inventario/servicetest.java
├── k8s/
│   ├── deployment.yaml
│   └── service.yaml
├── Dockerfile
├── README.md
├── diagramas
│   ├── diagrama_componentes.png
│   ├── diagrama_secuencia.png
│   └── diagrama_clases.png
└── pom.xml
```

## Ejecución del Proyecto

### Modo local

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/lszn-gft/inventario.git
   cd inventario
   ```

2. Ejecutar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

3. Acceder a Swagger UI:
   [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### Despliegue en Kubernetes

1. Construir la imagen Docker:
   ```bash
   docker build -t inventario-service .
   ```

2. Aplicar los manifiestos:
   ```bash
   kubectl apply -f k8s/
   ```

3. Verificar los pods y servicios:
   ```bash
   kubectl get pods
   kubectl get svc
   ```

## Endpoints Disponibles

- `POST /activos`  
- `GET /activos`  
- `GET /activos/{id}`  
- `PUT /activos/{id}`  
- `DELETE /activos/{id}`  
- `GET /activos/{id}/valor-actual`  

Todos documentados vía Swagger:  
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Pruebas Unitarias

El proyecto incluye pruebas con JUnit 5. Para ejecutarlas:

```bash
./mvnw test
```

## Diagramas UML

Los siguientes diagramas ilustran la estructura y funcionamiento del servicio:

1. **Diagrama de Clases**  
   Representa las entidades principales del sistema y sus relaciones.

2. **Diagrama de Componentes**  
   Muestra la estructura modular de la aplicación.

3. **Diagrama de Secuencia método (`GET /activos/{id}/valor-actual`)**  
   Representa el flujo de llamadas entre componentes durante una operación típica.

## Repositorio

[https://github.com/lszn-gft/inventario](https://github.com/lszn-gft/inventario)

## Contacto

**Luis Ernesto Garzón Garzón**  
📧 lszn@gft.com  
_Solution Consultant_
