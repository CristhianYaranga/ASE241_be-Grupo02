package vallegrande.edu.pe.Agroguard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.edu.pe.Agroguard.dto.TratamientoRequestDTO;
import vallegrande.edu.pe.Agroguard.dto.TratamientoResponseDTO;
import vallegrande.edu.pe.Agroguard.entity.*;
import vallegrande.edu.pe.Agroguard.exception.ResourceNotFoundException;
import vallegrande.edu.pe.Agroguard.repository.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Tratamientos
 * Contiene la lógica de negocio para operaciones de Tratamientos
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class TratamientoServiceImpl implements TratamientoService {
    
    private final TratamientoRepository tratamientoRepository;
    
    public TratamientoServiceImpl(TratamientoRepository tratamientoRepository) {
        this.tratamientoRepository = tratamientoRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TratamientoResponseDTO> obtenerTodosTratamientos() {
        log.info("Obteniendo todos los tratamientos");
        return tratamientoRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public TratamientoResponseDTO obtenerTratamientoPorId(Long id) {
        log.info("Obteniendo tratamiento con ID: {}", id);
        Tratamiento tratamiento = tratamientoRepository.findById(id)
            .orElseThrow(() -> ResourceNotFoundException.tratamientoNotFound(id));
        return convertToDTO(tratamiento);
    }
    
    @Override
    public TratamientoResponseDTO crearTratamiento(TratamientoRequestDTO request) {
        log.info("Creando nuevo tratamiento del catalogo");

        Tratamiento tratamiento = Tratamiento.builder()
            .nameTreatment(request.getNameTreatment())
            .typeTreatment(request.getTypeTreatment())
            .description(request.getDescription())
            .activeIngredient(request.getActiveIngredient())
            .recommendedDos(request.getRecommendedDos())
            .applicationMode(request.getApplicationMode())
            .isOrganic(request.getIsOrganic())
            .build();
        
        Tratamiento tratamientoCreado = tratamientoRepository.save(tratamiento);
        log.info("Tratamiento creado exitosamente con ID: {}", tratamientoCreado.getId());
        return convertToDTO(tratamientoCreado);
    }
    
    @Override
    public TratamientoResponseDTO actualizarTratamiento(Long id, TratamientoRequestDTO request) {
        log.info("Actualizando tratamiento con ID: {}", id);
        
        Tratamiento tratamiento = tratamientoRepository.findById(id)
            .orElseThrow(() -> ResourceNotFoundException.tratamientoNotFound(id));
        
        tratamiento.setNameTreatment(request.getNameTreatment());
        tratamiento.setTypeTreatment(request.getTypeTreatment());
        tratamiento.setDescription(request.getDescription());
        tratamiento.setActiveIngredient(request.getActiveIngredient());
        tratamiento.setRecommendedDos(request.getRecommendedDos());
        tratamiento.setApplicationMode(request.getApplicationMode());
        tratamiento.setIsOrganic(request.getIsOrganic());
        
        Tratamiento tratamientoActualizado = tratamientoRepository.save(tratamiento);
        log.info("Tratamiento actualizado exitosamente");
        return convertToDTO(tratamientoActualizado);
    }
    
    @Override
    public void eliminarTratamiento(Long id) {
        log.info("Eliminando tratamiento con ID: {}", id);
        
        Tratamiento tratamiento = tratamientoRepository.findById(id)
            .orElseThrow(() -> ResourceNotFoundException.tratamientoNotFound(id));
        
        tratamientoRepository.delete(tratamiento);
        log.info("Tratamiento eliminado exitosamente");
    }
    
    /**
     * Convierte una entidad Tratamiento a DTO
     */
    private TratamientoResponseDTO convertToDTO(Tratamiento tratamiento) {
        return TratamientoResponseDTO.builder()
            .id(tratamiento.getId())
            .nameTreatment(tratamiento.getNameTreatment())
            .typeTreatment(tratamiento.getTypeTreatment())
            .description(tratamiento.getDescription())
            .activeIngredient(tratamiento.getActiveIngredient())
            .recommendedDos(tratamiento.getRecommendedDos())
            .applicationMode(tratamiento.getApplicationMode())
            .isOrganic(tratamiento.getIsOrganic())
            .createdAt(tratamiento.getCreatedAt())
            .updatedAt(tratamiento.getUpdatedAt())
            .build();
    }
}
