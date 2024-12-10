package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import Exception.*;

/**
 * Represents an event, which is a type of reviewable entity.
 * Events have specific attributes such as capacity, start and end dates, and ticket pricing.
 */
public class Event extends ReviewableEntity implements Identifiable {

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
     * @throws ValidationException if any input parameter is invalid
     */
    public Event(int id, String eventName, String location, int capacity, EventType eventType, int currentSize, LocalDateTime startDate, LocalDateTime endDate, double price) {
        super(id, eventName, eventType, location);

        if (capacity <= 0) {
            throw new ValidationException("Capacity must be greater than 0.");
        }
        if (currentSize < 0) {
            throw new ValidationException("Current size cannot be negative.");
        }
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new ValidationException("Start date must be before end date, and both cannot be null.");
        }
        if (price < 0) {
            throw new ValidationException("Price cannot be negative.");
        }

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
     * @throws ValidationException if the current size is negative or exceeds capacity
     */
    public void setCurrentSize(int currentSize) {
        if (currentSize < 0) {
            throw new ValidationException("Current size cannot be negative.");
        }
        if (currentSize > capacity) {
            throw new BusinessLogicException("Current size cannot exceed event capacity.");
        }
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
     * @throws ValidationException if the start date is null or after the end date
     */
    public void setStartDate(LocalDateTime startDate) {
        if (startDate == null || (endDate != null && startDate.isAfter(endDate))) {
            throw new ValidationException("Start date must be before end date, and cannot be null.");
        }
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
     * @throws ValidationException if the end date is null or before the start date
     */
    public void setEndDate(LocalDateTime endDate) {
        if (endDate == null || (startDate != null && endDate.isBefore(startDate))) {
            throw new ValidationException("End date must be after start date, and cannot be null.");
        }
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
     * @throws ValidationException if the capacity is less than or equal to 0
     */
    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new ValidationException("Capacity must be greater than 0.");
        }
        if (currentSize > capacity) {
            throw new BusinessLogicException("Capacity cannot be less than the current size.");
        }
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
     * @throws ValidationException if the price is negative
     */
    public void setPrice(double price) {
        if (price < 0) {
            throw new ValidationException("Price cannot be negative.");
        }
        this.price = price;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return a string containing details about the event
     */
    @Override
    public String toString() {
        return String.format(
                "Event Details:\n" +
                        "- ID: %d\n" +
                        "- Name: %s\n" +
                        "- Location: %s\n" +
                        "- Type: %s\n" +
                        "- Price: %.2f\n",
                this.getId(), this.getName(), this.getLocation(), this.getEventType(), this.price
        );
    }

    @Override
    public String toCSV() {
        return "Event," + getId() + "," +
                getName() + "," +
                getLocation() + "," +
                getCapacity() + "," +
                getEventType() + "," +
                getCurrentSize() + "," +
                getStartDate() + "," +
                getEndDate() + "," +
                getPrice();
    }
}
