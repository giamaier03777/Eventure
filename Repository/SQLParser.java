package Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Parser for entities to and from SQL database format.
 *
 * @param <T> the type of the entity being parsed.
 */
public interface SQLParser<T> {

    /**
     * Retrieves the column names for SQL operations.
     *
     * @return a comma-separated list of column names.
     */
    String getColumns();

    /**
     * Provides placeholders for SQL INSERT operations (e.g., ?, ?, ?).
     *
     * @return a comma-separated list of placeholders.
     */
    String getPlaceholders();

    /**
     * Provides the column assignments for SQL UPDATE operations (e.g., column1 = ?, column2 = ?).
     *
     * @return a comma-separated list of column assignments.
     */
    String getUpdateColumns();

    /**
     * Populates a PreparedStatement for an SQL INSERT operation.
     *
     * @param stmt   the prepared statement.
     * @param entity the entity to insert.
     * @throws SQLException if the operation fails.
     */
    void fillPreparedStatementForInsert(PreparedStatement stmt, T entity) throws SQLException;

    /**
     * Populates a PreparedStatement for an SQL UPDATE operation.
     *
     * @param stmt   the prepared statement.
     * @param entity the entity to update.
     * @throws SQLException if the operation fails.
     */
    void fillPreparedStatementForUpdate(PreparedStatement stmt, T entity) throws SQLException;

    /**
     * Parses an entity from an SQL ResultSet.
     *
     * @param rs the ResultSet containing the entity's data.
     * @return the parsed entity.
     * @throws SQLException if the operation fails.
     */
    T parseFromResultSet(ResultSet rs) throws SQLException;

    int getUpdateParametersCount();
}

