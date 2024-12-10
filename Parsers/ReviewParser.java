package Parsers;
import Domain.Review;
import Domain.ReviewableEntity;
import Domain.User;
import Repository.EntityParser;

import java.time.LocalDateTime;

/**
 * A parser for the {@link Review} class, used for converting between {@code Review} objects and their CSV representation.
 */
public class ReviewParser implements EntityParser<Review> {

    private final UserParser userParser = new UserParser();

    @Override
    public String toCSV(Review review) {
        return review.getId() + ";" +
                userParser.toCSV(review.getUser()) + ";" +
                review.getReviewableEntity().toCSV() + ";" +
                review.getComment() + ";" +
                review.getReviewDate();
    }

    @Override
    public Review parseFromCSV(String csv) {
        String[] fields = csv.split(";");

        int id = Integer.parseInt(fields[0]);
        User user = userParser.parseFromCSV(fields[1]);
        ReviewableEntity reviewableEntity = parseReviewableEntityFromCSV(fields[2]);
        String comment = fields[3];
        LocalDateTime reviewDate = LocalDateTime.parse(fields[4]);

        return new Review(id, user, reviewableEntity, comment, reviewDate);
    }

    /**
     * Parses a {@link ReviewableEntity} from a CSV string.
     *
     * @param csv the CSV string representing a reviewable entity.
     * @return the parsed {@link ReviewableEntity}.
     * @throws IllegalArgumentException if the entity type is unknown.
     */
    private ReviewableEntity parseReviewableEntityFromCSV(String csv) {
        String[] parts = csv.split(",", 2);
        String type = parts[0];
        String entityData = parts[1];

        switch (type) {
            case "Activity":
                return new ActivityParser().parseFromCSV(entityData);
            case "Event":
                return new EventParser().parseFromCSV(entityData);
            case "FreeActivity":
                return new FreeActivityParser().parseFromCSV(entityData);
            default:
                throw new IllegalArgumentException("Unknown ReviewableEntity type: " + type);
        }
    }
}

