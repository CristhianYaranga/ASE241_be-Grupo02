package vallegrande.edu.pe.Agroguard.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

/**
 * Entidad Plaga
 * Representa una plaga del catalogo
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Entity
@Table(name = "PESTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plaga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pests")
    private Long id;
    
    @Column(name = "name_pests", nullable = false, length = 100)
    private String namePests;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "symptoms", length = 500)
    private String symptoms;

    @Column(name = "risk_level", nullable = false, length = 10)
    private String riskLevel;

    @Column(name = "image_url", length = 300)
    private String imageUrl;

    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "update_date")
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
