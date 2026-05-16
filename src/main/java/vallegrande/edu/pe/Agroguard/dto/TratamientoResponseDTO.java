package vallegrande.edu.pe.Agroguard.dto;

import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO para respuestas de Tratamientos
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TratamientoResponseDTO {
    
    private Long id;
    private String nameTreatment;
    private String typeTreatment;
    private String description;
    private String activeIngredient;
    private String recommendedDos;
    private String applicationMode;
    private Boolean isOrganic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
