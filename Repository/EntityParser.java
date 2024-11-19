package Repository;

/**
 * Interface for parsing and converting entities to and from CSV format.
 */
public interface EntityParser {

    /**
     * Converts the entity into a CSV-formatted string.
     *
     * @return a string representing the entity in CSV format.
     */
    String toCSV();

    /**
     * Parses an entity from a CSV-formatted string.
     *
     * @param csv the CSV string to parse.
     * @return an object representing the parsed entity.
     */
    Object parseFromCSV(String csv);
}
