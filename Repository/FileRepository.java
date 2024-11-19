package Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A file-based repository for managing entities that implement {@link Identifiable} and {@link EntityParser}.
 */
public class FileRepository implements IRepository<Object> {
    private final String filePath;
    private final EntityParser parser;

    /**
     * Constructs a new {@code FileRepository}.
     *
     * @param filePath the path to the file where entities are stored.
     * @param parser   the parser to handle entity serialization and deserialization.
     */
    public FileRepository(String filePath, EntityParser parser) {
        this.filePath = filePath;
        this.parser = parser;
    }

    /**
     * Adds a new entity to the repository.
     *
     * @param entity the entity to add.
     * @throws IllegalArgumentException if an entity with the same ID already exists.
     */
    @Override
    public synchronized void create(Object entity) {
        List<Object> entities = findAll();
        Identifiable identifiable = (Identifiable) entity;

        if (entities.stream().anyMatch(e -> ((Identifiable) e).getId() == identifiable.getId())) {
            throw new IllegalArgumentException("Entity with ID " + identifiable.getId() + " already exists.");
        }

        entities.add(entity);
        saveToFile(entities);
    }

    /**
     * Reads an entity by its ID.
     *
     * @param id the ID of the entity to retrieve.
     * @return the entity with the specified ID, or {@code null} if no such entity exists.
     */
    @Override
    public Object read(int id) {
        return findAll().stream()
                .filter(entity -> ((Identifiable) entity).getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates an existing entity in the repository.
     *
     * @param entity the entity to update.
     * @throws IllegalArgumentException if no entity with the same ID exists.
     */
    @Override
    public synchronized void update(Object entity) {
        List<Object> entities = findAll();
        Identifiable identifiable = (Identifiable) entity;
        boolean updated = false;

        for (int i = 0; i < entities.size(); i++) {
            if (((Identifiable) entities.get(i)).getId() == identifiable.getId()) {
                entities.set(i, entity);
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new IllegalArgumentException("Entity with ID " + identifiable.getId() + " not found.");
        }

        saveToFile(entities);
    }

    /**
     * Deletes an entity from the repository by its ID.
     *
     * @param id the ID of the entity to delete.
     * @throws IllegalArgumentException if no entity with the specified ID exists.
     */
    @Override
    public synchronized void delete(int id) {
        List<Object> entities = findAll();
        if (!entities.removeIf(entity -> ((Identifiable) entity).getId() == id)) {
            throw new IllegalArgumentException("Entity with ID " + id + " not found.");
        }
        saveToFile(entities);
    }

    /**
     * Retrieves all entities in the repository.
     *
     * @return a list of all entities.
     */
    @Override
    public List<Object> findAll() {
        List<Object> entities = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("File created: " + filePath);
            } catch (IOException e) {
                throw new RuntimeException("Error creating file: " + e.getMessage(), e);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entities.add(parser.parseFromCSV(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + e.getMessage(), e);
        }

        return entities;
    }

    /**
     * Saves a list of entities to the file.
     *
     * @param entities the list of entities to save.
     * @throws RuntimeException if an error occurs during file writing.
     */
    private void saveToFile(List<Object> entities) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Object entity : entities) {
                writer.write(((EntityParser) entity).toCSV());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file: " + e.getMessage(), e);
        }
    }
}
