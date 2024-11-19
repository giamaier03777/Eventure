package Service;

import Domain.Event;
import Domain.Review;
import Domain.User;
import Domain.ReviewableEntity;
import Repository.InMemoryRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing reviews of reviewable entities within the system.
 */
public class ReviewService {

    private final InMemoryRepo<Review> reviewRepo;

    /**
     * Constructs a new {@code ReviewService}.
     *
     * @param reviewRepo the repository for managing {@link Review} objects.
     */
    public ReviewService(InMemoryRepo<Review> reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    /**
     * Adds a new review to the repository.
     *
     * @param idString         the unique identifier of the review as a string.
     * @param user             the user who wrote the review.
     * @param reviewableEntity the entity being reviewed (e.g., activity, event).
     * @param comment          the text content of the review.
     * @param reviewDateString the date and time of the review as a string in ISO-8601 format.
     * @throws IllegalArgumentException if validation fails or the review already exists.
     */
    public void addReview(String idString, User user, ReviewableEntity reviewableEntity, String comment, String reviewDateString) {
        int id = Integer.parseInt(idString);
        LocalDateTime reviewDate = LocalDateTime.parse(reviewDateString);

        if (reviewRepo.read(id) != null) {
            throw new IllegalArgumentException("A review with this ID already exists.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (reviewableEntity == null) {
            throw new IllegalArgumentException("Reviewable entity cannot be null.");
        }

        if (comment == null || comment.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty.");
        }

        if (reviewDate == null || reviewDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Review date must be valid and cannot be in the future.");
        }

        Review review = new Review(id, user, reviewableEntity, comment, reviewDate);
        reviewRepo.create(review);
    }

    /**
     * Retrieves a review by its ID.
     *
     * @param id the unique identifier of the review.
     * @return the {@link Review} object if found.
     */
    public Review getReviewById(int id) {
        return reviewRepo.read(id);
    }

    /**
     * Updates an existing review in the repository.
     *
     * @param idString         the unique identifier of the review as a string.
     * @param newComment       the updated comment for the review.
     * @param newReviewDateString the updated date and time of the review as a string in ISO-8601 format.
     * @throws IllegalArgumentException if validation fails or the review does not exist.
     */
    public void updateReview(String idString, String newComment, String newReviewDateString) {
        int id = Integer.parseInt(idString);
        LocalDateTime newReviewDate = LocalDateTime.parse(newReviewDateString);

        Review review = reviewRepo.read(id);
        if (review == null) {
            throw new IllegalArgumentException("Review with the specified ID does not exist.");
        }

        if (newComment == null || newComment.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be empty.");
        }

        if (newReviewDate == null || newReviewDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Review date must be valid and cannot be in the future.");
        }

        review.setComment(newComment);
        review.setReviewDate(newReviewDate);
        reviewRepo.update(review);
    }

    /**
     * Deletes a review by its ID.
     *
     * @param id the unique identifier of the review as a string.
     * @throws IllegalArgumentException if the review does not exist.
     */
    public void deleteReview(String id) {
        Review review = reviewRepo.read(Integer.parseInt(id));
        if (review == null) {
            throw new IllegalArgumentException("Review with the specified ID does not exist.");
        }
        reviewRepo.delete(Integer.parseInt(id));
    }

    /**
     * Retrieves all reviews for a specific reviewable entity.
     *
     * @param entity the {@link ReviewableEntity} being reviewed.
     * @return a list of reviews associated with the given entity.
     */
    public List<Review> getReviewsByEvent(ReviewableEntity entity) {
        return reviewRepo.findAll().stream()
                .filter(review -> review.getReviewableEntity().equals(entity))
                .collect(Collectors.toList());
    }
}
