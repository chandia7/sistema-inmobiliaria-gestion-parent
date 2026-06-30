# 🚀 SISTEMA DE MICROSERVICIOS MULTIMÓDULO - ENTREGA FINAL

## 🧩 COMPONENTES DE DISTRIBUCIÓN Y DEFENSA TÉCNICA

Utilice los siguientes enlaces externos para descargar las versiones listas para producción y visualizar la defensa del proyecto:

| Componente | Descripción | Enlace de Descarga (Nube externa) |
| :--- | :--- | :--- |
| **🖥 Versión Sin Docker** <br>*(Arranque Nativo)* | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar` compilados y el script `arrancar-nativo.bat` ordenado por fases. | [Descargar ZIP Nativo aquí](REEMPLAZAR_CON_TU_ENLACE_DE_GOOGLE_DRIVE) |
| **🐳 Versión Con Docker** <br>*(Avance Examen Transversal)* | Archivo `.zip` que contiene la carpeta `apps/` con los `.jar`, el archivo `docker-compose.yml` y el script automatizado `arrancar-sistema.bat`. | [Descargar ZIP Docker aquí](REEMPLAZAR_CON_TU_ENLACE_DE_GOOGLE_DRIVE) |
| **📹 Video de Defensa Técnica** <br>*(Evaluación Individual)* | Enlace directo al video explicativo donde se evidencia el funcionamiento, testing y el aporte técnico individual. **Duración ideal: 15 minutos (Máximo permitido: 18 minutos).** El archivo de transcripción `subtitulos-video.txt` se encuentra en la raíz del repositorio. | [Ver Video Explicativo aquí](REEMPLAZAR_CON_TU_ENLACE_DE_VIDEO) |

---

# Sistema de Gestión Inmobiliaria - Arquitectura de Microservicios

Sistema de Gestión Inmobiliaria es un sistema para la gestión, reserva, pago, venta y seguimiento de propiedades inmobiliarias (casas, departamentos, etc.). Permite administrar usuarios, catálogos de propiedades, inventarios/disponibilidad, pedidos, pagos, logística notarial y notificaciones mediante una arquitectura de microservicios desarrollada con Spring Boot.

Este proyecto está diseñado como ejemplo académico para trabajar arquitectura de microservicios, Eureka Server, API Gateway, Feign Client, MySQL, Swagger/OpenAPI y estructura Maven padre-hijos.

---

# 1. Objetivo del proyecto

El sistema permite gestionar el flujo completo del ciclo inmobiliario y comercial:

1. Registrar perfiles de usuarios e iniciar sesión de forma segura (`ms-seguridad` y `ms-usuarios`).
2. Gestionar el catálogo de propiedades ofrecidas (casas, departamentos, etc.) (`ms-catalogo`).
3. Controlar el estado de disponibilidad y stock de cada propiedad (`ms-inventario`).
4. Crear pedidos para la compra o arriendo de propiedades (`ms-pedidos`).
5. Registrar y procesar pagos asociados a los pedidos (`ms-pagos`).
6. Coordinar la logística del proceso inmobiliario como la revisión de documentación y firmas ante notaría (`ms-logistica`).
7. Enviar notificaciones automatizadas y alertas de estado (`ms-notificaciones`).
8. Realizar seguimiento del estado de los pedidos (`ms-seguimiento`).
9. Generar reportes estadísticos de ventas e ingresos (`ms-reportes`).

---

# 2. Arquitectura general

```text
Cliente externo / Postman / Navegador
        |
        v
API Gateway :8080
        |
        +--> ms-seguridad      :8081  -> db_seguridad
        +--> ms-usuarios       :8082  -> db_usuarios
        +--> ms-catalogo       :8083  -> db_catalogo
        +--> ms-inventario     :8084  -> db_inventario
        +--> ms-pedidos        :8085  -> db_pedidos
        +--> ms-pagos          :8086  -> db_pagos
        +--> ms-logistica      :8087  -> db_logistica
        +--> ms-promociones    :8088  -> db_promociones
        +--> ms-seguimiento    :8089  -> db_seguimiento
        +--> ms-notificaciones :8090  -> db_notificaciones
        +--> ms-reportes       :8091  -> db_reportes

