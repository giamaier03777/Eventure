package Domain;

import Repository.EntityParser;
import Repository.Identifiable;
import Exception.*;


/**
 * Represents a ticket for an event or activity within the system.
 * Implements {@link Identifiable} for unique identification and {@link EntityParser} for CSV serialization/deserialization.
 */
public class Ticket implements Identifiable {
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
     * @throws ValidationException if any parameter is invalid
     */
    public Ticket(int id, ReviewableEntity event, User owner, String participantName) {
        if (event == null) {
            throw new ValidationException("Event cannot be null.");
        }
        if (owner == null) {
            throw new ValidationException("Owner cannot be null.");
        }
        if (participantName == null || participantName.trim().isEmpty()) {
            throw new ValidationException("Participant name cannot be null or empty.");
        }

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
     * @throws ValidationException if the event is null
     */
    public void setEvent(ReviewableEntity event) {
        if (event == null) {
            throw new ValidationException("Event cannot be null.");
        }
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
     * @throws ValidationException if the owner is null
     */
    public void setOwner(User owner) {
        if (owner == null) {
            throw new ValidationException("Owner cannot be null.");
        }
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
     * @throws ValidationException if the participant name is null or empty
     */
    public void setParticipantName(String participantName) {
        if (participantName == null || participantName.trim().isEmpty()) {
            throw new ValidationException("Participant name cannot be null or empty.");
        }
        this.participantName = participantName;
    }

    @Override
    public String toString() {
        return String.format(
                "Ticket Details:\n" +
                        "- ID: %d\n" +
                        "- Event: %s\n" +
                        "- Owner: %s\n" +
                        "- Participant Name: %s\n",
                id,
                event != null ? event.toString() : "No event",
                owner != null ? owner.toString() : "No owner",
                participantName != null ? participantName : "No participant name"
        );
    }
}
