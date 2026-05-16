package vallegrande.edu.pe.Agroguard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vallegrande.edu.pe.Agroguard.dto.TratamientoRequestDTO;
import vallegrande.edu.pe.Agroguard.dto.TratamientoResponseDTO;
import vallegrande.edu.pe.Agroguard.dto.EstadisticasTratamientoDTO;
import vallegrande.edu.pe.Agroguard.service.TratamientoService;
import java.util.List;

/**
 * Controlador REST para operaciones de Tratamientos
 * Expone endpoints CRUD para gestión de tratamientos
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/tratamientos")
@Tag(name = "Tratamientos", description = "API REST para gestión de tratamientos de plagas")
public class TratamientoController {
    
    private final TratamientoService tratamientoService;
    
    public TratamientoController(TratamientoService tratamientoService) {
        this.tratamientoService = tratamientoService;
    }
    
    /**
     * GET /api/tratamientos
     * Obtiene todos los tratamientos
     */
    @GetMapping
    @Operation(summary = "Obtener todos los tratamientos",
               description = "Retorna una lista completa de todos los tratamientos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de tratamientos obtenida exitosamente",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = TratamientoResponseDTO.class)))
    public ResponseEntity<List<TratamientoResponseDTO>> obtenerTodosTratamientos() {
        log.info("GET /tratamientos - Obteniendo todos los tratamientos");
        List<TratamientoResponseDTO> tratamientos = tratamientoService.obtenerTodosTratamientos();
        return ResponseEntity.ok(tratamientos);
    }
    
    /**
     * GET /api/tratamientos/{id}
     * Obtiene un tratamiento específico por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener tratamiento por ID",
               description = "Retorna los detalles de un tratamiento específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tratamiento encontrado",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = TratamientoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public ResponseEntity<TratamientoResponseDTO> obtenerTratamientoPorId(
            @Parameter(description = "ID del tratamiento")
            @PathVariable Long id) {
        log.info("GET /tratamientos/{} - Obteniendo tratamiento", id);
        TratamientoResponseDTO tratamiento = tratamientoService.obtenerTratamientoPorId(id);
        return ResponseEntity.ok(tratamiento);
    }
    
    /**
     * POST /api/tratamientos
     * Crea un nuevo tratamiento
     */
    @PostMapping
    @Operation(summary = "Crear nuevo tratamiento",
               description = "Crea un nuevo tratamiento con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Tratamiento creado exitosamente",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = TratamientoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Cultivo o plaga no encontrado")
    })
    public ResponseEntity<TratamientoResponseDTO> crearTratamiento(
            @Valid @RequestBody TratamientoRequestDTO request) {
        log.info("POST /tratamientos - Creando nuevo tratamiento");
        TratamientoResponseDTO tratamientoCreado = tratamientoService.crearTratamiento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(tratamientoCreado);
    }
    
    /**
     * PUT /api/tratamientos/{id}
     * Actualiza un tratamiento existente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tratamiento",
               description = "Actualiza los datos de un tratamiento existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Tratamiento actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = TratamientoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public ResponseEntity<TratamientoResponseDTO> actualizarTratamiento(
            @Parameter(description = "ID del tratamiento")
            @PathVariable Long id,
            @Valid @RequestBody TratamientoRequestDTO request) {
        log.info("PUT /tratamientos/{} - Actualizando tratamiento", id);
        TratamientoResponseDTO tratamientoActualizado = tratamientoService.actualizarTratamiento(id, request);
        return ResponseEntity.ok(tratamientoActualizado);
    }
    
    /**
     * DELETE /api/tratamientos/{id}
     * Elimina un tratamiento
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar tratamiento",
               description = "Elimina un tratamiento del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Tratamiento eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Tratamiento no encontrado")
    })
    public ResponseEntity<Void> eliminarTratamiento(
            @Parameter(description = "ID del tratamiento")
            @PathVariable Long id) {
        log.info("DELETE /tratamientos/{} - Eliminando tratamiento", id);
        tratamientoService.eliminarTratamiento(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * GET /api/tratamientos/estado/{estado}
     * Filtra tratamientos por estado
     */
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Obtener tratamientos por estado",
               description = "Retorna tratamientos filtrados por estado (activo, completado, pendiente)")
    @ApiResponse(responseCode = "200", description = "Lista de tratamientos filtrada",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = TratamientoResponseDTO.class)))
    public ResponseEntity<List<TratamientoResponseDTO>> obtenerTratamientosPorEstado(
            @Parameter(description = "Estado: activo, completado, pendiente")
            @PathVariable String estado) {
        log.info("GET /tratamientos/estado/{} - Obteniendo tratamientos por estado", estado);
        List<TratamientoResponseDTO> tratamientos = tratamientoService.obtenerTratamientosPorEstado(estado);
        return ResponseEntity.ok(tratamientos);
    }
    
    /**
     * GET /api/tratamientos/estado/activo
     * Obtiene solo tratamientos activos
     */
    @GetMapping("/activos")
    @Operation(summary = "Obtener tratamientos activos",
               description = "Retorna solo los tratamientos en estado activo")
    @ApiResponse(responseCode = "200", description = "Lista de tratamientos activos",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = TratamientoResponseDTO.class)))
    public ResponseEntity<List<TratamientoResponseDTO>> obtenerTratamientosActivos() {
        log.info("GET /tratamientos/activos - Obteniendo tratamientos activos");
        List<TratamientoResponseDTO> tratamientos = tratamientoService.obtenerTratamientosActivos();
        return ResponseEntity.ok(tratamientos);
    }
    
    /**
     * GET /api/tratamientos/cultivo/{cultivo}
     * Filtra tratamientos por cultivo
     */
    @GetMapping("/cultivo/{cultivo}")
    @Operation(summary = "Obtener tratamientos por cultivo",
               description = "Retorna tratamientos filtrados por nombre de cultivo")
    @ApiResponse(responseCode = "200", description = "Lista de tratamientos filtrada",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = TratamientoResponseDTO.class)))
    public ResponseEntity<List<TratamientoResponseDTO>> obtenerTratamientosPorCultivo(
            @Parameter(description = "Nombre del cultivo")
            @PathVariable String cultivo) {
        log.info("GET /tratamientos/cultivo/{} - Obteniendo tratamientos por cultivo", cultivo);
        List<TratamientoResponseDTO> tratamientos = tratamientoService.obtenerTratamientosPorCultivo(cultivo);
        return ResponseEntity.ok(tratamientos);
    }
    
    /**
     * GET /api/tratamientos/estadisticas
     * Obtiene estadísticas de tratamientos
     */
    @GetMapping("/estadisticas")
    @Operation(summary = "Obtener estadísticas",
               description = "Retorna estadísticas generales de los tratamientos")
    @ApiResponse(responseCode = "200", description = "Estadísticas obtenidas",
                content = @Content(mediaType = "application/json",
                                 schema = @Schema(implementation = EstadisticasTratamientoDTO.class)))
    public ResponseEntity<EstadisticasTratamientoDTO> obtenerEstadisticas() {
        log.info("GET /tratamientos/estadisticas - Obteniendo estadísticas");
        EstadisticasTratamientoDTO estadisticas = tratamientoService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }
}
