package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Represents an event, which is a type of reviewable entity.
 * Events have specific attributes such as capacity, start and end dates, and ticket pricing.
 */
public class Event extends ReviewableEntity implements Identifiable, EntityParser {

    private int currentSize;
    private int capacity;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double price;

    /**
     * Constructs a new {@link Event}.
     *
     * @param id          the unique identifier of the event
     * @param eventName   the name of the event
     * @param location    the location where the event takes place
     * @param capacity    the total capacity of the event
     * @param eventType   the type of the event (e.g., ENTERTAINMENT, EDUCATIONAL)
     * @param currentSize the current number of attendees
     * @param startDate   the start date and time of the event
     * @param endDate     the end date and time of the event
     * @param price       the price per ticket for the event
     */
    public Event(int id, String eventName, String location, int capacity, EventType eventType, int currentSize, LocalDateTime startDate, LocalDateTime endDate, double price) {
        super(id, eventName, eventType, location);
        this.capacity = capacity;
        this.currentSize = currentSize;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    /**
     * Default constructor for {@link Event}.
     */
    public Event() {
        super();
    }

    /**
     * Gets the current size (number of attendees) of the event.
     *
     * @return the current number of attendees
     */
    public int getCurrentSize() {
        return currentSize;
    }

    /**
     * Sets the current size (number of attendees) of the event.
     *
     * @param currentSize the new current size
     */
    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    /**
     * Gets the start date and time of the event.
     *
     * @return the start date and time
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date and time of the event.
     *
     * @param startDate the new start date and time
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date and time of the event.
     *
     * @return the end date and time
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date and time of the event.
     *
     * @param endDate the new end date and time
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the total capacity of the event.
     *
     * @return the total capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the total capacity of the event.
     *
     * @param capacity the new capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Gets the start date of the event as an {@link Instant}.
     * This is useful for time zone calculations.
     *
     * @return the start date as an {@link Instant}
     */
    public Instant getDate() {
        return startDate.toInstant(ZoneOffset.UTC);
    }

    /**
     * Gets the price per ticket for the event.
     *
     * @return the ticket price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price per ticket for the event.
     *
     * @param price the new ticket price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return a string containing details about the event
     */
    @Override
    public String toString() {
        return "Id: " + this.getId() +
                ", Event name: " + this.getName() +
                ", Location: " + this.getLocation() +
                ", Event type: " + this.getEventType() +
                ", price: " + price + "\n";
    }

    /**
     * Converts the event object into a comma-separated values (CSV) string.
     *
     * @return a CSV string representing the event
     */
    @Override
    public String toCSV() {
        return getId() + "," + getName() + "," + getLocation() + "," + capacity + "," +
                getEventType().name() + "," + currentSize + "," + startDate + "," +
                endDate + "," + price;
    }

    /**
     * Parses a CSV string and creates an {@link Event} object.
     *
     * @param csv the CSV string representing an event
     * @return an {@link Event} object parsed from the CSV string
     */
    @Override
    public Event parseFromCSV(String csv) {
        String[] parts = csv.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String location = parts[2];
        int capacity = Integer.parseInt(parts[3]);
        EventType eventType = EventType.valueOf(parts[4]);
        int currentSize = Integer.parseInt(parts[5]);
        LocalDateTime startDate = LocalDateTime.parse(parts[6]);
        LocalDateTime endDate = LocalDateTime.parse(parts[7]);
        double price = Double.parseDouble(parts[8]);
        return new Event(id, name, location, capacity, eventType, currentSize, startDate, endDate, price);
    }
}
