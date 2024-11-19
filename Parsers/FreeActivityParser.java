package Parsers;

import Domain.*;
import Repository.EntityParser;

/**
 * A parser for {@link FreeActivity} entities.
 */
public class FreeActivityParser implements EntityParser<FreeActivity> {

    @Override
    public String toCSV(FreeActivity activity) {
        return activity.getId() + "," +
                activity.getName() + "," +
                activity.getLocation() + "," +
                activity.getEventType() + "," +
                activity.getProgram();
    }

    @Override
    public FreeActivity parseFromCSV(String csv) {
        String[] fields = csv.split(",", -1);

        int id = Integer.parseInt(fields[0]);
        String name = fields[1];
        String location = fields[2];
        EventType eventType = EventType.valueOf(fields[3].toUpperCase());
        String program = fields[4];

        return new FreeActivity(id, name, location, eventType, program);
    }
}
