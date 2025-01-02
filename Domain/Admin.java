package Domain;

/**
 * Singleton class representing an admin in the system.
 * Extends {@link Person} for common attributes.
 */
public class Admin extends Person {
    private static Admin instance;

    /**
     * Private constructor to prevent external instantiation.
     *
     * @param id       the unique identifier for the admin.
     * @param username the username of the admin.
     */
    private Admin(int id, String username) {
        super(id, username);
    }

    /**
     * Returns the single instance of the {@code Admin}.
     * If no instance exists, creates a new one.
     *
     * @param id       the unique identifier for the admin.
     * @param username the username of the admin.
     * @return the singleton instance of the {@code Admin}.
     */
    public static Admin getInstance(int id, String username) {
        if (instance == null) {
            synchronized (Admin.class) { // Thread-safe initialization
                if (instance == null) {
                    instance = new Admin(id, username);
                }
            }
        }
        return instance;
    }

    /**
     * Returns the single instance of the {@code Admin} if already initialized.
     *
     * @return the singleton instance of the {@code Admin}.
     * @throws IllegalStateException if the instance has not been initialized.
     */
    public static Admin getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Admin instance has not been initialized. Call getInstance(int, String) first.");
        }
        return instance;
    }

    /**
     * Resets the admin instance for testing or reinitialization purposes.
     * Use with caution.
     */
    public static void resetInstance() {
        instance = null;
    }

    @Override
    public String toString() {
        return String.format("Admin Details:\n- ID: %d\n- Username: %s\n", getId(), getUsername());
    }
}
