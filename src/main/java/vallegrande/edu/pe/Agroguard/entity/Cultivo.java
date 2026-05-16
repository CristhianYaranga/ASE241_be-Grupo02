package vallegrande.edu.pe.Agroguard.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Entidad Cultivo
 * Representa un tipo de cultivo agrícola
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Entity
@Table(name = "Cultivos", indexes = {
    @Index(name = "idx_cultivo_nombre", columnList = "nombre")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cultivo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 255)
    private String nombre;
    
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String descripcion;
    
    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