Eureka Server :8761
```

---

# 3. Microservicios del sistema

| Módulo              | Puerto | Responsabilidad                                    |
| ------------------- | -----: | -------------------------------------------------- |
| `eureka-server`     |   8761 | Registro y descubrimiento de servicios             |
| `api-gateway`       |   8080 | Punto único de entrada a las APIs                  |
| `ms-seguridad`      |   8081 | Autenticación, registro y seguridad                |
| `ms-usuarios`       |   8082 | Gestión de datos personales de usuarios            |
| `ms-catalogo`       |   8083 | Catálogo de propiedades inmobiliarias              |
| `ms-inventario`     |   8084 | Control de disponibilidad/stock de propiedades     |
| `ms-pedidos`        |   8085 | Gestión de pedidos de compra/arriendo              |
| `ms-pagos`          |   8086 | Procesamiento de transacciones y pagos             |
| `ms-logistica`      |   8087 | Coordinación del proceso logístico y notarial      |
| `ms-promociones`    |   8088 | Gestión de códigos de descuento                    |
| `ms-seguimiento`    |   8089 | Trazabilidad del estado de las transacciones       |
| `ms-notificaciones` |   8090 | Envío de notificaciones por correo electrónico     |
| `ms-reportes`       |   8091 | Generación de reportes y estadísticas consolidadas |

---

# 4. Tecnologías utilizadas

* Java 21
* Spring Boot 3.4.2
* Spring Cloud 2024.0.0
* Eureka Server
* Eureka Client
* Spring Cloud Gateway
* OpenFeign
* Spring Web
* Spring Data JPA
* MySQL
* XAMPP
* Lombok
* Bean Validation
* Swagger / OpenAPI 2.6.0
* JUnit 5
* Mockito
* Maven
* VSCode

---

# 5. Estructura del proyecto

```text
sistema-gestion-parent/
│
├── pom.xml
├── README.md
├── subtitulos-video.txt
│
├── docs/
│   ├── script-bd.sql
│   ├── endpoints.md
│   └── orden-ejecucion.md
│
├── eureka-server/
│   ├── pom.xml
│   └── src/
│
├── api-gateway/
│   ├── pom.xml
│   └── src/
│
├── ms-seguridad/
│   ├── pom.xml
│   └── src/
│
├── ms-usuarios/
│   ├── pom.xml
│   └── src/
│
├── ms-catalogo/
│   ├── pom.xml
│   └── src/
│
├── ms-inventario/
│   ├── pom.xml
│   └── src/
│
├── ms-pedidos/
│   ├── pom.xml
│   └── src/
│
├── ms-pagos/
│   ├── pom.xml
│   └── src/
│
├── ms-logistica/
│   ├── pom.xml
│   └── src/
│
├── ms-promociones/
│   ├── pom.xml
│   └── src/
│
├── ms-seguimiento/
│   ├── pom.xml
│   └── src/
│
├── ms-notificaciones/
│   ├── pom.xml
│   └── src/
│
└── ms-reportes/
    ├── pom.xml
    └── src/
