package Repository;

import Domain.ActivitySchedule;
import java.util.ArrayList;
import java.util.List;

public class ActivityScheduleRepo implements IRepository<ActivitySchedule> {

    private List<ActivitySchedule> scheduleList = new ArrayList<>();

    @Override
    public void create(ActivitySchedule entity) {
        if (entity != null) {
            scheduleList.add(entity);
        }
    }

    @Override
    public ActivitySchedule read(int id) {
        for (ActivitySchedule schedule : scheduleList) {
            if (schedule.getId() == id) {
                return schedule;
            }
        }
        return null;
    }

    @Override
    public void update(ActivitySchedule entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < scheduleList.size(); i++) {
            if (scheduleList.get(i).getId() == entity.getId()) {
                scheduleList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        scheduleList.removeIf(s -> s.getId() == id);
    }

    @Override
    public List<ActivitySchedule> findAll() {
        return new ArrayList<>(scheduleList);
    }
}
