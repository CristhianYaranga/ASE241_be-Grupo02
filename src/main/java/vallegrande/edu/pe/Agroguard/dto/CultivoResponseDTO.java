package vallegrande.edu.pe.Agroguard.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO para respuestas de Cultivos
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CultivoResponseDTO {
    
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
