package vallegrande.edu.pe.Agroguard.service;

import vallegrande.edu.pe.Agroguard.dto.PlagaRequestDTO;
import vallegrande.edu.pe.Agroguard.dto.PlagaResponseDTO;
import vallegrande.edu.pe.Agroguard.entity.Plaga;
import java.util.List;

/**
 * Interfaz de servicio para operaciones de Plagas
 * Define el contrato de métodos para la lógica de negocio de Plagas
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
public interface PlagaService {
    
    /**
     * Obtiene todas las plagas
     * @return lista de todas las plagas
     */
    List<PlagaResponseDTO> obtenerTodasLasPlagas();
    
    /**
     * Obtiene una plaga por ID
     * @param id ID de la plaga
     * @return DTO de la plaga
     */
    PlagaResponseDTO obtenerPlagaPorId(Long id);
    
    /**
     * Crea una nueva plaga
     * @param request DTO con los datos de la plaga
     * @return DTO de la plaga creada
     */
    PlagaResponseDTO crearPlaga(PlagaRequestDTO request);
    
    /**
     * Actualiza una plaga existente
     * @param id ID de la plaga
     * @param request DTO con los datos actualizados
     * @return DTO de la plaga actualizada
     */
    PlagaResponseDTO actualizarPlaga(Long id, PlagaRequestDTO request);
    
    /**
     * Elimina una plaga
     * @param id ID de la plaga
     */
    void eliminarPlaga(Long id);
    
    /**
     * Obtiene plagas filtradas por tipo
     * @param tipo tipo de plaga (insectos, hongos, bacterias, virus)
     * @return lista de plagas del tipo especificado
     */
    List<PlagaResponseDTO> obtenerPlagasPorTipo(String tipo);
    
    /**
     * Obtiene plagas filtradas por prioridad
     * @param prioridad prioridad (baja, media, alta)
     * @return lista de plagas con la prioridad especificada
     */
    List<PlagaResponseDTO> obtenerPlagasPorPrioridad(String prioridad);
}
