package Repository;

import Domain.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActivityRepo implements IRepository<Activity> {

    private List<Activity> activityList = new ArrayList<>();

    @Override
    public void create(Activity entity) {
        if (entity != null) {
            activityList.add(entity);
        }
    }

    @Override
    public Activity read(int id) {
        Optional<Activity> activity = activityList.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
        return activity.orElse(null);
    }

    @Override
    public void update(Activity entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i).getId() == entity.getId()) {
                activityList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        activityList.removeIf(a -> a.getId() == id);
    }

    @Override
    public List<Activity> findAll() {
        return new ArrayList<>(activityList);
    }
}
