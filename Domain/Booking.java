package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

/**
 * Represents a booking for an activity schedule.
 */
public class Booking implements Identifiable {

    private int id;
    private ActivitySchedule schedule;
    private String customerName;
    private int numberOfPeople;

    /**
     * Constructs a new {@link Booking}.
     *
     * @param schedule       the activity schedule associated with the booking
     * @param customerName   the name of the customer making the booking
     * @param numberOfPeople the number of people included in the booking
     */
    public Booking(ActivitySchedule schedule, String customerName, int numberOfPeople) {
        this.schedule = schedule;
        this.customerName = customerName;
        this.numberOfPeople = numberOfPeople;

        this.schedule.reduceCapacity(numberOfPeople);
    }

    /**
     * Default constructor for {@link Booking}.
     */
    public Booking() {

    }

    /**
     * Gets the unique identifier of the booking.
     *
     * @return the ID of the booking
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the booking.
     *
     * @param id the new ID of the booking
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the activity schedule associated with the booking.
     *
     * @return the associated activity schedule
     */
    public ActivitySchedule getSchedule() {
        return schedule;
    }

    /**
     * Sets the activity schedule associated with the booking.
     *
     * @param schedule the new activity schedule
     */
    public void setSchedule(ActivitySchedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Gets the name of the customer who made the booking.
     *
     * @return the customer's name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer who made the booking.
     *
     * @param customerName the new customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the number of people included in the booking.
     *
     * @return the number of people in the booking
     */
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    /**
     * Sets the number of people included in the booking.
     *
     * @param numberOfPeople the new number of people
     */
    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
