package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

import java.time.LocalDateTime;

/**
 * Represents a reservation made by a user for an activity.
 * Implements {@link Identifiable} for providing unique IDs and {@link EntityParser} for parsing and serialization.
 */
public class Reservation implements Identifiable, EntityParser {
    private int id;
    private User user;
    private ActivitySchedule activitySchedule;
    private int numberOfPeople;
    private LocalDateTime reservationDate;

    /**
     * Constructs a Reservation instance with the specified details.
     *
     * @param id               the unique identifier of the reservation
     * @param user             the user making the reservation
     * @param activitySchedule the activity schedule being reserved
     * @param numberOfPeople   the number of people included in the reservation
     * @param reservationDate  the date and time of the reservation
     */
    public Reservation(int id, User user, ActivitySchedule activitySchedule, int numberOfPeople, LocalDateTime reservationDate) {
        this.id = id;
        this.user = user;
        this.activitySchedule = activitySchedule;
        this.numberOfPeople = numberOfPeople;
        this.reservationDate = reservationDate;
    }

    /**
     * Default constructor for creating an empty {@link Reservation} object.
     */
    public Reservation() {

    }

    /**
     * Gets the unique identifier of the reservation.
     *
     * @return the reservation ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the reservation.
     *
     * @param id the new reservation ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user associated with the reservation.
     *
     * @return the user who made the reservation
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with the reservation.
     *
     * @param user the new user who made the reservation
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the activity schedule being reserved.
     *
     * @return the associated activity schedule
     */
    public ActivitySchedule getActivitySchedule() {
        return activitySchedule;
    }

    /**
     * Sets the activity schedule being reserved.
     *
     * @param activitySchedule the new activity schedule
     */
    public void setActivitySchedule(ActivitySchedule activitySchedule) {
        this.activitySchedule = activitySchedule;
    }

    /**
     * Gets the number of people included in the reservation.
     *
     * @return the number of people
     */
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * Sets the number of people included in the reservation.
     *
     * @param numberOfPeople the new number of people
     */
    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    /**
     * Gets the date and time of the reservation.
     *
     * @return the reservation date and time
     */
    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    /**
     * Sets the date and time of the reservation.
     *
     * @param reservationDate the new reservation date and time
     */
    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    /**
     * Converts the {@link Reservation} object to a CSV string representation.
     *
     * @return a CSV string representing the reservation
     */
    @Override
    public String toCSV() {
        return id + "," + user.toCSV() + "," + activitySchedule.toCSV() + "," + numberOfPeople + "," + reservationDate;
    }

    /**
     * Parses a {@link Reservation} object from a CSV string.
     *
     * @param csv the CSV string representing a reservation
     * @return the parsed {@link Reservation} object
     */
    @Override
    public Reservation parseFromCSV(String csv) {
        String[] parts = csv.split(",", 5);

        int id = Integer.parseInt(parts[0]);

        String userCSV = parts[1];
        User user = new User().parseFromCSV(userCSV);

        String scheduleCSV = parts[2];
        ActivitySchedule activitySchedule = new ActivitySchedule().parseFromCSV(scheduleCSV);

        int numberOfPeople = Integer.parseInt(parts[3]);
        LocalDateTime reservationDate = LocalDateTime.parse(parts[4]);

        Reservation reservation = new Reservation(id, user, activitySchedule, numberOfPeople, reservationDate);
        reservation.setId(id);
        return reservation;
    }
}
