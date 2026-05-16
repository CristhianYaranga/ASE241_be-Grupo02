package vallegrande.edu.pe.Agroguard.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad Sintoma
 * Representa un síntoma de una plaga específica
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Entity
@Table(name = "Sintomas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sintoma {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plagaId", nullable = false)
    private Plaga plaga;
    
    @Column(nullable = false, length = 255)
    private String descripcion;
}
