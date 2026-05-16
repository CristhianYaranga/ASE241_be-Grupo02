package vallegrande.edu.pe.Agroguard.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private String nombre;
    private String tipo;
    private String prioridad;
    private String descripcion;
    private List<String> sintomas;
    private List<String> tratamientosRecomendados;
    private List<String> cultivosAfectados;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
