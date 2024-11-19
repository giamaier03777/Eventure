package Repository;

/**
 * An interface for parsing entities to and from CSV format.
 *
 * @param <T> the type of the entity being parsed.
 */
public interface EntityParser<T> {

    /**
     * Converts an entity to its CSV string representation.
     *
     * @param entity the entity to convert.
     * @return the CSV string representation of the entity.
     */
    String toCSV(T entity);

    /**
     * Parses an entity from its CSV string representation.
     *
     * @param csv the CSV string representation of the entity.
     * @return the parsed entity.
     */
    T parseFromCSV(String csv);
}
