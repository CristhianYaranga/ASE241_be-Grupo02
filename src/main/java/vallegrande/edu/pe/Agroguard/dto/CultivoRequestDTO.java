package vallegrande.edu.pe.Agroguard.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO para crear o actualizar un Cultivo
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CultivoRequestDTO {
    
    @NotBlank(message = "El nombre del cultivo es requerido")
    private String nombre;
    
    private String descripcion;
}
