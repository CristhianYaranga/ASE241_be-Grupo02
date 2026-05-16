package vallegrande.edu.pe.Agroguard.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Estructura de respuesta para errores
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String error;
    private String path;
    private List<FieldError> fieldErrors;
    
    /**
     * Estructura para errores de validación de campos
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FieldError {
        private String field;
        private String message;
        private Object rejectedValue;
    }
}
