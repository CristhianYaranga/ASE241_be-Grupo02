package vallegrande.edu.pe.Agroguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.Agroguard.entity.Cultivo;
import java.util.Optional;

/**
 * Repositorio para entidad Cultivo
 * Define métodos de acceso a datos para Cultivos
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Repository
public interface CultivoRepository extends JpaRepository<Cultivo, Long> {
    
    /**
     * Busca un cultivo por nombre
     * @param nombre nombre del cultivo
     * @return Optional con el cultivo si existe
     */
    Optional<Cultivo> findByNombre(String nombre);
    
    /**
     * Verifica si existe un cultivo con el nombre especificado
     * @param nombre nombre del cultivo
     * @return true si existe, false en caso contrario
     */
    boolean existsByNombre(String nombre);
}