```
 
---

# 6. Bases de datos

El proyecto usa una base de datos independiente por microservicio.

| Microservicio       | Base de datos       | Tabla principal         |
| ------------------- | ------------------- | ----------------------- |
| `ms-seguridad`      | `db_seguridad`      | `credenciales_usuarios` |
| `ms-usuarios`       | `db_usuarios`       | `usuario`               |
| `ms-catalogo`       | `db_catalogo`       | `catalogo`              |
| `ms-inventario`     | `db_inventario`     | `inventario`            |
| `ms-pedidos`        | `db_pedidos`        | `pedido`                |
| `ms-pagos`          | `db_pagos`          | `pago`                  |
| `ms-logistica`      | `db_logistica`      | `logistica`             |
| `ms-promociones`    | `db_promociones`    | `promociones`           |
| `ms-seguimiento`    | `db_seguimiento`    | `seguimiento`           |
| `ms-notificaciones` | `db_notificaciones` | `notificacion`          |
| `ms-reportes`       | `db_reportes`       | `reporte`               |

El script de creación de bases y datos iniciales se encuentra en:

```text
docs/script-bd.sql
```

---

# 7. Configuración de MySQL

Este proyecto está configurado para usar MySQL mediante XAMPP en el puerto:

```text
3307
```

Ejemplo de configuración usada en los microservicios:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/db_usuarios?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Si el equipo usa MySQL en el puerto `3306`, se debe cambiar la URL en cada microservicio:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_usuarios?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
```

---

# 8. Orden de ejecución

Antes de levantar los microservicios, se debe iniciar XAMPP y activar MySQL.

Luego se deben ejecutar los servicios en este orden:

| Orden | Servicio            | Puerto |
| ----: | ------------------- | -----: |
|     1 | `eureka-server`     |   8761 |
|     2 | `ms-usuarios`       |   8082 |
|     3 | `ms-seguridad`      |   8081 |
|     4 | `ms-catalogo`       |   8083 |
|     5 | `ms-inventario`     |   8084 |
|     6 | `ms-promociones`    |   8088 |
|     7 | `ms-pedidos`        |   8085 |
|     8 | `ms-pagos`          |   8086 |
|     9 | `ms-logistica`      |   8087 |
|    10 | `ms-seguimiento`    |   8089 |
|    11 | `ms-notificaciones` |   8090 |
|    12 | `ms-reportes`       |   8091 |
|    13 | `api-gateway`       |   8080 |

---

# 9. Ejecución desde VSCode

Se recomienda usar la extensión **Spring Boot Dashboard** de VSCode.

Desde el panel de Spring Boot se pueden iniciar los servicios uno por uno:

```text
eureka-server
ms-usuarios
ms-seguridad
ms-catalogo
ms-inventario
ms-promociones
ms-pedidos
ms-pagos
ms-logistica
ms-seguimiento
ms-notificaciones
ms-reportes
api-gateway
```

También se pueden ejecutar desde terminal.

Ejemplo:

```bash
cd eureka-server
mvn spring-boot:run
```

---

# 10. Puesta en marcha automatizada (Versión Nativa)

El archivo `arrancar-nativo.bat` incluido en el ZIP de la Versión Sin Docker automatiza el arranque completo del sistema de forma **secuencial y cronometrada** en tres fases:

| Fase | Servicios que arranca | Espera antes de continuar |
| ---: | --------------------- | ------------------------- |
| **1** | `eureka-server` | 20 segundos |
| **2** | `ms-usuarios`, `ms-seguridad`, `ms-catalogo`, `ms-inventario`, `ms-promociones`, `ms-pedidos`, `ms-pagos`, `ms-logistica`, `ms-seguimiento`, `ms-notificaciones`, `ms-reportes` | 30 segundos |
| **3** | `api-gateway` | — |

Para ejecutar el sistema completo desde Windows, doble clic sobre el archivo o ejecutar desde `cmd`:

```cmd
arrancar-nativo.bat
```

> **Requisito previo:** XAMPP debe estar corriendo con el servicio MySQL activo antes de lanzar el script.

La Versión Con Docker incluye el archivo `docker-compose.yml` y el script `arrancar-sistema.bat`, que levanta automáticamente los contenedores de MySQL y todos los microservicios sin necesidad de XAMPP.

---

# 11. Compilación del proyecto completo

Desde la raíz del proyecto:

```bash
mvn clean install
```

