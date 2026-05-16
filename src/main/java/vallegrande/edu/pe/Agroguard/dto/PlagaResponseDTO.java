package vallegrande.edu.pe.Agroguard.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO para respuestas de Plagas
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlagaResponseDTO {
    
    private Long id;
    private String namePests;
    private String category;
    private String description;
    private String symptoms;
    private String riskLevel;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
