package Parsers;

import Domain.ActivitySchedule;
import Domain.Booking;
import Repository.EntityParser;

/**
 * A parser for the {@link Booking} class, used for converting between {@code Booking} objects and their CSV representation.
 */
public class BookingParser implements EntityParser<Booking> {

    private final ActivityScheduleParser activityScheduleParser;

    /**
     * Constructs a {@link BookingParser} with its dependencies.
     *
     * @param activityScheduleParser the parser for {@link ActivitySchedule} objects.
     */
    public BookingParser(ActivityScheduleParser activityScheduleParser) {
        this.activityScheduleParser = activityScheduleParser;
    }

    @Override
    public String toCSV(Booking booking) {
        return booking.getId() + "," +
                activityScheduleParser.toCSV(booking.getSchedule()) + "," +
                booking.getCustomerName() + "," +
                booking.getNumberOfPeople();
    }

    @Override
    public Booking parseFromCSV(String csv) {
        String[] fields = csv.split(",", 4);

        int id = Integer.parseInt(fields[0]);

        ActivitySchedule schedule = activityScheduleParser.parseFromCSV(fields[1]);

        String customerName = fields[2];

        int numberOfPeople = Integer.parseInt(fields[3]);

        Booking booking = new Booking(schedule, customerName, numberOfPeople);
        booking.setId(id);
        return booking;
    }
}