El proyecto compila ejecutando la suite completa de pruebas unitarias. Se implementó cobertura con **JUnit 5 y Mockito** en capas de servicio y controlador para todos los microservicios de negocio.

---

# 12. Eureka Server

La consola de Eureka se encuentra en:

```text
http://localhost:8761
```

Cuando todos los servicios están levantados, deben aparecer registrados:

```text
API-GATEWAY
MS-SEGURIDAD
MS-USUARIOS
MS-CATALOGO
MS-INVENTARIO
MS-PEDIDOS
MS-PAGOS
MS-LOGISTICA
MS-PROMOCIONES
MS-SEGUIMIENTO
MS-NOTIFICACIONES
MS-REPORTES
```

---

# 13. API Gateway

El API Gateway permite consumir todos los microservicios desde el puerto:

```text
http://localhost:8080
```

Rutas principales:

| Recurso          | URL                                        |
| ---------------- | ------------------------------------------ |
| Seguridad / Auth | `http://localhost:8080/api/auth/registrar` |
| Usuarios         | `http://localhost:8080/api/usuarios`       |
| Catálogo         | `http://localhost:8080/api/catalogo`       |
| Inventario       | `http://localhost:8080/api/inventario`     |
| Pedidos          | `http://localhost:8080/api/pedidos`        |
| Pagos            | `http://localhost:8080/api/pagos`          |
| Logística        | `http://localhost:8080/api/logistica`      |
| Promociones      | `http://localhost:8080/api/promociones`    |
| Seguimiento      | `http://localhost:8080/api/seguimiento`    |
| Notificaciones   | `http://localhost:8080/api/notificaciones` |
| Reportes         | `http://localhost:8080/api/reportes`       |

---

# 14. Swagger / OpenAPI

Cada microservicio de negocio documenta sus endpoints mediante anotaciones **`@Tag`**, **`@Operation`** y **`@ApiResponse`**, describiendo el comportamiento de cada operación y los códigos de estado HTTP esperados (`200`, `201`, `400`, `404`, `500`).

Para revisar la documentación interactiva de cada microservicio, acceder directamente por su puerto:

| Microservicio       | Swagger UI                               |
| ------------------- | ---------------------------------------- |
| `ms-seguridad`      | `http://localhost:8081/swagger-ui.html`  |
| `ms-usuarios`       | `http://localhost:8082/swagger-ui.html`  |
| `ms-catalogo`       | `http://localhost:8083/swagger-ui.html`  |
| `ms-inventario`     | `http://localhost:8084/swagger-ui.html`  |
| `ms-pedidos`        | `http://localhost:8085/swagger-ui.html`  |
| `ms-pagos`          | `http://localhost:8086/swagger-ui.html`  |
| `ms-logistica`      | `http://localhost:8087/swagger-ui.html`  |
| `ms-promociones`    | `http://localhost:8088/swagger-ui.html`  |
| `ms-seguimiento`    | `http://localhost:8089/swagger-ui.html`  |
| `ms-notificaciones` | `http://localhost:8090/swagger-ui.html`  |
| `ms-reportes`       | `http://localhost:8091/swagger-ui.html`  |

El Gateway se usa para consumir APIs, pero no para centralizar Swagger en esta versión.

---

# 15. Comunicación entre microservicios

El proyecto usa OpenFeign para comunicación entre servicios.

| Servicio origen | Servicio destino | Objetivo                                                              |
| --------------- | ---------------- | --------------------------------------------------------------------- |
| `ms-seguridad`  | `MS-USUARIOS`    | Registrar automáticamente el perfil de usuario al registrar credenciales |
| `ms-reportes`   | `ms-pedidos`     | Consolidar listado de pedidos para estadísticas                       |
| `ms-reportes`   | `ms-pagos`       | Consolidar listado de pagos para estadísticas financieras             |

---

# 16. Flujo funcional principal

## Paso 1: Registrar usuario (Seguridad + Perfil)

```http
POST http://localhost:8080/api/auth/registrar
Content-Type: application/json
```

