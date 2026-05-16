package vallegrande.edu.pe.Agroguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.Agroguard.entity.Tratamiento;
import java.util.List;

/**
 * Repositorio para entidad Tratamiento
 * Define métodos de acceso a datos para Tratamientos
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    
    /**
     * Busca tratamientos por estado
     * @param estado estado del tratamiento (ACTIVO, COMPLETADO, PENDIENTE)
     * @return lista de tratamientos con el estado especificado
     */
    List<Tratamiento> findByEstado(Tratamiento.Estado estado);
    
    /**
     * Busca tratamientos activos
     * @return lista de tratamientos activos
     */
    List<Tratamiento> findByEstadoOrderByFechaInicioDesc(Tratamiento.Estado estado);
    
    /**
     * Busca tratamientos por cultivo ID
     * @param cultivoId ID del cultivo
     * @return lista de tratamientos para el cultivo
     */
    List<Tratamiento> findByCultivoId(Long cultivoId);
    
    /**
     * Busca tratamientos por cultivo nombre
     * @param cultivoNombre nombre del cultivo
     * @return lista de tratamientos para el cultivo
     */
    List<Tratamiento> findByCultivoNombre(String cultivoNombre);
    
    /**
     * Cuenta tratamientos por estado
     * @param estado estado del tratamiento
     * @return cantidad de tratamientos en ese estado
     */
    long countByEstado(Tratamiento.Estado estado);
    
    /**
     * Busca tratamientos por plaga ID
     * @param plagaId ID de la plaga
     * @return lista de tratamientos para la plaga
     */
    List<Tratamiento> findByPlagaId(Long plagaId);
}
