package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

/**
 * Represents a ticket for an event or activity within the system.
 * Implements {@link Identifiable} for unique identification and {@link EntityParser} for CSV serialization/deserialization.
 */
public class Ticket implements Identifiable, EntityParser {
    private int id;
    private ReviewableEntity event;
    private User owner;
    private String participantName;

    /**
     * Constructs a new {@code Ticket}.
     *
     * @param id              the unique identifier for the ticket.
     * @param event           the event or activity associated with the ticket.
     * @param owner           the user who owns the ticket.
     * @param participantName the name of the participant for whom the ticket is reserved.
     */
    public Ticket(int id, ReviewableEntity event, User owner, String participantName) {
        this.id = id;
        this.event = event;
        this.owner = owner;
        this.participantName = participantName;
    }

    /**
     * Default constructor for creating an empty {@code Ticket} object.
     */
    public Ticket() {
    }

    /**
     * Gets the unique identifier of the ticket.
     *
     * @return the ticket ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the ticket.
     *
     * @param id the ticket ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the event or activity associated with the ticket.
     *
     * @return the associated event or activity.
     */
    public ReviewableEntity getEvent() {
        return event;
    }

    /**
     * Sets the event or activity associated with the ticket.
     *
     * @param event the associated event or activity.
     */
    public void setEvent(ReviewableEntity event) {
        this.event = event;
    }

    /**
     * Gets the user who owns the ticket.
     *
     * @return the ticket owner.
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Sets the user who owns the ticket.
     *
     * @param owner the ticket owner.
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Gets the name of the participant for whom the ticket is reserved.
     *
     * @return the participant's name.
     */
    public String getParticipantName() {
        return participantName;
    }

    /**
     * Sets the name of the participant for whom the ticket is reserved.
     *
     * @param participantName the participant's name.
     */
    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    /**
     * Converts the {@code Ticket} object into a CSV string representation.
     *
     * @return a CSV string representing the ticket.
     */
    @Override
    public String toCSV() {
        return id + "," + event.toCSV() + "," + owner.toCSV() + "," + participantName;
    }

    /**
     * Parses a {@code Ticket} object from a CSV string.
     *
     * @param csv the CSV string to parse.
     * @return the constructed {@code Ticket} object.
     */
    @Override
    public Ticket parseFromCSV(String csv) {
        String[] parts = csv.split(",", 4);

        int id = Integer.parseInt(parts[0]);

        String entityCSV = parts[1];
        ReviewableEntity event = parseReviewableEntityFromCSV(entityCSV);

        String userCSV = parts[2];
        User owner = new User().parseFromCSV(userCSV);

        String participantName = parts[3];

        Ticket ticket = new Ticket(id, event, owner, participantName);
        ticket.setId(id);
        return ticket;
    }

    /**
     * Helper method to parse a {@link ReviewableEntity} from a CSV string.
     *
     * @param csv the CSV string to parse.
     * @return the constructed {@link ReviewableEntity} object.
     * @throws IllegalArgumentException if the entity type is unknown.
     */
    private ReviewableEntity parseReviewableEntityFromCSV(String csv) {
        String[] parts = csv.split(",", 2);
        String type = parts[0];
        String entityData = parts[1];

        switch (type) {
            case "Activity":
                return new Activity().parseFromCSV(entityData);
            case "Event":
                return new Event().parseFromCSV(entityData);
            case "FreeActivity":
                return new FreeActivity().parseFromCSV(entityData);
            default:
                throw new IllegalArgumentException("Unknown ReviewableEntity type: " + type);
        }
    }
}