```json
{
  "email": "juan.perez@correo.cl",
  "password": "SecurePassword123",
  "rol": "CLIENTE",
  "nombre": "Juan",
  "apellido": "Pérez",
  "telefono": "987654321"
}
```

## Paso 2: Registrar propiedad en Catálogo

```http
POST http://localhost:8080/api/catalogo
Content-Type: application/json
```

```json
{
  "nombre": "Departamento Vista Mar",
  "descripcion": "Hermoso departamento de 2 habitaciones frente al mar",
  "ubicacion": "Viña del Mar",
  "precio": 85000000.00,
  "tipo": "DEPARTAMENTO",
  "metrosCuadrados": 75,
  "habitaciones": 2,
  "imagenUrl": "http://img.example.com/depto1.jpg"
}
```

## Paso 3: Registrar disponibilidad en Inventario

```http
POST http://localhost:8080/api/inventario
Content-Type: application/json
```

```json
{
  "idPropiedad": 1,
  "stock": 1,
  "estado": "DISPONIBLE"
}
```

## Paso 4: Crear pedido de compra

```http
POST http://localhost:8080/api/pedidos
Content-Type: application/json
```

```json
{
  "idUsuario": 1,
  "idPropiedad": 1,
  "idPromocion": null,
  "montoFinal": 85000000.00
}
```

## Paso 5: Registrar pago de reserva/compra

```http
POST http://localhost:8080/api/pagos
Content-Type: application/json
```

```json
{
  "idPedido": 1,
  "monto": 5000000.00,
  "metodoPago": "TRANSFERENCIA",
  "estado": "PENDIENTE",
  "fechaPago": "2026-06-27T10:30:00"
}
```

---

# 17. Validaciones implementadas

## `ms-seguridad` / `RegistroRequestDto`

* Correo obligatorio y con formato de correo electrónico válido.
* Contraseña obligatoria.
* Rol obligatorio.
* Nombre obligatorio.
* Apellido obligatorio.

## `ms-usuarios` / `Usuario`

* Nombre obligatorio.
* Apellido obligatorio.
* Email obligatorio, formato de correo válido y único en base de datos.
* ID de autenticación (`idAuth`) obligatorio.

## `ms-catalogo` / `Catalogo`

* Nombre obligatorio.
* Ubicación obligatoria.
* Precio obligatorio y mayor a 0.
* Tipo de propiedad obligatorio.
* Metros cuadrados deben ser valores positivos.
* Número de habitaciones no puede ser negativo (mínimo 0).

## `ms-inventario` / `Inventario`

* ID de propiedad obligatorio.
* Stock obligatorio y no puede ser negativo (mínimo 0).
* Estado obligatorio (`DISPONIBLE`, `RESERVADO`, `VENDIDO`, `BLOQUEADO`).

## `ms-pedidos` / `Pedido`

* ID de usuario obligatorio.
* ID de propiedad obligatorio.
* Monto final obligatorio y mayor o igual a 0.
* Estado del pedido (`CREADO`, `RESERVADO`, `PAGADO`, `EN_PROCESO`, `COMPLETADO`, `CANCELADO`).

## `ms-pagos` / `Pago`

* ID de pedido obligatorio.
* Monto de pago obligatorio y mayor a 0.
* Método de pago obligatorio (`TRANSFERENCIA`, `TARJETA`, `EFECTIVO`).
* Estado de pago (`PENDIENTE`, `APROBADO`, `RECHAZADO`).

## `ms-promociones` / `Promocion`

* Código de la promoción obligatorio y único.
* Porcentaje de descuento obligatorio, debe estar entre 1% y 100%.

## `ms-logistica` / `Logistica`

* ID de pedido obligatorio.
* Notaría obligatoria.
* Estado logístico obligatorio (`DOCUMENTACION_EN_REVISION`, `DOCUMENTACION_APROBADA`, `FIRMA_AGENDADA`, `ESCRITURA_EN_PROCESO`, `ENTREGA_FINALIZADA`).

