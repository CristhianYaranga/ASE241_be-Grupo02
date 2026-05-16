package vallegrande.edu.pe.Agroguard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

/**
 * Configuración de OpenAPI/Swagger
 * Define la documentación de la API REST
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("AgroGuard API")
                .version("1.0.0")
                .description("API REST para gestión de plagas y enfermedades en cultivos agrícolas\n\n" +
                           "**Características:**\n" +
                           "- Gestión completa de plagas (CRUD)\n" +
                           "- Registro y monitoreo de tratamientos\n" +
                           "- Filtrado y búsqueda avanzada\n" +
                           "- Estadísticas en tiempo real\n" +
                           "- Validación robusta de datos\n" +
                           "- Manejo global de errores")
                .contact(new Contact()
                    .name("AgroGuard Team")
                    .email("info@agroguard.com"))
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")))
            .servers(List.of(
                new Server()
                    .url("http://localhost:3000/api")
                    .description("Servidor de desarrollo"),
                new Server()
                    .url("http://localhost:8080/api")
                    .description("Servidor alternativo")
            ));
    }
}
