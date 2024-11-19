package Parsers;

import Domain.*;
import Repository.EntityParser;

/**
 * A parser for Ticket entities.
 */
public class TicketParser implements EntityParser<Ticket> {

    private final UserParser userParser = new UserParser();

    @Override
    public String toCSV(Ticket ticket) {
        return ticket.getId() + "," +
                ticket.getEvent().getClass().getSimpleName() + "," +
                ticket.getEvent().toCSV() + "," +
                userParser.toCSV(ticket.getOwner()) + "," +
                ticket.getParticipantName();
    }

    @Override
    public Ticket parseFromCSV(String csv) {
        String[] fields = csv.split(",", 5);
        int id = Integer.parseInt(fields[0]);
        String eventType = fields[1];
        String eventCSV = fields[2];
        String userCSV = fields[3];
        String participantName = fields[4];

        ReviewableEntity event = parseReviewableEntityFromCSV(eventType, eventCSV);
        User owner = userParser.parseFromCSV(userCSV);

        return new Ticket(id, event, owner, participantName);
    }

    /**
     * Parses a {@link ReviewableEntity} from its CSV representation.
     *
     * @param eventType the type of the entity (e.g., "Activity", "Event", "FreeActivity").
     * @param csv       the CSV string representation of the entity.
     * @return the parsed {@link ReviewableEntity}.
     * @throws IllegalArgumentException if the entity type is unknown.
     */
    private ReviewableEntity parseReviewableEntityFromCSV(String eventType, String csv) {
        switch (eventType) {
            case "Activity":
                return new ActivityParser().parseFromCSV(csv);
            case "Event":
                return new EventParser().parseFromCSV(csv);
            case "FreeActivity":
                return new FreeActivityParser().parseFromCSV(csv);
            default:
                throw new IllegalArgumentException("Unknown ReviewableEntity type: " + eventType);
        }
    }
}
