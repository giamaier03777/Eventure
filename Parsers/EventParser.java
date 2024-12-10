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
        return event.toCSV();
    }

    @Override
    public Event parseFromCSV(String csv) {
        String[] fields = csv.split(",");

        int id = Integer.parseInt(fields[1]);
        String name = fields[2];
        String location = fields[3];
        int capacity = Integer.parseInt(fields[4]);
        EventType eventType = EventType.valueOf(fields[5].toUpperCase());
        int currentSize = Integer.parseInt(fields[6]);
        LocalDateTime startDate = LocalDateTime.parse(fields[7]);
        LocalDateTime endDate = LocalDateTime.parse(fields[8]);
        double price = Double.parseDouble(fields[9]);

        return new Event(id, name, location, capacity, eventType, currentSize, startDate, endDate, price);
    }
}

