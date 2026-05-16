package vallegrande.edu.pe.Agroguard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.edu.pe.Agroguard.dto.PlagaRequestDTO;
import vallegrande.edu.pe.Agroguard.dto.PlagaResponseDTO;
import vallegrande.edu.pe.Agroguard.entity.*;
import vallegrande.edu.pe.Agroguard.exception.ResourceNotFoundException;
import vallegrande.edu.pe.Agroguard.repository.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de Plagas
 * Contiene la lógica de negocio para operaciones de Plagas
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class PlagaServiceImpl implements PlagaService {
    
    private final PlagaRepository plagaRepository;
    
    public PlagaServiceImpl(PlagaRepository plagaRepository) {
        this.plagaRepository = plagaRepository;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PlagaResponseDTO> obtenerTodasLasPlagas() {
        log.info("Obteniendo todas las plagas");
        return plagaRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public PlagaResponseDTO obtenerPlagaPorId(Long id) {
        log.info("Obteniendo plaga con ID: {}", id);
        Plaga plaga = plagaRepository.findById(id)
            .orElseThrow(() -> ResourceNotFoundException.plagaNotFound(id));
        return convertToDTO(plaga);
    }
    
    @Override
    public PlagaResponseDTO crearPlaga(PlagaRequestDTO request) {
        log.info("Creando nueva plaga del catalogo: {}", request.getNamePests());

        Plaga plaga = Plaga.builder()
            .namePests(request.getNamePests())
            .category(request.getCategory())
            .description(request.getDescription())
            .symptoms(request.getSymptoms())
            .riskLevel(request.getRiskLevel())
            .imageUrl(request.getImageUrl())
            .build();
        
        Plaga plagaCreada = plagaRepository.save(plaga);
        log.info("Plaga creada exitosamente con ID: {}", plagaCreada.getId());
        return convertToDTO(plagaCreada);
    }
    
    @Override
    public PlagaResponseDTO actualizarPlaga(Long id, PlagaRequestDTO request) {
        log.info("Actualizando plaga con ID: {}", id);
        
        Plaga plaga = plagaRepository.findById(id)
            .orElseThrow(() -> ResourceNotFoundException.plagaNotFound(id));

        plaga.setNamePests(request.getNamePests());
        plaga.setCategory(request.getCategory());
        plaga.setDescription(request.getDescription());
        plaga.setSymptoms(request.getSymptoms());
        plaga.setRiskLevel(request.getRiskLevel());
        plaga.setImageUrl(request.getImageUrl());
        
        Plaga plagaActualizada = plagaRepository.save(plaga);
        log.info("Plaga actualizada exitosamente");
        return convertToDTO(plagaActualizada);
    }
    
    @Override
    public void eliminarPlaga(Long id) {
        log.info("Eliminando plaga con ID: {}", id);
        
        Plaga plaga = plagaRepository.findById(id)
            .orElseThrow(() -> ResourceNotFoundException.plagaNotFound(id));
        
        plagaRepository.delete(plaga);
        log.info("Plaga eliminada exitosamente");
    }
    
    /**
     * Convierte una entidad Plaga a DTO
     */
    private PlagaResponseDTO convertToDTO(Plaga plaga) {
        return PlagaResponseDTO.builder()
            .id(plaga.getId())
            .namePests(plaga.getNamePests())
            .category(plaga.getCategory())
            .description(plaga.getDescription())
            .symptoms(plaga.getSymptoms())
            .riskLevel(plaga.getRiskLevel())
            .imageUrl(plaga.getImageUrl())
            .createdAt(plaga.getCreatedAt())
            .updatedAt(plaga.getUpdatedAt())
            .build();
    }
}
