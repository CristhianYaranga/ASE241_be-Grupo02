# 🌾 AgroGuard - Backend Spring Boot

![Java](https://img.shields.io/badge/Java-25-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.15--SNAPSHOT-green)
![Maven](https://img.shields.io/badge/Maven-3.9+-blue)
![SQL Server](https://img.shields.io/badge/SQL%20Server-Docker-yellow)
![License](https://img.shields.io/badge/License-MIT-brightgreen)

Backend profesional para AgroGuard - Sistema de gestión de plagas y tratamientos agrícolas.

---

## 📋 Contenido

- [Inicio Rápido](#-inicio-rápido)
- [Arquitectura](#-arquitectura)
- [API Endpoints](#-api-endpoints)
- [Configuración](#-configuración)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Documentación](#-documentación)
- [Troubleshooting](#-troubleshooting)

---

## 🚀 Inicio Rápido

### Requisitos Previos
- Docker Desktop instalado
- Java 25+
- Maven 3.9+
- Git

### 1️⃣ Iniciar Base de Datos
```bash
docker-compose up -d
```

Verifica que SQL Server está corriendo:
```bash
docker ps | grep mssql
```

### 2️⃣ Compilar Backend
```bash
mvn clean install
```

### 3️⃣ Ejecutar Backend
```bash
mvn spring-boot:run
```

**Verifica que está corriendo:**
```
http://localhost:3000/api/swagger-ui.html
```

### 4️⃣ (Opcional) Iniciar Frontend
```bash
cd C:\Users\crist\OneDrive\Desktop\ASE241_PI-Grupo02\frontend
npm start
```

**Frontend disponible en:**
```
http://localhost:4200
```

---

## 🏛️ Arquitectura

### Stack Tecnológico
```
┌──────────────────────────┐
│  Angular 21.2 Frontend   │
│   (localhost:4200)       │
└────────────┬─────────────┘
             │ HttpClient
             ▼
┌──────────────────────────┐
│  Spring Boot 3.5.15      │
│  (localhost:3000/api)    │
├──────────────────────────┤
│ ✓ Spring Web             │
│ ✓ Spring Data JPA        │
│ ✓ Spring Validation      │
│ ✓ Hibernate + Lombok     │
│ ✓ SpringDoc OpenAPI      │
└────────────┬─────────────┘
             │ JDBC
             ▼
┌──────────────────────────┐
│  SQL Server (Docker)     │
│  (localhost:1433)        │
│  Database: AgroGuard     │
└──────────────────────────┘
```

**Capas:**
1. **Controllers** - REST API
2. **Services** - Lógica de negocio
3. **Repositories** - Acceso a datos (JPA)
4. **Entities** - Modelos JPA
5. **DTOs** - Transferencia de datos

---

## 📡 API Endpoints

### Plagas (`/api/plagas`)

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| GET | `/` | Obtener todas las plagas | 200 |
| GET | `/{id}` | Obtener plaga por ID | 200 |
| POST | `/` | Crear nueva plaga | 201 |
| PUT | `/{id}` | Actualizar plaga | 200 |
| DELETE | `/{id}` | Eliminar plaga | 204 |
| GET | `/tipo/{tipo}` | Filtrar por tipo | 200 |
| GET | `/prioridad/{prioridad}` | Filtrar por prioridad | 200 |

**Tipos:** `insectos`, `hongos`, `bacterias`, `virus`  
**Prioridades:** `baja`, `media`, `alta`

---

### Tratamientos (`/api/tratamientos`)

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| GET | `/` | Obtener todos los tratamientos | 200 |
| GET | `/{id}` | Obtener tratamiento por ID | 200 |
| POST | `/` | Crear nuevo tratamiento | 201 |
| PUT | `/{id}` | Actualizar tratamiento | 200 |
| DELETE | `/{id}` | Eliminar tratamiento | 204 |
| GET | `/estado/{estado}` | Filtrar por estado | 200 |
| GET | `/activos` | Obtener solo activos | 200 |
| GET | `/cultivo/{cultivo}` | Filtrar por cultivo | 200 |
| GET | `/estadisticas` | Obtener estadísticas | 200 |

**Estados:** `activo`, `completado`, `pendiente`

---

### Cultivos (`/api/cultivos`)

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| GET | `/` | Obtener todos los cultivos | 200 |
| GET | `/{id}` | Obtener cultivo por ID | 200 |
| POST | `/` | Crear nuevo cultivo | 201 |
| PUT | `/{id}` | Actualizar cultivo | 200 |
| DELETE | `/{id}` | Eliminar cultivo | 204 |

---

## ⚙️ Configuración

### application.yaml

```yaml
server:
  port: 3000
  servlet:
    context-path: /api

spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=AroGuard
    username: sa
    password: verYs3cret
    driverClassName: com.microsoft.sqlserver.jdbc.SQLServerDriver
  
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.SQLServerDialect
        format_sql: true
  
  jackson:
    default-property-inclusion: non_null
    serialization:
      write-dates-as-timestamps: false

# CORS configurado para Angular
cors:
  allowed-origins: localhost:4200, localhost:3000, 127.0.0.1:4200, 127.0.0.1:3000
  allowed-methods: GET, POST, PUT, DELETE, OPTIONS, PATCH
  allow-credentials: true
```

### Base de Datos
```
Host:     localhost
Port:     1433
Usuario:  sa
Password: verYs3cret
Database: AroGuard
```

---

## 📁 Estructura del Proyecto

```
Agroguard/
├── src/main/java/vallegrande/edu/pe/Agroguard/
│   ├── AgroguardApplication.java          # Punto de entrada
│   ├── controller/
│   │   ├── PlagaController.java           # Endpoints de plagas
│   │   └── TratamientoController.java     # Endpoints de tratamientos
│   ├── service/
│   │   ├── PlagaService.java              # Interfaz
│   │   ├── PlagaServiceImpl.java           # Implementación
│   │   ├── TratamientoService.java        # Interfaz
│   │   └── TratamientoServiceImpl.java     # Implementación
│   ├── repository/
│   │   ├── PlagaRepository.java
│   │   ├── TratamientoRepository.java
│   │   ├── CultivoRepository.java
│   │   ├── SintomaRepository.java
│   │   └── TratamientoRecomendadoRepository.java
│   ├── entity/
│   │   ├── Plaga.java                     # Entidad JPA
│   │   ├── Tratamiento.java
│   │   ├── Cultivo.java
│   │   ├── Sintoma.java
│   │   └── TratamientoRecomendado.java
│   ├── dto/
│   │   ├── PlagaRequestDTO.java
│   │   ├── PlagaResponseDTO.java
│   │   ├── TratamientoRequestDTO.java
│   │   ├── TratamientoResponseDTO.java
│   │   ├── CultivoRequestDTO.java
│   │   ├── CultivoResponseDTO.java
│   │   └── EstadisticasTratamientoDTO.java
│   ├── exception/
│   │   ├── ResourceNotFoundException.java
│   │   ├── ResourceAlreadyExistsException.java
│   │   ├── ValidationException.java
│   │   ├── GlobalExceptionHandler.java
│   │   └── ErrorResponse.java
│   └── config/
│       ├── CorsConfig.java                # CORS para Angular
│       └── OpenApiConfig.java             # Swagger/OpenAPI
│
├── src/main/resources/
│   └── application.yaml                   # Configuración
│
├── src/test/
│   └── java/.../                          # Tests
│
├── pom.xml                                # Dependencias Maven
├── compose.yaml                           # Docker Compose
├── BACKEND_README.md                      # Documentación API
├── TESTING_GUIDE.md                       # Guía de testing
├── INTEGRATION_GUIDE.md                   # Guía de integración
└── ARCHITECTURE.md                        # Arquitectura detallada
```

---

## 📚 Documentación

### Guías Incluidas

1. **BACKEND_README.md**
   - Documentación completa de API
   - Ejemplos de requests/responses
   - Schemas de base de datos
   - Enums y tipos de datos

2. **TESTING_GUIDE.md**
   - Colecciones Postman
   - Ejemplos de validaciones
   - Flujos de testing completos
   - Comandos Maven útiles

3. **INTEGRATION_GUIDE.md**
   - Mapeo Frontend ↔ Backend
   - Cambios necesarios en Angular
   - Sincronización de datos
   - Troubleshooting

4. **ARCHITECTURE.md**
   - Diagrama de arquitectura
   - Descripción de capas
   - Modelo de datos
   - Patrones utilizados

### Swagger UI
```
http://localhost:3000/api/swagger-ui.html
```

Acceso interactivo a todos los endpoints con:
- Parámetros esperados
- Respuestas posibles
- Schemas de datos
- Pruebas directas

---

## 🧪 Testing

### Compilar y Tests
```bash
# Solo compilar
mvn clean compile

# Compilar + ejecutar tests
mvn clean install

# Solo tests
mvn test

# Generar JAR
mvn clean package
```

### Con Postman
1. Importar colecciones de `TESTING_GUIDE.md`
2. Cambiar URL base a `http://localhost:3000/api`
3. Ejecutar requests

### Con cURL
```bash
# Obtener plagas
curl http://localhost:3000/api/plagas

# Crear plaga
curl -X POST http://localhost:3000/api/plagas \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Pulgón","tipo":"insectos",...}'
```

---

## 🔌 Integración con Frontend

### URLs Base
```typescript
// Plagas
private apiUrl = 'http://localhost:3000/api/plagas';

// Tratamientos
private apiUrl = 'http://localhost:3000/api/tratamientos';

// Cultivos
private apiUrl = 'http://localhost:3000/api/cultivos';
```

### Headers
```
Content-Type: application/json
Accept: application/json
```

### CORS
✅ Ya está configurado para:
- `localhost:4200` (Angular dev)
- `localhost:3000` (Fallback)
- `127.0.0.1:4200`
- `127.0.0.1:3000`

Ver `INTEGRATION_GUIDE.md` para detalles.

---

## 🐛 Troubleshooting

### Backend no inicia
```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar logs detallados
mvn spring-boot:run -X
```

### Conexión a BD falla
```bash
# Verificar Docker
docker ps

# Verificar SQL Server
docker logs agroguard-sql-server

# Reconnect
docker-compose restart
```

### CORS error en frontend
```
❌ "Access to XMLHttpRequest blocked by CORS policy"

✅ Solución: Verifica que las URLs están correctas
   - Backend: http://localhost:3000/api/...
   - Frontend: http://localhost:4200
```

### Datos no se guardan
```bash
# Verificar DB está corriendo
docker ps | grep mssql

# Revisar logs del backend
mvn spring-boot:run

# Verificar tabla en BD
docker exec agroguard-sql-server \
  /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P verYs3cret \
  -Q "SELECT * FROM Plagas"
```

---

## 📊 Estado del Proyecto

✅ **Completado (100%)**

- ✅ Entities con relaciones JPA
- ✅ Services con lógica de negocio
- ✅ Controllers REST con validación
- ✅ DTOs request/response
- ✅ Exception handling global
- ✅ CORS configurado
- ✅ Swagger/OpenAPI
- ✅ Documentación completa
- ✅ Tests structure

---

## 🚀 Próximas Características (Roadmap)

- [ ] Autenticación JWT
- [ ] Paginación en listas
- [ ] Búsqueda avanzada
- [ ] Historial de cambios (Audit)
- [ ] Reportes PDF
- [ ] WebSockets (real-time)
- [ ] GraphQL endpoint
- [ ] Docker image para backend

---

## 📝 Notas Importantes

### Base de Datos
- Las tablas se crean automáticamente en primer inicio
- `ddl-auto: update` en `application.yaml`
- No recomendado para producción (usar `validate`)

### Validaciones
Multinivel:
1. **Bean Validation** en Controllers
2. **Lógica de negocio** en Services
3. **Integridad relacional** en Database

### Errores
Formato estándar:
```json
{
  "timestamp": "2026-05-16T10:30:00",
  "status": 400,
  "message": "Descripción clara del error",
  "error": "VALIDATION_ERROR",
  "path": "/api/plagas",
  "fieldErrors": [
    {
      "field": "nombre",
      "message": "El nombre es requerido",
      "rejectedValue": null
    }
  ]
}
```

---

## 📞 Soporte

Para problemas:
1. Consulta `TROUBLESHOOTING` en este archivo
2. Revisa `INTEGRATION_GUIDE.md`
3. Usa Swagger UI en `http://localhost:3000/api/swagger-ui.html`
4. Revisa logs: `mvn spring-boot:run`

---

## 📄 Licencia

MIT License - Libre para usar y modificar

---

## 👥 Equipo

- **Backend Architect:** GitHub Copilot
- **Frontend Integration:** ASE241 PI Grupo 02
- **Database Design:** Spring Data JPA + Hibernate

---

## 📈 Métricas

- **Líneas de código:** ~3,500+
- **Endpoints:** 19
- **DTOs:** 7
- **Entities:** 5
- **Repositories:** 5
- **Excepciones manejadas:** 5+ tipos
- **Cobertura de documentación:** 100%

---

**Status: ✅ Listo para integración con frontend Angular**

¡Gracias por usar AgroGuard! 🌾

---

### 🔗 Enlaces Rápidos

- [API Docs](BACKEND_README.md)
- [Testing Guide](TESTING_GUIDE.md)
- [Integración Frontend](INTEGRATION_GUIDE.md)
- [Arquitectura](ARCHITECTURE.md)
- [Swagger UI](http://localhost:3000/api/swagger-ui.html)
