package SQLParser;

import Domain.*;
import Repository.SQLParser;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * SQLParser implementation for {@link Review} entities.
 */
public class ReviewSQLParser implements SQLParser<Review> {

    private final UserSQLParser userSQLParser;
    private final ActivitySQLParser activitySQLParser;
    private final EventSQLParser eventSQLParser;
    private final FreeActivitySQLParser freeActivitySQLParser;

    /**
     * Constructs a {@link ReviewSQLParser} with its dependencies.
     *
     * @param userSQLParser           the parser for {@link User} objects.
     * @param activitySQLParser       the parser for {@link Activity} objects.
     * @param eventSQLParser          the parser for {@link Event} objects.
     * @param freeActivitySQLParser  the parser for {@link FreeActivity} objects.
     */
    public ReviewSQLParser(UserSQLParser userSQLParser,
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

        // Handle the reviewable entity type and ID
        setReviewableEntityParameters(stmt, 3, review);

        stmt.setString(5, review.getComment());
        stmt.setObject(6, review.getReviewDate());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Review review) throws SQLException {
        stmt.setInt(1, review.getUser().getId());

        // Handle the reviewable entity type and ID
        setReviewableEntityParameters(stmt, 2, review);

        stmt.setString(4, review.getComment());
        stmt.setObject(5, review.getReviewDate());
        stmt.setInt(6, review.getId()); // WHERE clause
    }

    @Override
    public Review parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");

        User user = userSQLParser.parseFromResultSet(rs);

        String reviewableEntityType = rs.getString("reviewable_entity_type");
        int reviewableEntityId = rs.getInt("reviewable_entity_id");

        ReviewableEntity reviewableEntity = parseReviewableEntityFromResultSet(reviewableEntityType, rs);

        String comment = rs.getString("comment");
        LocalDateTime reviewDate = rs.getObject("review_date", LocalDateTime.class);

        return new Review(id, user, reviewableEntity, comment, reviewDate);
    }

    /**
     * Sets the reviewable entity type and ID in the PreparedStatement.
     * This reduces redundancy in the insert/update methods.
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
     * Parses a {@link ReviewableEntity} from the ResultSet based on its type and ID.
     *
     * @param type the type of the reviewable entity (Activity, Event, or FreeActivity).
     * @return the parsed {@link ReviewableEntity}.
     * @throws SQLException if the entity type is unknown.
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
