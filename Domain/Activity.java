package Domain;

import Repository.Identifiable;
import Exception.*;
/**
 * Represents an activity that users can participate in.
 * Extends the {@link ReviewableEntity} class.
 */
public class Activity extends ReviewableEntity implements Identifiable {

    private int currentSize;
    private String description;
    private int capacity;
    private double price;

    /**
     * Constructor to create a new {@link Activity}.
     *
     * @param id            the unique identifier of the activity
     * @param activityName  the name of the activity
     * @param capacity      the maximum number of participants for the activity
     * @param location      the location where the activity takes place
     * @param eventType     the type of event (category) for this activity
     * @param description   a brief description of the activity
     * @param price         the price to participate in the activity
     * @throws ValidationException if capacity or price are invalid
     */
    public Activity(int id, String activityName, int capacity, String location, EventType eventType, String description, double price) {
        super(id, activityName, eventType, location);
        if (capacity <= 0) {
            throw new ValidationException("Capacity must be greater than 0.");
        }
        if (price < 0) {
            throw new ValidationException("Price cannot be negative.");
        }
        this.capacity = capacity;
        this.description = description;
        this.price = price;
        this.currentSize = 0;
    }

    /**
     * Default constructor for {@link Activity}.
     */
    public Activity() {
        super();
    }

    /**
     * Gets the unique identifier of the activity.
     *
     * @return the ID of the activity
     */
    @Override
    public int getId() {
        return super.getId();
    }

    /**
     * Sets the unique identifier of the activity.
     *
     * @param id the new ID of the activity
     */
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String toCSV() {
        return "Activity," + getId() + "," +
                getName() + "," +
                getCapacity() + "," +
                getLocation() + "," +
                getCategory() + "," +
                getDescription() + "," +
                getPrice();
    }

    /**
     * Gets the description of the activity.
     *
     * @return the description of the activity
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the activity.
     *
     * @param description the new description of the activity
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the maximum capacity of the activity.
     *
     * @return the capacity of the activity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the maximum capacity of the activity.
     *
     * @param capacity the new capacity of the activity
     * @throws ValidationException if the capacity is invalid
     */
    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new ValidationException("Capacity must be greater than 0.");
        }
        this.capacity = capacity;
    }

    /**
     * Gets the price to participate in the activity.
     *
     * @return the price of the activity
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price to participate in the activity.
     *
     * @param price the new price of the activity
     * @throws ValidationException if the price is negative
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new ValidationException("Price cannot be negative.");
        }
        this.price = price;
    }

    /**
     * Gets the current number of participants in the activity.
     *
     * @return the current number of participants
     */
    public int getCurrentSize() {
        return currentSize;
    }

    /**
     * Sets the current number of participants in the activity.
     *
     * @param currentSize the new number of participants
     * @throws BusinessLogicException if the current size exceeds the capacity
     */
    public void setCurrentSize(int currentSize) {
        if (currentSize > capacity) {
            throw new BusinessLogicException("Current size cannot exceed the activity's capacity.");
        }
        this.currentSize = currentSize;
    }

    /**
     * Gets the event type (category) of the activity.
     *
     * @return the event type of the activity
     */
    public EventType getCategory() {
        return super.getEventType();
    }

    public void setCategory(EventType activityCategory) {
        super.setEventType(activityCategory);
    }

    /**
     * Returns a string representation of the activity.
     *
     * @return a string with the activity's description, capacity, and price
     */
    @Override
    public String toString() {
        return String.format(
                "Activity Details:\n" +
                        "- Description: %s\n" +
                        "- Capacity: %d\n" +
                        "- Price: %.2f euros",
                description, capacity, price
        );
    }
}
