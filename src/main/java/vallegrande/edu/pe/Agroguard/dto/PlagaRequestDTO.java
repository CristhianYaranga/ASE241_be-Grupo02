package vallegrande.edu.pe.Agroguard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

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
    private String nombre;
    
    @NotBlank(message = "El tipo de plaga es requerido")
    private String tipo; // "insectos", "hongos", "bacterias", "virus"
    
    @NotBlank(message = "La prioridad es requerida")
    private String prioridad; // "baja", "media", "alta"
    
    @NotBlank(message = "La descripción es requerida")
    private String descripcion;
    
    private List<String> sintomas;
    
    private List<String> tratamientosRecomendados;
    
    private List<String> cultivosAfectados;
}
