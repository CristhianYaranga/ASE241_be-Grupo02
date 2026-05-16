package vallegrande.edu.pe.Agroguard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación AgroGuard - Gestión de Plagas y Enfermedades Agrícolas
 * 
 * Punto de entrada de la aplicación Spring Boot
 * 
 * **Características principales:**
 * - API REST completa para gestión de plagas
 * - Monitoreo y registro de tratamientos
 * - Base de datos SQL Server integrada
 * - Documentación OpenAPI/Swagger
 * - Validación robusta y manejo global de errores
 * - Configuración CORS para Angular frontend
 * 
 * **URLs importantes:**
 * - API: http://localhost:3000/api
 * - Swagger UI: http://localhost:3000/api/swagger-ui.html
 * - API Docs: http://localhost:3000/api/v3/api-docs
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@SpringBootApplication
public class AgroguardApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgroguardApplication.class, args);
	}

}

