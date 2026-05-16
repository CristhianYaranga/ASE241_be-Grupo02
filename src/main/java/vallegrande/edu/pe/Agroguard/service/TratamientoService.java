package vallegrande.edu.pe.Agroguard.service;

import vallegrande.edu.pe.Agroguard.dto.TratamientoRequestDTO;
import vallegrande.edu.pe.Agroguard.dto.TratamientoResponseDTO;
import java.util.List;

/**
 * Interfaz de servicio para operaciones de Tratamientos
 * Define el contrato de métodos para la lógica de negocio de Tratamientos
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
public interface TratamientoService {
    
    /**
     * Obtiene todos los tratamientos
     * @return lista de todos los tratamientos
     */
    List<TratamientoResponseDTO> obtenerTodosTratamientos();
    
    /**
     * Obtiene un tratamiento por ID
     * @param id ID del tratamiento
     * @return DTO del tratamiento
     */
    TratamientoResponseDTO obtenerTratamientoPorId(Long id);
    
    /**
     * Crea un nuevo tratamiento
     * @param request DTO con los datos del tratamiento
     * @return DTO del tratamiento creado
     */
    TratamientoResponseDTO crearTratamiento(TratamientoRequestDTO request);
    
    /**
     * Actualiza un tratamiento existente
     * @param id ID del tratamiento
     * @param request DTO con los datos actualizados
     * @return DTO del tratamiento actualizado
     */
    TratamientoResponseDTO actualizarTratamiento(Long id, TratamientoRequestDTO request);
    
    /**
     * Elimina un tratamiento
     * @param id ID del tratamiento
     */
    void eliminarTratamiento(Long id);
    
}
