package Exception;

/**
 * Exception thrown when an entity with the same ID already exists in the repository.
 */
public class EntityAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new EntityAlreadyExistsException with the specified detail message.
     *
     * @param message the detail message.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new EntityAlreadyExistsException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception (can be null).
     */
    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
