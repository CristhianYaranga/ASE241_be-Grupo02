package vallegrande.edu.pe.Agroguard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO para crear o actualizar una Plaga
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlagaRequestDTO {
    
    @NotBlank(message = "El nombre de la plaga es requerido")
    private String namePests;

    @NotBlank(message = "La categoria es requerida")
    private String category;

    private String description;

    private String symptoms;

    @NotBlank(message = "El nivel de riesgo es requerido")
    private String riskLevel;

    private String imageUrl;
}
