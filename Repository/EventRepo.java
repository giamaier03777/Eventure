package Repository;

import Domain.Event;

import java.util.ArrayList;
import java.util.List;

public class EventRepo implements IRepository<Event> {

    private List<Event> eventList = new ArrayList<>();

    @Override
    public void create(Event entity) {
        if (entity != null) {
            eventList.add(entity);
        }
    }

    @Override
    public Event read(int id) {
        for (Event event : eventList) {
            if (event.getId() == id) {
                return event;
            }
        }
        return null;
    }

    @Override
    public void update(Event entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < eventList.size(); i++) {
            if (eventList.get(i).getId() == entity.getId()) {
                eventList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        eventList.removeIf(e -> e.getId() == id);
    }

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(eventList);
    }
}
