package vallegrande.edu.pe.Agroguard.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO para crear o actualizar un Tratamiento
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TratamientoRequestDTO {
    
    @NotBlank(message = "El nombre del tratamiento es requerido")
    private String nameTreatment;

    @NotBlank(message = "El tipo de tratamiento es requerido")
    private String typeTreatment;

    private String description;

    private String activeIngredient;

    private String recommendedDos;

    private String applicationMode;

    @NotNull(message = "El campo isOrganic es requerido")
    private Boolean isOrganic;
}
