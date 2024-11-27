package Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A database-backed repository for managing entities that implement Identifiable and EntityParser.
 *
 * @param <T> the type of the entity managed by the repository.
 */
public class DBRepository<T extends Identifiable> implements IRepository<T> {
    private final String tableName;
    private final SQLParser<T> parser;
    private final Connection connection;

    /**
     * Constructs a new DBRepository.
     *
     * @param connection the JDBC connection to the database.
     * @param tableName  the name of the table storing entities.
     * @param parser     the parser to handle entity serialization and deserialization.
     */
    public DBRepository(Connection connection, String tableName, SQLParser<T> parser) {
        this.connection = connection;
        this.tableName = tableName;
        this.parser = (SQLParser<T>) parser;
    }

    @Override
    public void create(T entity) {
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName,
                parser.getColumns(),
                parser.getPlaceholders());
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            parser.fillPreparedStatementForInsert(stmt, entity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create entity", e);
        }
    }

    @Override
    public T read(int id) {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", tableName);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return parser.parseFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to read entity", e);
        }
        return null;
    }

    @Override
    public void update(T entity) {
        String sql = String.format("UPDATE %s SET %s WHERE id = ?",
                tableName,
                parser.getUpdateColumns());
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            parser.fillPreparedStatementForUpdate(stmt, entity);
            stmt.setInt(parser.getUpdateParametersCount() + 1, entity.getId());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Entity with ID " + entity.getId() + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update entity", e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = String.format("DELETE FROM %s WHERE id = ?", tableName);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new IllegalArgumentException("Entity with ID " + id + " not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete entity", e);
        }
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s", tableName);
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                entities.add(parser.parseFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find all entities", e);
        }
        return entities;
    }
}

