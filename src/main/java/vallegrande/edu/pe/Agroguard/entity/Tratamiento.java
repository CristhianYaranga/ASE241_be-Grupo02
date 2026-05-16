package vallegrande.edu.pe.Agroguard.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Entidad Tratamiento
 * Representa un tratamiento del catalogo
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Entity
@Table(name = "TREATMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tratamiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treatment")
    private Long id;
    
    @Column(name = "name_treatment", nullable = false, length = 100)
    private String nameTreatment;

    @Column(name = "type_treatment", nullable = false, length = 50)
    private String typeTreatment;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "active_ingredient", length = 150)
    private String activeIngredient;

    @Column(name = "recommended_dos", length = 100)
    private String recommendedDos;

    @Column(name = "application_mode", length = 200)
    private String applicationMode;

    @Column(name = "is_organic", nullable = false)
    private Boolean isOrganic = Boolean.FALSE;

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
