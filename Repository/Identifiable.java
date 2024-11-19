package Repository;

/**
 * Interface representing an identifiable entity with an ID.
 * Classes implementing this interface must provide methods to get and set their unique identifier.
 */
public interface Identifiable {

    /**
     * Retrieves the unique identifier of the entity.
     *
     * @return the ID of the entity.
     */
    int getId();

    /**
     * Sets the unique identifier of the entity.
     *
     * @param id the ID to set for the entity.
     */
    void setId(int id);
}
