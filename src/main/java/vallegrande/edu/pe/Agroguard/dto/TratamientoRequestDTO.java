package vallegrande.edu.pe.Agroguard.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

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
    
    @NotNull(message = "El ID del cultivo es requerido")
    private Long cultivoId;
    
    @NotBlank(message = "El sector es requerido")
    private String sector;
    
    @NotNull(message = "El ID de la plaga es requerido")
    private Long plagaId;
    
    @NotBlank(message = "El tratamiento aplicado es requerido")
    private String tratamientoAplicado;
    
    @NotBlank(message = "El estado es requerido")
    private String estado; // "activo", "completado", "pendiente"
    
    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDateTime fechaInicio;
    
    private LocalDateTime fechaFin;
    
    @NotNull(message = "El progreso total es requerido")
    @Min(value = 1, message = "El progreso total debe ser mayor a 0")
    private Integer progresoTotal;
    
    @Min(value = 0, message = "El progreso actual no puede ser negativo")
    private Integer progresoActual = 0;
    
    @DecimalMin(value = "0", message = "La efectividad debe ser mayor o igual a 0")
    @DecimalMax(value = "100", message = "La efectividad debe ser menor o igual a 100")
    private BigDecimal efectividad = BigDecimal.ZERO;
    
    private String notas;
}
