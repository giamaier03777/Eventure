package Domain;

/**
 * Abstract class representing a generic person.
 */
public abstract class Person {
    private int id;
    private String username;

    /**
     * Constructs a new {@code Person}.
     *
     * @param id       the unique identifier for the person.
     * @param username the username of the person.
     */
    public Person(int id, String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        this.id = id;
        this.username = username;
    }

    /**
     * Gets the ID of the person.
     *
     * @return the ID of the person.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the person.
     *
     * @param id the ID of the person.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username of the person.
     *
     * @return the username of the person.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the person.
     *
     * @param username the username of the person.
     */
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        this.username = username;
    }
}

