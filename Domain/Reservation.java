package Domain;

import Repository.EntityParser;
import Repository.Identifiable;
import Exception.*;

import java.time.LocalDateTime;

/**
 * Represents a reservation made by a user for an activity.
 * Implements {@link Identifiable} for providing unique IDs and {@link EntityParser} for parsing and serialization.
 */
public class Reservation implements Identifiable {
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
     * @throws ValidationException if any input parameter is invalid
     * @throws BusinessLogicException if there is insufficient capacity in the activity schedule
     */
    public Reservation(int id, User user, ActivitySchedule activitySchedule, int numberOfPeople, LocalDateTime reservationDate) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
        if (activitySchedule == null) {
            throw new ValidationException("Activity schedule cannot be null.");
        }
        if (numberOfPeople <= 0) {
            throw new ValidationException("Number of people must be greater than 0.");
        }
        if (reservationDate == null || reservationDate.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Reservation date cannot be null or in the future.");
        }
        if (activitySchedule.getAvailableCapacity() < numberOfPeople) {
            throw new BusinessLogicException("Insufficient capacity in the activity schedule for the requested number of people.");
        }

        this.id = id;
        this.user = user;
        this.activitySchedule = activitySchedule;
        this.numberOfPeople = numberOfPeople;
        this.reservationDate = reservationDate;

        // Update the capacity of the activity schedule
        this.activitySchedule.reduceCapacity(numberOfPeople);
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
     * @throws ValidationException if the user is null
     */
    public void setUser(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
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
     * @throws ValidationException if the activity schedule is null
     */
    public void setActivitySchedule(ActivitySchedule activitySchedule) {
        if (activitySchedule == null) {
            throw new ValidationException("Activity schedule cannot be null.");
        }
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
     * @throws ValidationException if the number of people is less than or equal to 0
     * @throws BusinessLogicException if the activity schedule does not have sufficient capacity
     */
    public void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople <= 0) {
            throw new ValidationException("Number of people must be greater than 0.");
        }
        if (activitySchedule != null && activitySchedule.getAvailableCapacity() < numberOfPeople) {
            throw new BusinessLogicException("Insufficient capacity in the activity schedule for the requested number of people.");
        }
        this.numberOfPeople = numberOfPeople;

        // Update the activity schedule capacity
        if (activitySchedule != null) {
            activitySchedule.reduceCapacity(numberOfPeople);
        }
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
     * @throws ValidationException if the reservation date is null or in the future
     */
    public void setReservationDate(LocalDateTime reservationDate) {
        if (reservationDate == null || reservationDate.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Reservation date cannot be null or in the future.");
        }
        this.reservationDate = reservationDate;
    }

    @Override
    public String toString() {
        return String.format(
                "Reservation Details:\n" +
                        "- ID: %d\n" +
                        "- User: %s\n" +
                        "- Activity Schedule: %s\n" +
                        "- Number of People: %d\n" +
                        "- Reservation Date: %s\n",
                id,
                user != null ? user.toString() : "No user",
                activitySchedule != null ? activitySchedule.toString() : "No activity schedule",
                numberOfPeople,
                reservationDate != null ? reservationDate.toString() : "No reservation date"
        );
    }
}
