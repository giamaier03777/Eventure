package Parsers;

import Repository.EntityParser;
import Domain.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A parser for {@link ActivitySchedule} entities.
 */
public class ActivityScheduleParser implements EntityParser<ActivitySchedule> {

    private final ActivityParser activityParser;

    /**
     * Constructs a new {@code ActivityScheduleParser} with a dependency on {@code ActivityParser}.
     *
     * @param activityParser the parser for {@link Activity} entities.
     */
    public ActivityScheduleParser(ActivityParser activityParser) {
        this.activityParser = activityParser;
    }

    @Override
    public String toCSV(ActivitySchedule schedule) {
        return schedule.getId() + ";" +
                activityParser.toCSV(schedule.getActivity()) + ";" +
                schedule.getDate() + ";" +
                schedule.getStartTime() + ";" +
                schedule.getEndTime() + ";" +
                schedule.getAvailableCapacity();
    }

    @Override
    public ActivitySchedule parseFromCSV(String csv) {
        String[] fields = csv.split(";");
        System.out.println(fields);
        int id = Integer.parseInt(fields[0]);

        Activity activity = activityParser.parseFromCSV(fields[1]);

        LocalDate date = LocalDate.parse(fields[2]);
        LocalTime startTime = LocalTime.parse(fields[3]);
        LocalTime endTime = LocalTime.parse(fields[4]);
        int availableCapacity = Integer.parseInt(fields[5]);

        ActivitySchedule schedule = new ActivitySchedule(activity, date, startTime, endTime, availableCapacity);
        schedule.setId(id);

        return schedule;
    }
}
