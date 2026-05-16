package vallegrande.edu.pe.Agroguard.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * Entidad Tratamiento
 * Representa la aplicación de un tratamiento a un cultivo para combatir una plaga
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Entity
@Table(name = "Tratamientos", indexes = {
    @Index(name = "idx_estado", columnList = "estado"),
    @Index(name = "idx_cultivo", columnList = "cultivoId"),
    @Index(name = "idx_plaga", columnList = "plagaId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tratamiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cultivoId", nullable = false)
    private Cultivo cultivo;
    
    @Column(nullable = false, length = 255)
    private String sector;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plagaId", nullable = false)
    private Plaga plaga;
    
    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String tratamientoAplicado;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;
    
    @Column(nullable = false)
    private LocalDateTime fechaInicio;
    
    @Column
    private LocalDateTime fechaFin;
    
    @Column(nullable = false)
    private Integer progresoActual = 0;
    
    @Column(nullable = false)
    private Integer progresoTotal;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal efectividad = BigDecimal.ZERO;
    
    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String notas;
    
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
    
    /**
     * Enum para estados del tratamiento
     */
    public enum Estado {
        ACTIVO("activo"),
        COMPLETADO("completado"),
        PENDIENTE("pendiente");
        
        private final String valor;
        
        Estado(String valor) {
            this.valor = valor;
        }
        
        public String getValor() {
            return valor;
        }
    }
}
