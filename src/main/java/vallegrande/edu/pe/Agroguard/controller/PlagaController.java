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
import vallegrande.edu.pe.Agroguard.dto.PlagaRequestDTO;
import vallegrande.edu.pe.Agroguard.dto.PlagaResponseDTO;
import vallegrande.edu.pe.Agroguard.service.PlagaService;
import java.util.List;

/**
 * Controlador REST para operaciones de Plagas
 * Expone endpoints CRUD para gestión de plagas
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/plagas")
@Tag(name = "Plagas", description = "API REST para gestión de plagas y enfermedades")
public class PlagaController {
    
    private final PlagaService plagaService;
    
    public PlagaController(PlagaService plagaService) {
        this.plagaService = plagaService;
    }
    
    /**
     * GET /api/plagas
     * Obtiene todas las plagas
     */
    @GetMapping
    @Operation(summary = "Obtener todas las plagas", 
               description = "Retorna una lista completa del catalogo de plagas")
    @ApiResponse(responseCode = "200", description = "Lista de plagas obtenida exitosamente",
                 content = @Content(mediaType = "application/json",
                                  schema = @Schema(implementation = PlagaResponseDTO.class)))
    public ResponseEntity<List<PlagaResponseDTO>> obtenerTodasLasPlagas() {
        log.info("GET /plagas - Obteniendo todas las plagas");
        List<PlagaResponseDTO> plagas = plagaService.obtenerTodasLasPlagas();
        return ResponseEntity.ok(plagas);
    }
    
    /**
     * GET /api/plagas/{id}
     * Obtiene una plaga específica por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener plaga por ID",
               description = "Retorna los detalles de una plaga del catalogo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plaga encontrada",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = PlagaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Plaga no encontrada")
    })
    public ResponseEntity<PlagaResponseDTO> obtenerPlagaPorId(
            @Parameter(description = "ID de la plaga")
            @PathVariable Long id) {
        log.info("GET /plagas/{} - Obteniendo plaga", id);
        PlagaResponseDTO plaga = plagaService.obtenerPlagaPorId(id);
        return ResponseEntity.ok(plaga);
    }
    
    /**
     * POST /api/plagas
     * Crea una nueva plaga
     */
    @PostMapping
    @Operation(summary = "Crear nueva plaga",
               description = "Crea una nueva plaga en el catalogo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Plaga creada exitosamente",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = PlagaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "409", description = "La plaga ya existe")
    })
    public ResponseEntity<PlagaResponseDTO> crearPlaga(
            @Valid @RequestBody PlagaRequestDTO request) {
        log.info("POST /plagas - Creando nueva plaga: {}", request.getNamePests());
        PlagaResponseDTO plagaCreada = plagaService.crearPlaga(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(plagaCreada);
    }
    
    /**
     * PUT /api/plagas/{id}
     * Actualiza una plaga existente
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar plaga",
               description = "Actualiza los datos de una plaga del catalogo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Plaga actualizada exitosamente",
                    content = @Content(mediaType = "application/json",
                                     schema = @Schema(implementation = PlagaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "404", description = "Plaga no encontrada")
    })
    public ResponseEntity<PlagaResponseDTO> actualizarPlaga(
            @Parameter(description = "ID de la plaga")
            @PathVariable Long id,
            @Valid @RequestBody PlagaRequestDTO request) {
        log.info("PUT /plagas/{} - Actualizando plaga", id);
        PlagaResponseDTO plagaActualizada = plagaService.actualizarPlaga(id, request);
        return ResponseEntity.ok(plagaActualizada);
    }
    
    /**
     * DELETE /api/plagas/{id}
     * Elimina una plaga
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar plaga",
               description = "Elimina una plaga del catalogo")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Plaga eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Plaga no encontrada")
    })
    public ResponseEntity<Void> eliminarPlaga(
            @Parameter(description = "ID de la plaga")
            @PathVariable Long id) {
        log.info("DELETE /plagas/{} - Eliminando plaga", id);
        plagaService.eliminarPlaga(id);
        return ResponseEntity.noContent().build();
    }
    
}
