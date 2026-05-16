package vallegrande.edu.pe.Agroguard.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Entidad Plaga
 * Representa una plaga o enfermedad que afecta cultivos agrícolas
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Entity
@Table(name = "Plagas", indexes = {
    @Index(name = "idx_tipo", columnList = "tipo"),
    @Index(name = "idx_prioridad", columnList = "prioridad"),
    @Index(name = "idx_nombre", columnList = "nombre")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plaga {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 255)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoPlaga tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Prioridad prioridad;
    
    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String descripcion;
    
    @OneToMany(mappedBy = "plaga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Sintoma> sintomas = new HashSet<>();
    
    @OneToMany(mappedBy = "plaga", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<TratamientoRecomendado> tratamientosRecomendados = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "Plagas_Cultivos",
        joinColumns = @JoinColumn(name = "plagaId"),
        inverseJoinColumns = @JoinColumn(name = "cultivoId")
    )
    private Set<Cultivo> cultivos = new HashSet<>();
    
    @OneToMany(mappedBy = "plaga", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Tratamiento> tratamientos = new HashSet<>();
    
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
     * Enum para tipos de plagas
     */
    public enum TipoPlaga {
        INSECTOS("insectos"),
        HONGOS("hongos"),
        BACTERIAS("bacterias"),
        VIRUS("virus");
        
        private final String valor;
        
        TipoPlaga(String valor) {
            this.valor = valor;
        }
        
        public String getValor() {
            return valor;
        }
    }
    
    /**
     * Enum para prioridades
     */
    public enum Prioridad {
        BAJA("baja"),
        MEDIA("media"),
        ALTA("alta");
        
        private final String valor;
        
        Prioridad(String valor) {
            this.valor = valor;
        }
        
        public String getValor() {
            return valor;
        }
    }
}
