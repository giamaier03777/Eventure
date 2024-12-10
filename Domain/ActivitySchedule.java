package Domain;

import Repository.EntityParser;
import Repository.Identifiable;
import Exception.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a schedule for a specific activity, including date, time, and available capacity.
 */
public class ActivitySchedule implements Identifiable {

    private int id;
    private Activity activity;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private int availableCapacity;

    /**
     * Constructor to create a new {@link ActivitySchedule}.
     *
     * @param activity          the activity associated with the schedule
     * @param date              the date of the activity
     * @param startTime         the start time of the activity
     * @param endTime           the end time of the activity
     * @param availableCapacity the number of available slots for the activity
     * @throws ValidationException if the provided arguments are invalid
     */
    public ActivitySchedule(Activity activity, LocalDate date, LocalTime startTime, LocalTime endTime, int availableCapacity) {
        if (activity == null) {
            throw new ValidationException("Activity cannot be null.");
        }
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new ValidationException("Date cannot be null or in the past.");
        }
        if (startTime == null || endTime == null || startTime.isAfter(endTime)) {
            throw new ValidationException("Start time must be before end time, and both cannot be null.");
        }
        if (availableCapacity < 0) {
            throw new ValidationException("Available capacity cannot be negative.");
        }
        this.activity = activity;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.availableCapacity = availableCapacity;
    }

    /**
     * Default constructor for {@link ActivitySchedule}.
     */
    public ActivitySchedule() {

    }

    /**
     * Gets the unique identifier for the schedule.
     *
     * @return the ID of the schedule
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the schedule.
     *
     * @param id the new ID of the schedule
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the associated activity.
     *
     * @return the activity
     */
    public Activity getActivity() {
        return activity;
    }

    /**
     * Sets the associated activity.
     *
     * @param activity the new activity
     * @throws ValidationException if the activity is null
     */
    public void setActivity(Activity activity) {
        if (activity == null) {
            throw new ValidationException("Activity cannot be null.");
        }
        this.activity = activity;
    }

    /**
     * Gets the date of the scheduled activity.
     *
     * @return the date of the activity
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the date of the scheduled activity.
     *
     * @param date the new date of the activity
     * @throws ValidationException if the date is in the past or null
     */
    public void setDate(LocalDate date) {
        if (date == null || date.isBefore(LocalDate.now())) {
            throw new ValidationException("Date cannot be null or in the past.");
        }
        this.date = date;
    }

    /**
     * Gets the start time of the scheduled activity.
     *
     * @return the start time of the activity
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the scheduled activity.
     *
     * @param startTime the new start time of the activity
     * @throws ValidationException if the start time is after the end time
     */
    public void setStartTime(LocalTime startTime) {
        if (startTime == null || (endTime != null && startTime.isAfter(endTime))) {
            throw new ValidationException("Start time must be before end time, and cannot be null.");
        }
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the scheduled activity.
     *
     * @return the end time of the activity
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the scheduled activity.
     *
     * @param endTime the new end time of the activity
     * @throws ValidationException if the end time is before the start time
     */
    public void setEndTime(LocalTime endTime) {
        if (endTime == null || (startTime != null && endTime.isBefore(startTime))) {
            throw new ValidationException("End time must be after start time, and cannot be null.");
        }
        this.endTime = endTime;
    }

    /**
     * Gets the number of available slots for the scheduled activity.
     *
     * @return the available capacity
     */
    public int getAvailableCapacity() {
        return availableCapacity;
    }

    /**
     * Sets the number of available slots for the scheduled activity.
     *
     * @param availableCapacity the new available capacity
     * @throws ValidationException if the capacity is negative
     */
    public void setAvailableCapacity(int availableCapacity) {
        if (availableCapacity < 0) {
            throw new ValidationException("Available capacity cannot be negative.");
        }
        this.availableCapacity = availableCapacity;
    }

    /**
     * Reduces the available capacity for the activity by the specified number of people.
     *
     * @param numberOfPeople the number of slots to reduce
     * @throws BusinessLogicException if there is insufficient capacity
     */
    public void reduceCapacity(int numberOfPeople) {
        if (numberOfPeople <= 0) {
            throw new ValidationException("Number of people must be greater than 0.");
        }
        if (availableCapacity >= numberOfPeople) {
            availableCapacity -= numberOfPeople;
        } else {
            throw new BusinessLogicException("Insufficient capacity for booking!");
        }
    }

    @Override
    public String toString() {
        return String.format(
                "Activity Schedule Details:\n" +
                        "- ID: %d\n" +
                        "- Activity: %s\n" +
                        "- Date: %s\n" +
                        "- Start Time: %s\n" +
                        "- End Time: %s\n" +
                        "- Available Capacity: %d",
                id, activity, date, startTime, endTime, availableCapacity
        );
    }
}
