package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

import java.io.Serializable;

/**
 * Represents an abstract base class for entities that can be reviewed.
 * This class defines common properties and methods for reviewable entities such as events, activities, etc.
 */
public abstract class ReviewableEntity implements Identifiable {
    private int id;
    private String name;
    private EventType eventType;
    private String location;

    /**
     * Constructs a ReviewableEntity with the specified details.
     *
     * @param id        the unique identifier for the entity
     * @param name      the name of the entity
     * @param eventType the type of event associated with the entity
     * @param location  the location of the entity
     */
    public ReviewableEntity(int id, String name, EventType eventType, String location) {
        this.id = id;
        this.name = name;
        this.eventType = eventType;
        this.location = location;
    }

    public ReviewableEntity() {

    }

    /**
     * Gets the ID of the entity.
     *
     * @return the entity ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the entity.
     *
     * @param id the new entity ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the entity.
     *
     * @return the entity name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the entity.
     *
     * @param name the new entity name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the event type associated with the entity.
     *
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Sets the event type associated with the entity.
     *
     * @param eventType the new event type
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Gets the location of the entity.
     *
     * @return the entity location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the entity.
     *
     * @param location the new entity location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Converts the entity to a CSV string.
     *
     * @return the CSV representation of the entity.
     */
    public abstract String toCSV();


}
