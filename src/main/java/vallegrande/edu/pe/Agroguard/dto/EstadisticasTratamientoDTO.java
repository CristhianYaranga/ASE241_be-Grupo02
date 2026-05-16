package vallegrande.edu.pe.Agroguard.dto;

import lombok.*;

/**
 * DTO para respuestas de Estadísticas de Tratamientos
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadisticasTratamientoDTO {
    
    private Long totalTratamientos;
    private Long activos;
    private Long completados;
    private Long pendientes;
    private Double efectividadPromedio;
    private Long plagasMasComunes;
    private Long cultivosMasAfectados;
}
