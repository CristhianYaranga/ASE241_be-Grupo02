package vallegrande.edu.pe.Agroguard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración CORS para la aplicación
 * Permite comunicación entre frontend Angular y backend Spring Boot
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:4200",      // Angular dev server
                "http://localhost:3000",       // Local testing
                "http://127.0.0.1:4200",
                "http://127.0.0.1:3000"
            )
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600)
            .exposedHeaders("X-Total-Count", "X-Total-Pages");
    }
}
