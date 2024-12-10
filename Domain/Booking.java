package Domain;

import Repository.EntityParser;
import Repository.Identifiable;
import Exception.*;

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
     * @throws ValidationException if any of the provided arguments are invalid
     * @throws BusinessLogicException if there is insufficient capacity in the schedule
     */
    public Booking(ActivitySchedule schedule, String customerName, int numberOfPeople) {
        if (schedule == null) {
            throw new ValidationException("Schedule cannot be null.");
        }
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new ValidationException("Customer name cannot be null or empty.");
        }
        if (numberOfPeople <= 0) {
            throw new ValidationException("Number of people must be greater than 0.");
        }

        this.schedule = schedule;
        this.customerName = customerName;
        this.numberOfPeople = numberOfPeople;

        // Try to reduce capacity in the schedule, may throw BusinessLogicException
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
     * @throws ValidationException if the schedule is null
     */
    public void setSchedule(ActivitySchedule schedule) {
        if (schedule == null) {
            throw new ValidationException("Schedule cannot be null.");
        }
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
     * @throws ValidationException if the customer name is null or empty
     */
    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new ValidationException("Customer name cannot be null or empty.");
        }
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
     * @throws ValidationException if the number of people is less than or equal to 0
     */
    public void setNumberOfPeople(int numberOfPeople) {
        if (numberOfPeople <= 0) {
            throw new ValidationException("Number of people must be greater than 0.");
        }
        if (schedule != null && numberOfPeople > schedule.getAvailableCapacity()) {
            throw new BusinessLogicException("Insufficient capacity for the specified number of people.");
        }
        this.numberOfPeople = numberOfPeople;
    }

    @Override
    public String toString() {
        return String.format(
                "Booking Details:\n" +
                        "- ID: %d\n" +
                        "- Schedule: %s\n" +
                        "- Customer Name: %s\n" +
                        "- Number of People: %d",
                id, schedule, customerName, numberOfPeople
        );
    }
}
