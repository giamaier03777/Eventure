package SQLParser;

import Domain.*;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLParser implementation for {@link Ticket} entities.
 */
public class TicketSQLParser implements SQLParser<Ticket> {

    private final UserSQLParser userSQLParser;
    private final ActivitySQLParser activitySQLParser;
    private final EventSQLParser eventSQLParser;
    private final FreeActivitySQLParser freeActivitySQLParser;

    /**
     * Constructs a {@link TicketSQLParser} with its dependencies.
     *
     * @param userSQLParser            the parser for {@link User} objects.
     * @param activitySQLParser        the parser for {@link Activity} objects.
     * @param eventSQLParser           the parser for {@link Event} objects.
     * @param freeActivitySQLParser    the parser for {@link FreeActivity} objects.
     */
    public TicketSQLParser(UserSQLParser userSQLParser,
                           ActivitySQLParser activitySQLParser,
                           EventSQLParser eventSQLParser,
                           FreeActivitySQLParser freeActivitySQLParser) {
        this.userSQLParser = userSQLParser;
        this.activitySQLParser = activitySQLParser;
        this.eventSQLParser = eventSQLParser;
        this.freeActivitySQLParser = freeActivitySQLParser;
    }

    @Override
    public String getColumns() {
        return "id, event_type, event_id, owner_id, participant_name";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "event_type = ?, event_id = ?, owner_id = ?, participant_name = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, Ticket ticket) throws SQLException {
        stmt.setInt(1, ticket.getId());

        setEventParameters(stmt, 2, ticket);

        stmt.setInt(4, ticket.getOwner().getId());
        stmt.setString(5, ticket.getParticipantName());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Ticket ticket) throws SQLException {
        setEventParameters(stmt, 1, ticket);

        stmt.setInt(3, ticket.getOwner().getId());
        stmt.setString(4, ticket.getParticipantName());
        stmt.setInt(5, ticket.getId()); // WHERE clause
    }

    @Override
    public Ticket parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");

        String eventType = rs.getString("event_type");
        int eventId = rs.getInt("event_id");

        // Parse the event (ReviewableEntity)
        ReviewableEntity event = parseReviewableEntityFromResultSet(eventType, rs);

        int ownerId = rs.getInt("owner_id");
        User owner = userSQLParser.parseFromResultSet(rs); // Parse the owner (User)

        String participantName = rs.getString("participant_name");

        return new Ticket(id, event, owner, participantName);
    }

    /**
     * Helper method to set event type and ID in PreparedStatement for insert/update.
     */
    private void setEventParameters(PreparedStatement stmt, int startIndex, Ticket ticket) throws SQLException {
        if (ticket.getEvent() instanceof Activity) {
            stmt.setString(startIndex, "Activity");
            stmt.setInt(startIndex + 1, ((Activity) ticket.getEvent()).getId());
        } else if (ticket.getEvent() instanceof Event) {
            stmt.setString(startIndex, "Event");
            stmt.setInt(startIndex + 1, ((Event) ticket.getEvent()).getId());
        } else if (ticket.getEvent() instanceof FreeActivity) {
            stmt.setString(startIndex, "FreeActivity");
            stmt.setInt(startIndex + 1, ((FreeActivity) ticket.getEvent()).getId());
        } else {
            throw new SQLException("Unknown ReviewableEntity type.");
        }
    }

    /**
     * Parses a {@link ReviewableEntity} from the ResultSet based on its type and ID.
     */
    private ReviewableEntity parseReviewableEntityFromResultSet(String type, ResultSet rs) throws SQLException {
        switch (type) {
            case "Activity":
                return activitySQLParser.parseFromResultSet(rs);
            case "Event":
                return eventSQLParser.parseFromResultSet(rs);
            case "FreeActivity":
                return freeActivitySQLParser.parseFromResultSet(rs);
            default:
                throw new SQLException("Unknown ReviewableEntity type: " + type);
        }
    }

    @Override
    public int getUpdateParametersCount() {
        return 5;
    }
}
