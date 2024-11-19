package Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory implementation of the IRepository interface.
 * This repository stores entities in a map for efficient access by ID
 * and provides basic CRUD operations.
 *
 * @param <T> the type of entities managed by this repository.
 */
public class InMemoryRepo<T extends Identifiable> implements IRepository<T> {
    private final Map<Integer, T> entities = new HashMap<>();

    /**
     * Adds a new entity to the repository.
     *
     * @param entity the entity to be created.
     */
    @Override
    public void create(T entity) {
        if (entities.containsKey(entity.getId())) {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " already exists.");
        }
        entities.put(entity.getId(), entity);
    }


    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity.
     * @return the entity with the specified ID, or {@code null} if not found.
     */
    @Override
    public T read(int id) {
        return entities.get(id);
    }

    /**
     * Updates an existing entity in the repository.
     *
     * @param entity the entity with updated values.
     */
    @Override
    public void update(T entity) {
        if (entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        } else {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " not found.");
        }
    }

    /**
     * Deletes an entity from the repository by its unique identifier.
     *
     * @param id the unique identifier of the entity to be deleted.
     */
    @Override
    public void delete(int id) {
        if (entities.containsKey(id)) {
            entities.remove(id);
        } else {
            throw new IllegalArgumentException("Entity with ID " + id + " not found.");
        }
    }

    /**
     * Retrieves all entities in the repository.
     *
     * @return a list of all entities.
     */
    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities.values());
    }
}
