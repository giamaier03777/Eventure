package Parsers;

import Domain.ActivitySchedule;
import Domain.Reservation;
import Domain.User;
import Repository.EntityParser;

import java.time.LocalDateTime;

/**
 * A parser for the {@link Reservation} class, used for converting between {@code Reservation} objects and their CSV representation.
 */
public class ReservationParser implements EntityParser<Reservation> {

    private final UserParser userParser;
    private final ActivityScheduleParser activityScheduleParser;

    /**
     * Constructs a {@link ReservationParser} with its dependencies.
     *
     * @param userParser           the parser for {@link User} objects.
     * @param activityScheduleParser the parser for {@link ActivitySchedule} objects.
     */
    public ReservationParser(UserParser userParser, ActivityScheduleParser activityScheduleParser) {
        this.userParser = userParser;
        this.activityScheduleParser = activityScheduleParser;
    }

    @Override
    public String toCSV(Reservation reservation) {
        return reservation.getId() + "|" +
                userParser.toCSV(reservation.getUser()) + "|" +
                activityScheduleParser.toCSV(reservation.getActivitySchedule()) + "|" +
                reservation.getNumberOfPeople() + "|" +
                reservation.getReservationDate();
    }

    @Override
    public Reservation parseFromCSV(String csv) {
        String[] fields = csv.split("\\|");

        int id = Integer.parseInt(fields[0]);
        User user = userParser.parseFromCSV(fields[1]);
        ActivitySchedule activitySchedule = activityScheduleParser.parseFromCSV(fields[2]);
        int numberOfPeople = Integer.parseInt(fields[3]);
        LocalDateTime reservationDate = LocalDateTime.parse(fields[4]);
        return new Reservation(id, user, activitySchedule, numberOfPeople, reservationDate);
    }
}
