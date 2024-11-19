package Parsers;

import Domain.*;
import Repository.EntityParser;

/**
 * A parser for Activity entities.
 */
public class ActivityParser implements EntityParser<Activity> {

    @Override
    public String toCSV(Activity activity) {
        return activity.getId() + "," +
                activity.getName() + "," +
                activity.getCapacity() + "," +
                activity.getLocation() + "," +
                activity.getCategory() + "," +
                activity.getDescription() + "," +
                activity.getPrice();
    }

    @Override
    public Activity parseFromCSV(String csv) {
        String[] fields = csv.split(",");
        int id = Integer.parseInt(fields[0]);
        String name = fields[1];
        int capacity = Integer.parseInt(fields[2]);
        String location = fields[3];
        EventType category = EventType.valueOf(fields[4]);
        String description = fields[5];
        double price = Double.parseDouble(fields[6]);

        return new Activity(id, name, capacity, location, category, description, price);
    }
}
