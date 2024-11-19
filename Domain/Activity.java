package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

/**
 * Represents an activity that users can participate in.
 * Extends the {@link ReviewableEntity} class.
 */
public class Activity extends ReviewableEntity implements Identifiable, EntityParser {

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
     */
    public Activity(int id, String activityName, int capacity, String location, EventType eventType, String description, double price) {
        super(id, activityName, eventType, location);
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
     */
    public void setCapacity(int capacity) {
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
     */
    public void setPrice(double price) {
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
     */
    public void setCurrentSize(int currentSize) {
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

    /**
     * Converts the activity object into a comma-separated values (CSV) string.
     *
     * @return a CSV string representing the activity
     */
    @Override
    public String toCSV() {
        return getId() + "," + getName() + "," + capacity + "," + getLocation() + "," + getEventType() + "," + description + "," + price;
    }

    /**
     * Parses a CSV string and creates an {@link Activity} object.
     *
     * @param csv the CSV string representing an activity
     * @return an {@link Activity} object parsed from the CSV string
     */
    @Override
    public Activity parseFromCSV(String csv) {
        String[] parts = csv.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        int capacity = Integer.parseInt(parts[2]);
        String location = parts[3];
        EventType eventType = EventType.valueOf(parts[4].toUpperCase());
        String description = parts[5];
        double price = Double.parseDouble(parts[6]);

        Activity activity = new Activity(id, name, capacity, location, eventType, description, price);
        activity.setId(id);
        return activity;
    }

    /**
     * Returns a string representation of the activity.
     *
     * @return a string with the activity's description, capacity, and price
     */
    @Override
    public String toString() {
        return "Activity{" +
                "description='" + description + '\'' +
                ", capacity=" + capacity +
                ", price=" + price +
                '}';
    }
}
