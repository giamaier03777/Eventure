package Repository;

import java.util.List;

/**
 * Generic interface for a repository that manages CRUD operations for a specific type of entity.
 *
 * @param <T> the type of the entity managed by the repository.
 */
public interface IRepository<T> {

    /**
     * Adds a new entity to the repository.
     *
     * @param entity the entity to be created.
     */
    void create(T entity);

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity.
     * @return the entity with the specified ID, or {@code null} if not found.
     */
    T read(int id);

    /**
     * Updates an existing entity in the repository.
     *
     * @param entity the entity with updated values.
     */
    void update(T entity);

    /**
     * Deletes an entity from the repository by its unique identifier.
     *
     * @param id the unique identifier of the entity to be deleted.
     */
    void delete(int id);

    /**
     * Retrieves all entities in the repository.
     *
     * @return a list of all entities.
     */
    List<T> findAll();
}
