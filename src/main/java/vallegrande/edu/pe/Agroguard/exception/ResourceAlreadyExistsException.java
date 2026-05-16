package vallegrande.edu.pe.Agroguard.exception;

/**
 * Excepción lanzada cuando hay un conflicto de integridad (ej: duplicado)
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
public class ResourceAlreadyExistsException extends RuntimeException {
    
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
    
    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static ResourceAlreadyExistsException plagaAlreadyExists(String nombre) {
        return new ResourceAlreadyExistsException("Ya existe una plaga con el nombre '" + nombre + "'");
    }
    
    public static ResourceAlreadyExistsException cultivoAlreadyExists(String nombre) {
        return new ResourceAlreadyExistsException("Ya existe un cultivo con el nombre '" + nombre + "'");
    }
}
