package SQLParser;

import Domain.*;
import Repository.DBRepository;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLParser implementation for {@link Ticket} entities.
 */
public class TicketSQLParser implements SQLParser<Ticket> {

    private final DBRepository<User> userRepo;
    private final DBRepository<Activity> activityRepo;
    private final DBRepository<Event> eventRepo;
    private final DBRepository<FreeActivity> freeActivityRepo;

    /**
     * Constructs a {@link TicketSQLParser} with its dependencies.
     *
     * @param userRepo            the repository for {@link User} objects.
     * @param activityRepo        the repository for {@link Activity} objects.
     * @param eventRepo           the repository for {@link Event} objects.
     * @param freeActivityRepo    the repository for {@link FreeActivity} objects.
     */
    public TicketSQLParser(DBRepository<User> userRepo,
                           DBRepository<Activity> activityRepo,
                           DBRepository<Event> eventRepo,
                           DBRepository<FreeActivity> freeActivityRepo) {
        this.userRepo = userRepo;
        this.activityRepo = activityRepo;
        this.eventRepo = eventRepo;
        this.freeActivityRepo = freeActivityRepo;
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
        stmt.setInt(5, ticket.getId());
    }

    @Override
    public Ticket parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");

        String eventType = rs.getString("event_type");
        int eventId = rs.getInt("event_id");

        ReviewableEntity event = fetchReviewableEntityFromRepository(eventType, eventId);

        int ownerId = rs.getInt("owner_id");
        User owner = userRepo.read(ownerId);
        if (owner == null) {
            throw new SQLException("User with ID " + ownerId + " not found.");
        }

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
     * Fetches a {@link ReviewableEntity} from the corresponding repository.
     *
     * @param type the type of the reviewable entity (Activity, Event, or FreeActivity).
     * @param id   the ID of the reviewable entity.
     * @return the fetched {@link ReviewableEntity}.
     * @throws SQLException if the entity type is unknown or not found.
     */
    private ReviewableEntity fetchReviewableEntityFromRepository(String type, int id) throws SQLException {
        switch (type) {
            case "Activity":
                Activity activity = activityRepo.read(id);
                if (activity == null) {
                    throw new SQLException("Activity with ID " + id + " not found.");
                }
                return activity;
            case "Event":
                Event event = eventRepo.read(id);
                if (event == null) {
                    throw new SQLException("Event with ID " + id + " not found.");
                }
                return event;
            case "FreeActivity":
                FreeActivity freeActivity = freeActivityRepo.read(id);
                if (freeActivity == null) {
                    throw new SQLException("FreeActivity with ID " + id + " not found.");
                }
                return freeActivity;
            default:
                throw new SQLException("Unknown ReviewableEntity type: " + type);
        }
    }

    @Override
    public int getUpdateParametersCount() {
        return 5;
    }
}
