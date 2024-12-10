package Parsers;

import Domain.*;
import Repository.EntityParser;

/**
 * A parser for {@link FreeActivity} entities.
 */
public class FreeActivityParser implements EntityParser<FreeActivity> {

    @Override
    public String toCSV(FreeActivity activity) {
        return activity.toCSV();
    }

    @Override
    public FreeActivity parseFromCSV(String csv) {
        String[] fields = csv.split(",");

        int id = Integer.parseInt(fields[1]);
        String name = fields[2];
        String location = fields[3];
        EventType eventType = EventType.valueOf(fields[4].toUpperCase());
        String program = fields[5];

        return new FreeActivity(id, name, location, eventType, program);
    }
}

