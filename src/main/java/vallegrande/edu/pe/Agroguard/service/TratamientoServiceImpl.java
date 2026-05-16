package vallegrande.edu.pe.Agroguard.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vallegrande.edu.pe.Agroguard.dto.TratamientoRequestDTO;
import vallegrande.edu.pe.Agroguard.dto.TratamientoResponseDTO;
import vallegrande.edu.pe.Agroguard.dto.EstadisticasTratamientoDTO;
import vallegrande.edu.pe.Agroguard.entity.*;
import vallegrande.edu.pe.Agroguard.exception.ResourceNotFoundException;
import vallegrande.edu.pe.Agroguard.exception.ValidationException;
import vallegrande.edu.pe.Agroguard.repository.*;
import java.math.BigDecimal;
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
    private final CultivoRepository cultivoRepository;
    private final PlagaRepository plagaRepository;
    
    public TratamientoServiceImpl(TratamientoRepository tratamientoRepository,
                                CultivoRepository cultivoRepository,
                                PlagaRepository plagaRepository) {
        this.tratamientoRepository = tratamientoRepository;
        this.cultivoRepository = cultivoRepository;
        this.plagaRepository = plagaRepository;
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
        log.info("Creando nuevo tratamiento");
        
        // Obtener cultivo
        Cultivo cultivo = cultivoRepository.findById(request.getCultivoId())
            .orElseThrow(() -> ResourceNotFoundException.cultivoNotFound(request.getCultivoId()));
        
        // Obtener plaga
        Plaga plaga = plagaRepository.findById(request.getPlagaId())
            .orElseThrow(() -> ResourceNotFoundException.plagaNotFound(request.getPlagaId()));
        
        // Validar estado
        Tratamiento.Estado estado = convertToEstado(request.getEstado());
        
        // Validar fechas
        if (request.getFechaFin() != null && request.getFechaInicio().isAfter(request.getFechaFin())) {
            throw new ValidationException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        
        // Crear entidad
        Tratamiento tratamiento = Tratamiento.builder()
            .cultivo(cultivo)
            .sector(request.getSector())
            .plaga(plaga)
            .tratamientoAplicado(request.getTratamientoAplicado())
            .estado(estado)
            .fechaInicio(request.getFechaInicio())
            .fechaFin(request.getFechaFin())
            .progresoActual(request.getProgresoActual() != null ? request.getProgresoActual() : 0)
            .progresoTotal(request.getProgresoTotal())
            .efectividad(request.getEfectividad() != null ? request.getEfectividad() : BigDecimal.ZERO)
            .notas(request.getNotas())
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
        
        // Actualizar cultivo si es diferente
        if (!tratamiento.getCultivo().getId().equals(request.getCultivoId())) {
            Cultivo cultivo = cultivoRepository.findById(request.getCultivoId())
                .orElseThrow(() -> ResourceNotFoundException.cultivoNotFound(request.getCultivoId()));
            tratamiento.setCultivo(cultivo);
        }
        
        // Actualizar plaga si es diferente
        if (!tratamiento.getPlaga().getId().equals(request.getPlagaId())) {
            Plaga plaga = plagaRepository.findById(request.getPlagaId())
                .orElseThrow(() -> ResourceNotFoundException.plagaNotFound(request.getPlagaId()));
            tratamiento.setPlaga(plaga);
        }
        
        // Validar fechas
        if (request.getFechaFin() != null && request.getFechaInicio().isAfter(request.getFechaFin())) {
            throw new ValidationException("La fecha de fin debe ser posterior a la fecha de inicio");
        }
        
        // Actualizar campos
        tratamiento.setSector(request.getSector());
        tratamiento.setTratamientoAplicado(request.getTratamientoAplicado());
        tratamiento.setEstado(convertToEstado(request.getEstado()));
        tratamiento.setFechaInicio(request.getFechaInicio());
        tratamiento.setFechaFin(request.getFechaFin());
        tratamiento.setProgresoActual(request.getProgresoActual());
        tratamiento.setProgresoTotal(request.getProgresoTotal());
        tratamiento.setEfectividad(request.getEfectividad());
        tratamiento.setNotas(request.getNotas());
        
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
    
    @Override
    @Transactional(readOnly = true)
    public List<TratamientoResponseDTO> obtenerTratamientosPorEstado(String estado) {
        log.info("Obteniendo tratamientos por estado: {}", estado);
        
        Tratamiento.Estado estadoTratamiento = convertToEstado(estado);
        return tratamientoRepository.findByEstado(estadoTratamiento)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TratamientoResponseDTO> obtenerTratamientosActivos() {
        log.info("Obteniendo tratamientos activos");
        
        return tratamientoRepository.findByEstadoOrderByFechaInicioDesc(Tratamiento.Estado.ACTIVO)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<TratamientoResponseDTO> obtenerTratamientosPorCultivo(String cultivoNombre) {
        log.info("Obteniendo tratamientos por cultivo: {}", cultivoNombre);
        
        return tratamientoRepository.findByCultivoNombre(cultivoNombre)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public EstadisticasTratamientoDTO obtenerEstadisticas() {
        log.info("Obteniendo estadísticas de tratamientos");
        
        long totalTratamientos = tratamientoRepository.count();
        long activos = tratamientoRepository.countByEstado(Tratamiento.Estado.ACTIVO);
        long completados = tratamientoRepository.countByEstado(Tratamiento.Estado.COMPLETADO);
        long pendientes = tratamientoRepository.countByEstado(Tratamiento.Estado.PENDIENTE);
        
        // Calcular efectividad promedio
        double efectividadPromedio = tratamientoRepository.findAll()
            .stream()
            .map(t -> t.getEfectividad() != null ? t.getEfectividad().doubleValue() : 0.0)
            .mapToDouble(Double::doubleValue)
            .average()
            .orElse(0.0);
        
        return EstadisticasTratamientoDTO.builder()
            .totalTratamientos(totalTratamientos)
            .activos(activos)
            .completados(completados)
            .pendientes(pendientes)
            .efectividadPromedio(efectividadPromedio)
            .build();
    }
    
    /**
     * Convierte una entidad Tratamiento a DTO
     */
    private TratamientoResponseDTO convertToDTO(Tratamiento tratamiento) {
        return TratamientoResponseDTO.builder()
            .id(tratamiento.getId())
            .cultivo(tratamiento.getCultivo().getNombre())
            .sector(tratamiento.getSector())
            .plaga(tratamiento.getPlaga().getNombre())
            .tratamientoAplicado(tratamiento.getTratamientoAplicado())
            .estado(tratamiento.getEstado().getValor())
            .fechaInicio(tratamiento.getFechaInicio())
            .fechaFin(tratamiento.getFechaFin())
            .progresoActual(tratamiento.getProgresoActual())
            .progresoTotal(tratamiento.getProgresoTotal())
            .efectividad(tratamiento.getEfectividad())
            .notas(tratamiento.getNotas())
            .createdAt(tratamiento.getCreatedAt())
            .updatedAt(tratamiento.getUpdatedAt())
            .build();
    }
    
    /**
     * Convierte string a enum Estado
     */
    private Tratamiento.Estado convertToEstado(String estado) {
        try {
            return Tratamiento.Estado.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ValidationException("Estado inválido. Debe ser: activo, completado o pendiente");
        }
    }
}
