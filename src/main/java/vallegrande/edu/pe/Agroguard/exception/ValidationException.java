package vallegrande.edu.pe.Agroguard.exception;

/**
 * Excepción lanzada cuando hay un error de validación
 * 
 * @author AgroGuard Team
 * @version 1.0
 */
public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
