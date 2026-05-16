package vallegrande.edu.pe.Agroguard.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad TratamientoRecomendado
 * Representa un tratamiento recomendado para una plaga específica
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Entity
@Table(name = "TratamientosRecomendados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TratamientoRecomendado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plagaId", nullable = false)
    private Plaga plaga;
    
    @Column(nullable = false, length = 255)
    private String descripcion;
}
