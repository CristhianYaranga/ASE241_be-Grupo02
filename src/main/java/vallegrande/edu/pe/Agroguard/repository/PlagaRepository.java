package vallegrande.edu.pe.Agroguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.Agroguard.entity.Plaga;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para entidad Plaga
 * Define métodos de acceso a datos para Plagas
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Repository
public interface PlagaRepository extends JpaRepository<Plaga, Long> {
    
    /**
     * Busca plagas por tipo
     * @param tipo tipo de plaga (INSECTOS, HONGOS, BACTERIAS, VIRUS)
     * @return lista de plagas del tipo especificado
     */
    List<Plaga> findByTipo(Plaga.TipoPlaga tipo);
    
    /**
     * Busca plagas por prioridad
     * @param prioridad prioridad (BAJA, MEDIA, ALTA)
     * @return lista de plagas con la prioridad especificada
     */
    List<Plaga> findByPrioridad(Plaga.Prioridad prioridad);
    
    /**
     * Busca una plaga por nombre
     * @param nombre nombre de la plaga
     * @return Optional con la plaga si existe
     */
    Optional<Plaga> findByNombre(String nombre);
    
    /**
     * Verifica si existe una plaga con el nombre especificado
     * @param nombre nombre de la plaga
     * @return true si existe, false en caso contrario
     */
    boolean existsByNombre(String nombre);
}
