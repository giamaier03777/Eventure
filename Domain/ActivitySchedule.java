package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

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
     */
    public ActivitySchedule(Activity activity, LocalDate date, LocalTime startTime, LocalTime endTime, int availableCapacity) {
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
     */
    public void setActivity(Activity activity) {
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
     */
    public void setDate(LocalDate date) {
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
     */
    public void setStartTime(LocalTime startTime) {
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
     */
    public void setEndTime(LocalTime endTime) {
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
     */
    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    /**
     * Reduces the available capacity for the activity by the specified number of people.
     *
     * @param numberOfPeople the number of slots to reduce
     * @throws IllegalArgumentException if there is insufficient capacity
     */
    public void reduceCapacity(int numberOfPeople) {
        if (availableCapacity >= numberOfPeople) {
            availableCapacity -= numberOfPeople;
        } else {
            throw new IllegalArgumentException("Insufficient capacity for booking!");
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