## `ms-seguimiento` / `Seguimiento`

* ID de pedido obligatorio.
* Estado obligatorio (`PEDIDO_CREADO`, `RESERVA_CONFIRMADA`, `PAGO_APROBADO`, `DOCUMENTACION_EN_REVISION`, `FIRMA_NOTARIAL_AGENDADA`, `ESCRITURA_COMPLETADA`, `ENTREGA_FINALIZADA`).
* Descripción obligatoria.

## `ms-notificaciones` / `Notificacion`

* Destinatario obligatorio y con formato de correo válido.
* Asunto obligatorio.
* Mensaje obligatorio.

## `ms-reportes` / `Reporte`

* Total de ventas obligatorio (mayor o igual a 0).
* Total de pedidos obligatorio (mayor o igual a 0).
* Pedidos completados obligatorio (mayor o igual a 0).

---

# 18. Manejo de errores

Cada microservicio incorpora manejo de errores mediante `@RestControllerAdvice`.

Ejemplo de respuesta de error:

```json
{
  "fecha": "2026-06-27T10:30:00",
  "estado": 400,
  "error": "Error de validación",
  "mensaje": "Existen campos inválidos en la solicitud",
  "ruta": "/api/auth/registrar",
  "validaciones": {
    "email": "El formato del correo no es válido",
    "password": "La contraseña es obligatoria"
  }
}
```

---

# 19. Logs

Cada microservicio incorpora logs básicos mediante Lombok:

```java
@Slf4j
```

Ejemplo:

```java
log.info("Registrando nueva propiedad en el catálogo");
log.warn("Propiedad no encontrada con el ID proporcionado");
log.error("Error al comunicarse con ms-usuarios desde ms-seguridad");
```

---

# 20. Comandos útiles

## Compilar todo el proyecto (incluye pruebas unitarias)

```bash
mvn clean install
```

## Ejecutar un microservicio desde terminal

```bash
cd ms-usuarios
mvn spring-boot:run
```

## Compilar solo un módulo

```bash
mvn clean install -pl ms-usuarios
```

## Compilar un módulo y sus dependencias

```bash
mvn clean install -pl ms-usuarios -am
```

---

# 21. Documentación adicional

La documentación complementaria se encuentra en:

```text
docs/endpoints.md
docs/orden-ejecucion.md
docs/script-bd.sql
```

---

# 22. Estado actual del proyecto

| Elemento                  | Estado        |
| ------------------------- | ------------- |
| Proyecto padre Maven      | Implementado  |
| Bases de datos MySQL      | Implementadas |
| Eureka Server             | Implementado  |
| API Gateway               | Implementado  |
| `ms-seguridad`            | Implementado  |
| `ms-usuarios`             | Implementado  |
| `ms-catalogo`             | Implementado  |
| `ms-inventario`           | Implementado  |
| `ms-pedidos`              | Implementado  |
| `ms-pagos`                | Implementado  |
| `ms-logistica`            | Implementado  |
| `ms-promociones`          | Implementado  |
| `ms-seguimiento`          | Implementado  |
| `ms-notificaciones`       | Implementado  |
| `ms-reportes`             | Implementado  |
| Swagger por microservicio | Implementado  |
| Feign Client              | Implementado  |
| Manejo de errores         | Implementado  |
| Logs                      | Implementado  |
| Testing (JUnit 5/Mockito) | Implementado  |
| Script arrancar-nativo.bat | Implementado |
| Docker Compose            | Implementado  |
| Frontend web              | Pendiente     |

---

# 23. Próximas mejoras sugeridas

* Crear frontend web con Spring Boot + Thymeleaf o React.
* Crear colección Postman con todos los endpoints documentados.
* Centralización de Swagger/OpenAPI en el Gateway.
* Agregar perfiles `dev` y `test` con base de datos H2 en memoria.
