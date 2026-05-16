package vallegrande.edu.pe.Agroguard.exception;

/**
 * Excepción lanzada cuando un recurso no es encontrado
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static ResourceNotFoundException plagaNotFound(Long id) {
        return new ResourceNotFoundException("Plaga con ID " + id + " no encontrada");
    }
    
    public static ResourceNotFoundException plagaNotFoundByName(String nombre) {
        return new ResourceNotFoundException("Plaga con nombre '" + nombre + "' no encontrada");
    }
    
    public static ResourceNotFoundException tratamientoNotFound(Long id) {
        return new ResourceNotFoundException("Tratamiento con ID " + id + " no encontrado");
    }
    
    public static ResourceNotFoundException cultivoNotFound(Long id) {
        return new ResourceNotFoundException("Cultivo con ID " + id + " no encontrado");
    }
    
    public static ResourceNotFoundException cultivoNotFoundByName(String nombre) {
        return new ResourceNotFoundException("Cultivo con nombre '" + nombre + "' no encontrado");
    }
}
