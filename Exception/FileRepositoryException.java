package Exception;

/**
 * A custom exception that represents errors specific to file-based repositories.
 */
public class FileRepositoryException extends RuntimeException {

    /**
     * Constructs a new FileRepositoryException with the specified detail message.
     *
     * @param message the detail message.
     */
    public FileRepositoryException(String message) {
        super(message);
    }

    /**
     * Constructs a new FileRepositoryException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception (can be null).
     */
    public FileRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
