package vallegrande.edu.pe.Agroguard.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

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
    private String cultivo;
    private String sector;
    private String plaga;
    private String tratamientoAplicado;
    private String estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer progresoActual;
    private Integer progresoTotal;
    private BigDecimal efectividad;
    private String notas;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
