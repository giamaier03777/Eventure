package Parsers;

import Domain.*;
import Repository.EntityParser;
import java.time.LocalDateTime;

/**
 * A parser for {@link Event} entities.
 */
public class EventParser implements EntityParser<Event> {

    @Override
    public String toCSV(Event event) {
        return event.getId() + "," +
                event.getName() + "," +
                event.getLocation() + "," +
                event.getCapacity() + "," +
                event.getEventType() + "," +
                event.getCurrentSize() + "," +
                event.getStartDate() + "," +
                event.getEndDate() + "," +
                event.getPrice();
    }

    @Override
    public Event parseFromCSV(String csv) {
        String[] fields = csv.split(",", -1);

        int id = Integer.parseInt(fields[0]);
        String name = fields[1];
        String location = fields[2];
        int capacity = Integer.parseInt(fields[3]);
        EventType eventType = EventType.valueOf(fields[4].toUpperCase());
        int currentSize = Integer.parseInt(fields[5]);
        LocalDateTime startDate = LocalDateTime.parse(fields[6]);
        LocalDateTime endDate = LocalDateTime.parse(fields[7]);
        double price = Double.parseDouble(fields[8]);

        return new Event(id, name, location, capacity, eventType, currentSize, startDate, endDate, price);
    }
}

