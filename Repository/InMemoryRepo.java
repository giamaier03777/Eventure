package Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryRepo<T> implements IRepository<T> {
    private List<T> entities = new ArrayList<>();
    private int currentId = 0;

    @Override
    public void create(T entity) {
        entities.add(entity);
    }

    @Override
    public T read(int id) {
        return entities.size() > id ? entities.get(id) : null;
    }

    @Override
    public void update(T entity) {
        int index = entities.indexOf(entity);
        if (index >= 0) {
            entities.set(index, entity);
        }
    }

    @Override
    public void delete(int id) {
        if (id >= 0 && id < entities.size()) {
            entities.remove(id);
        }
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }
}
