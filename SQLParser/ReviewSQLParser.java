package SQLParser;

import Domain.*;
import Repository.DBRepository;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * SQLParser implementation for {@link Review} entities.
 */
public class ReviewSQLParser implements SQLParser<Review> {

    private final DBRepository<User> userRepo;
    private final DBRepository<Activity> activityRepo;
    private final DBRepository<Event> eventRepo;
    private final DBRepository<FreeActivity> freeActivityRepo;

    /**
     * Constructs a {@link ReviewSQLParser} with its dependencies.
     *
     * @param userRepo            the repository for {@link User} objects.
     * @param activityRepo        the repository for {@link Activity} objects.
     * @param eventRepo           the repository for {@link Event} objects.
     * @param freeActivityRepo    the repository for {@link FreeActivity} objects.
     */
    public ReviewSQLParser(DBRepository<User> userRepo,
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
        return "id, user_id, reviewable_entity_type, reviewable_entity_id, comment, review_date";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?, ?, ?, ?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "user_id = ?, reviewable_entity_type = ?, reviewable_entity_id = ?, comment = ?, review_date = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, Review review) throws SQLException {
        stmt.setInt(1, review.getId());
        stmt.setInt(2, review.getUser().getId());

        setReviewableEntityParameters(stmt, 3, review);

        stmt.setString(5, review.getComment());
        stmt.setObject(6, review.getReviewDate());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Review review) throws SQLException {
        stmt.setInt(1, review.getUser().getId());

        setReviewableEntityParameters(stmt, 2, review);

        stmt.setString(4, review.getComment());
        stmt.setObject(5, review.getReviewDate());
        stmt.setInt(6, review.getId());
    }

    @Override
    public Review parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");

        User user = userRepo.read(userId);
        if (user == null) {
            throw new SQLException("User with ID " + userId + " not found.");
        }

        String reviewableEntityType = rs.getString("reviewable_entity_type");
        int reviewableEntityId = rs.getInt("reviewable_entity_id");

        ReviewableEntity reviewableEntity = fetchReviewableEntityFromRepository(reviewableEntityType, reviewableEntityId);

        String comment = rs.getString("comment");
        LocalDateTime reviewDate = rs.getObject("review_date", LocalDateTime.class);

        return new Review(id, user, reviewableEntity, comment, reviewDate);
    }

    /**
     * Sets the reviewable entity type and ID in the PreparedStatement.
     */
    private void setReviewableEntityParameters(PreparedStatement stmt, int startIndex, Review review) throws SQLException {
        if (review.getReviewableEntity() instanceof Activity) {
            stmt.setString(startIndex, "Activity");
            stmt.setInt(startIndex + 1, ((Activity) review.getReviewableEntity()).getId());
        } else if (review.getReviewableEntity() instanceof Event) {
            stmt.setString(startIndex, "Event");
            stmt.setInt(startIndex + 1, ((Event) review.getReviewableEntity()).getId());
        } else if (review.getReviewableEntity() instanceof FreeActivity) {
            stmt.setString(startIndex, "FreeActivity");
            stmt.setInt(startIndex + 1, ((FreeActivity) review.getReviewableEntity()).getId());
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
