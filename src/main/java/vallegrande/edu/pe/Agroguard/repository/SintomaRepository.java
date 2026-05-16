package vallegrande.edu.pe.Agroguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.Agroguard.entity.Sintoma;
import java.util.List;

/**
 * Repositorio para entidad Sintoma
 * Define métodos de acceso a datos para Síntomas
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Repository
public interface SintomaRepository extends JpaRepository<Sintoma, Long> {
    
    /**
     * Busca síntomas por plaga ID
     * @param plagaId ID de la plaga
     * @return lista de síntomas de la plaga
     */
    List<Sintoma> findByPlagaId(Long plagaId);
}
