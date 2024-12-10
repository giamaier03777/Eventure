package Domain;

import Repository.EntityParser;
import Repository.Identifiable;
import Exception.*;

import java.time.LocalDateTime;

/**
 * Represents a review given by a user for a specific reviewable entity.
 * Implements {@link Identifiable} for unique identification and {@link EntityParser} for CSV serialization/deserialization.
 */
public class Review implements Identifiable {
    private int id;
    private User user;
    private ReviewableEntity reviewableEntity;
    private String comment;
    private LocalDateTime reviewDate;

    /**
     * Constructs a Review instance with the specified details.
     *
     * @param id               the unique identifier of the review
     * @param user             the user who created the review
     * @param reviewableEntity the entity (activity, event, etc.) being reviewed
     * @param comment          the textual content of the review
     * @param reviewDate       the date and time the review was made
     * @throws ValidationException if any parameter is invalid
     */
    public Review(int id, User user, ReviewableEntity reviewableEntity, String comment, LocalDateTime reviewDate) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
        if (reviewableEntity == null) {
            throw new ValidationException("Reviewable entity cannot be null.");
        }
        if (comment == null || comment.trim().isEmpty()) {
            throw new ValidationException("Comment cannot be null or empty.");
        }
        if (reviewDate == null || reviewDate.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Review date cannot be null or in the future.");
        }

        this.id = id;
        this.user = user;
        this.reviewableEntity = reviewableEntity;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    /**
     * Default constructor for creating an empty {@link Review} object.
     */
    public Review() {
    }

    /**
     * Gets the unique identifier of the review.
     *
     * @return the review ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the review.
     *
     * @param id the new review ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user who created the review.
     *
     * @return the user who created the review
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who created the review.
     *
     * @param user the new user
     * @throws ValidationException if the user is null
     */
    public void setUser(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
        this.user = user;
    }

    /**
     * Gets the entity being reviewed.
     *
     * @return the reviewed entity
     */
    public ReviewableEntity getReviewableEntity() {
        return reviewableEntity;
    }

    /**
     * Sets the entity being reviewed.
     *
     * @param reviewableEntity the new reviewed entity
     * @throws ValidationException if the reviewable entity is null
     */
    public void setReviewableEntity(ReviewableEntity reviewableEntity) {
        if (reviewableEntity == null) {
            throw new ValidationException("Reviewable entity cannot be null.");
        }
        this.reviewableEntity = reviewableEntity;
    }

    /**
     * Gets the textual content of the review.
     *
     * @return the review comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the textual content of the review.
     *
     * @param comment the new comment
     * @throws ValidationException if the comment is null or empty
     */
    public void setComment(String comment) {
        if (comment == null || comment.trim().isEmpty()) {
            throw new ValidationException("Comment cannot be null or empty.");
        }
        this.comment = comment;
    }

    /**
     * Gets the date and time when the review was made.
     *
     * @return the review date
     */
    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    /**
     * Sets the date and time when the review was made.
     *
     * @param reviewDate the new review date
     * @throws ValidationException if the review date is null or in the future
     */
    public void setReviewDate(LocalDateTime reviewDate) {
        if (reviewDate == null || reviewDate.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Review date cannot be null or in the future.");
        }
        this.reviewDate = reviewDate;
    }

    /**
     * Returns a string representation of the review.
     *
     * @return a string containing the review's details
     */
    @Override
    public String toString() {
        return String.format(
                "Review Details:\n" +
                        "- ID: %d\n" +
                        "- User: %s\n" +
                        "- Reviewable Entity: %s\n" +
                        "- Comment: %s\n" +
                        "- Review Date: %s\n",
                id,
                user != null ? user.toString() : "No user",
                reviewableEntity != null ? reviewableEntity.toString() : "No reviewable entity",
                comment != null ? comment : "No comment",
                reviewDate != null ? reviewDate.toString() : "No review date"
        );
    }
}
