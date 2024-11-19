package Domain;

/**
 * Represents the roles that a user can have in the system.
 * <p>
 * The roles determine the level of access and permissions within the application.
 */
public enum Role {
    /**
     * A regular user with standard access rights.
     */
    USER,

    /**
     * An administrator with elevated permissions and access to manage the system.
     */
    ADMIN
}
