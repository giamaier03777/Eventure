package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

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
     */
    public Review(int id, User user, ReviewableEntity reviewableEntity, String comment, LocalDateTime reviewDate) {
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
     */
    public void setUser(User user) {
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
     */
    public void setReviewableEntity(ReviewableEntity reviewableEntity) {
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
     */
    public void setComment(String comment) {
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
     */
    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }

    /**
     * Returns a string representation of the review.
     *
     * @return a string containing the review's details
     */
    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", user=" + user +
                ", reviewableEntity=" + reviewableEntity +
                ", comment='" + comment + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
