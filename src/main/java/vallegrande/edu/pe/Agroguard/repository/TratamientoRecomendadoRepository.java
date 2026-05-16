package vallegrande.edu.pe.Agroguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.Agroguard.entity.TratamientoRecomendado;
import java.util.List;

/**
 * Repositorio para entidad TratamientoRecomendado
 * Define métodos de acceso a datos para Tratamientos Recomendados
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Repository
public interface TratamientoRecomendadoRepository extends JpaRepository<TratamientoRecomendado, Long> {
    
    /**
     * Busca tratamientos recomendados por plaga ID
     * @param plagaId ID de la plaga
     * @return lista de tratamientos recomendados para la plaga
     */
    List<TratamientoRecomendado> findByPlagaId(Long plagaId);
}
