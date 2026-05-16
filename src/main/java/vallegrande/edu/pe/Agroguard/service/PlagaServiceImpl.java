package vallegrande.edu.pe.Agroguard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.edu.pe.Agroguard.dto.PlagaRequestDTO;
import vallegrande.edu.pe.Agroguard.dto.PlagaResponseDTO;
import vallegrande.edu.pe.Agroguard.entity.*;
import vallegrande.edu.pe.Agroguard.exception.ResourceAlreadyExistsException;
import vallegrande.edu.pe.Agroguard.exception.ResourceNotFoundException;
import vallegrande.edu.pe.Agroguard.exception.ValidationException;
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
    private final CultivoRepository cultivoRepository;
    private final SintomaRepository sintomaRepository;
    private final TratamientoRecomendadoRepository tratamientoRecomendadoRepository;
    
    public PlagaServiceImpl(PlagaRepository plagaRepository,
                          CultivoRepository cultivoRepository,
                          SintomaRepository sintomaRepository,
                          TratamientoRecomendadoRepository tratamientoRecomendadoRepository) {
        this.plagaRepository = plagaRepository;
        this.cultivoRepository = cultivoRepository;
        this.sintomaRepository = sintomaRepository;
        this.tratamientoRecomendadoRepository = tratamientoRecomendadoRepository;
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
        log.info("Creando nueva plaga: {}", request.getNombre());
        
        // Validar que no exista una plaga con el mismo nombre
        if (plagaRepository.existsByNombre(request.getNombre())) {
            throw ResourceAlreadyExistsException.plagaAlreadyExists(request.getNombre());
        }
        
        // Validar tipos válidos
        Plaga.TipoPlaga tipo = convertToTipoPlaga(request.getTipo());
        Plaga.Prioridad prioridad = convertToPrioridad(request.getPrioridad());
        
        // Crear entidad
        Plaga plaga = Plaga.builder()
            .nombre(request.getNombre())
            .tipo(tipo)
            .prioridad(prioridad)
            .descripcion(request.getDescripcion())
            .build();
        
        // Agregar síntomas
        if (request.getSintomas() != null && !request.getSintomas().isEmpty()) {
            Set<Sintoma> sintomas = request.getSintomas()
                .stream()
                .map(desc -> Sintoma.builder()
                    .descripcion(desc)
                    .plaga(plaga)
                    .build())
                .collect(Collectors.toSet());
            plaga.setSintomas(sintomas);
        }
        
        // Agregar tratamientos recomendados
        if (request.getTratamientosRecomendados() != null && !request.getTratamientosRecomendados().isEmpty()) {
            Set<TratamientoRecomendado> tratamientos = request.getTratamientosRecomendados()
                .stream()
                .map(desc -> TratamientoRecomendado.builder()
                    .descripcion(desc)
                    .plaga(plaga)
                    .build())
                .collect(Collectors.toSet());
            plaga.setTratamientosRecomendados(tratamientos);
        }
        
        // Agregar cultivos afectados
        if (request.getCultivosAfectados() != null && !request.getCultivosAfectados().isEmpty()) {
            Set<Cultivo> cultivos = new HashSet<>();
            for (String nombreCultivo : request.getCultivosAfectados()) {
                Cultivo cultivo = cultivoRepository.findByNombre(nombreCultivo)
                    .orElseThrow(() -> ResourceNotFoundException.cultivoNotFoundByName(nombreCultivo));
                cultivos.add(cultivo);
            }
            plaga.setCultivos(cultivos);
        }
        
        Plaga plagaCreada = plagaRepository.save(plaga);
        log.info("Plaga creada exitosamente con ID: {}", plagaCreada.getId());
        return convertToDTO(plagaCreada);
    }
    
    @Override
    public PlagaResponseDTO actualizarPlaga(Long id, PlagaRequestDTO request) {
        log.info("Actualizando plaga con ID: {}", id);
        
        Plaga plaga = plagaRepository.findById(id)
            .orElseThrow(() -> ResourceNotFoundException.plagaNotFound(id));
        
        // Verificar que el nuevo nombre no exista en otra plaga
        if (!plaga.getNombre().equals(request.getNombre()) && 
            plagaRepository.existsByNombre(request.getNombre())) {
            throw ResourceAlreadyExistsException.plagaAlreadyExists(request.getNombre());
        }
        
        // Actualizar campos básicos
        plaga.setNombre(request.getNombre());
        plaga.setTipo(convertToTipoPlaga(request.getTipo()));
        plaga.setPrioridad(convertToPrioridad(request.getPrioridad()));
        plaga.setDescripcion(request.getDescripcion());
        
        // Actualizar síntomas
        if (request.getSintomas() != null) {
            plaga.getSintomas().clear();
            Set<Sintoma> nuevosSintomas = request.getSintomas()
                .stream()
                .map(desc -> Sintoma.builder()
                    .descripcion(desc)
                    .plaga(plaga)
                    .build())
                .collect(Collectors.toSet());
            plaga.setSintomas(nuevosSintomas);
        }
        
        // Actualizar tratamientos recomendados
        if (request.getTratamientosRecomendados() != null) {
            plaga.getTratamientosRecomendados().clear();
            Set<TratamientoRecomendado> nuevosTratamientos = request.getTratamientosRecomendados()
                .stream()
                .map(desc -> TratamientoRecomendado.builder()
                    .descripcion(desc)
                    .plaga(plaga)
                    .build())
                .collect(Collectors.toSet());
            plaga.setTratamientosRecomendados(nuevosTratamientos);
        }
        
        // Actualizar cultivos
        if (request.getCultivosAfectados() != null) {
            plaga.getCultivos().clear();
            Set<Cultivo> nuevosCultivos = new HashSet<>();
            for (String nombreCultivo : request.getCultivosAfectados()) {
                Cultivo cultivo = cultivoRepository.findByNombre(nombreCultivo)
                    .orElseThrow(() -> ResourceNotFoundException.cultivoNotFoundByName(nombreCultivo));
                nuevosCultivos.add(cultivo);
            }
            plaga.setCultivos(nuevosCultivos);
        }
        
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
    
    @Override
    @Transactional(readOnly = true)
    public List<PlagaResponseDTO> obtenerPlagasPorTipo(String tipo) {
        log.info("Obteniendo plagas por tipo: {}", tipo);
        
        Plaga.TipoPlaga tipoPlaga = convertToTipoPlaga(tipo);
        return plagaRepository.findByTipo(tipoPlaga)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PlagaResponseDTO> obtenerPlagasPorPrioridad(String prioridad) {
        log.info("Obteniendo plagas por prioridad: {}", prioridad);
        
        Plaga.Prioridad prioridadPlaga = convertToPrioridad(prioridad);
        return plagaRepository.findByPrioridad(prioridadPlaga)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    /**
     * Convierte una entidad Plaga a DTO
     */
    private PlagaResponseDTO convertToDTO(Plaga plaga) {
        return PlagaResponseDTO.builder()
            .id(plaga.getId())
            .nombre(plaga.getNombre())
            .tipo(plaga.getTipo().getValor())
            .prioridad(plaga.getPrioridad().getValor())
            .descripcion(plaga.getDescripcion())
            .sintomas(plaga.getSintomas()
                .stream()
                .map(Sintoma::getDescripcion)
                .collect(Collectors.toList()))
            .tratamientosRecomendados(plaga.getTratamientosRecomendados()
                .stream()
                .map(TratamientoRecomendado::getDescripcion)
                .collect(Collectors.toList()))
            .cultivosAfectados(plaga.getCultivos()
                .stream()
                .map(Cultivo::getNombre)
                .collect(Collectors.toList()))
            .createdAt(plaga.getCreatedAt())
            .updatedAt(plaga.getUpdatedAt())
            .build();
    }
    
    /**
     * Convierte string a enum TipoPlaga
     */
    private Plaga.TipoPlaga convertToTipoPlaga(String tipo) {
        try {
            return Plaga.TipoPlaga.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Tipo de plaga inválido. Debe ser: insectos, hongos, bacterias o virus");
        }
    }
    
    /**
     * Convierte string a enum Prioridad
     */
    private Plaga.Prioridad convertToPrioridad(String prioridad) {
        try {
            return Plaga.Prioridad.valueOf(prioridad.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Prioridad inválida. Debe ser: baja, media o alta");
        }
    }
}
