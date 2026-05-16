package vallegrande.edu.pe.Agroguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.Agroguard.entity.Plaga;

/**
 * Repositorio para entidad Plaga
 * Define métodos de acceso a datos para Plagas
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Repository
public interface PlagaRepository extends JpaRepository<Plaga, Long> {
    
    // CRUD basico heredado de JpaRepository
}
