package Parsers;

import Domain.*;
import Repository.EntityParser;

public class ActivityParser implements EntityParser<Activity> {

    @Override
    public String toCSV(Activity activity) {
        return activity.toCSV();
    }

    @Override
    public Activity parseFromCSV(String csv) {
        String[] fields = csv.split(",");

        int id = Integer.parseInt(fields[1]);
        String name = fields[2];
        int capacity = Integer.parseInt(fields[3]);
        String location = unescape(fields[4]);
        EventType category = EventType.valueOf(fields[5].toUpperCase());
        String description = unescape(fields[6]);
        double price = Double.parseDouble(fields[7]);

        return new Activity(id, name, capacity, location, category, description, price);
    }

    private String escape(String value) {
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }

    private String unescape(String value) {
        return value.replace("\"\"", "\"").replaceAll("^\"|\"$", "");
    }

    private void validateFieldLength(String[] fields, int expectedLength) {
        if (fields.length < expectedLength) {
            throw new IllegalArgumentException("Invalid CSV format, missing fields.");
        }
    }
}
