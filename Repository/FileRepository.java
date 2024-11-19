package Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A file-based repository for managing entities that implement Identifiable and EntityParser.
 *
 * @param <T> the type of the entity managed by the repository.
 */
public class FileRepository<T extends Identifiable> implements IRepository<T> {
    private final String filePath;
    private final EntityParser<T> parser;

    /**
     * Constructs a new FileRepository.
     *
     * @param filePath the path to the file where entities are stored.
     * @param parser   the parser to handle entity serialization and deserialization.
     */
    public FileRepository(String filePath, EntityParser<T> parser) {
        this.filePath = filePath;
        this.parser = parser;
    }

    @Override
    public void create(T entity) {
        List<T> entities = findAll();

        if (entities.stream().anyMatch(e -> e.getId() == entity.getId())) {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " already exists.");
        }

        entities.add(entity);
        saveToFile(entities);
    }

    @Override
    public T read(int id) {
        return findAll().stream()
                .filter(entity -> entity.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(T entity) {
        List<T> entities = findAll();
        boolean updated = false;

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i).getId() == entity.getId()) {
                entities.set(i, entity);
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new IllegalArgumentException("Entity with ID " + entity.getId() + " not found.");
        }

        saveToFile(entities);
    }

    @Override
    public void delete(int id) {
        List<T> entities = findAll();
        if (!entities.removeIf(entity -> entity.getId() == id)) {
            throw new IllegalArgumentException("Entity with ID " + id + " not found.");
        }
        saveToFile(entities);
    }

    @Override
    public List<T> findAll() {
        List<T> entities = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return entities;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                entities.add(parser.parseFromCSV(line));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }

        return entities;
    }

    private void saveToFile(List<T> entities) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T entity : entities) {
                writer.write(parser.toCSV(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to file", e);
        }
    }
}
