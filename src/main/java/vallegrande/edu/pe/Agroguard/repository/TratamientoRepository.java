package vallegrande.edu.pe.Agroguard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vallegrande.edu.pe.Agroguard.entity.Tratamiento;

/**
 * Repositorio para entidad Tratamiento
 * Define métodos de acceso a datos para Tratamientos
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    
    // CRUD basico heredado de JpaRepository
}
