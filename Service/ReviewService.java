package Service;

import Domain.Review;
import Domain.User;
import Domain.ReviewableEntity;
import Repository.IRepository;
import Exception.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing reviews of reviewable entities within the system.
 */
public class ReviewService {

    private final IRepository<Review> reviewRepo;

    /**
     * Constructs a new {@code ReviewService}.
     *
     * @param reviewRepo the repository for managing {@link Review} objects.
     */
    public ReviewService(IRepository<Review> reviewRepo) {
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
     * @throws ValidationException if validation fails.
     */
    public void addReview(String idString, User user, ReviewableEntity reviewableEntity, String comment, String reviewDateString) {
        try {
            int id = Integer.parseInt(idString);
            LocalDateTime reviewDate = LocalDateTime.parse(reviewDateString);

            validateReviewInputs(user, reviewableEntity, comment, reviewDate);

            Review review = new Review(id, user, reviewableEntity, comment, reviewDate);
            reviewRepo.create(review);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID.", e);
        }
    }

    /**
     * Retrieves a review by its ID.
     *
     * @param id the unique identifier of the review.
     * @return the {@link Review} object if found.
     * @throws EntityNotFoundException if the review does not exist.
     */
    public Review getReviewById(int id) {
        Review review = reviewRepo.read(id);
        if (review == null) {
            throw new EntityNotFoundException("Review with ID " + id + " not found.");
        }
        return review;
    }

    /**
     * Updates an existing review in the repository.
     *
     * @param idString          the unique identifier of the review as a string.
     * @param newComment        the updated comment for the review.
     * @param newReviewDateString the updated date and time of the review as a string in ISO-8601 format.
     * @throws EntityNotFoundException if the review does not exist.
     * @throws ValidationException if validation fails.
     */
    public void updateReview(String idString, String newComment, String newReviewDateString) {
        try {
            int id = Integer.parseInt(idString);
            LocalDateTime newReviewDate = LocalDateTime.parse(newReviewDateString);

            Review review = reviewRepo.read(id);
            if (review == null) {
                throw new EntityNotFoundException("Review with ID " + id + " not found.");
            }

            validateReviewInputs(review.getUser(), review.getReviewableEntity(), newComment, newReviewDate);

            review.setComment(newComment);
            review.setReviewDate(newReviewDate);
            reviewRepo.update(review);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid number format for ID.", e);
        }
    }

    /**
     * Deletes a review by its ID.
     *
     * @param id the unique identifier of the review as a string.
     * @throws EntityNotFoundException if the review does not exist.
     */
    public void deleteReview(String id) {
        try {
            int reviewId = Integer.parseInt(id);
            if (reviewRepo.read(reviewId) == null) {
                throw new EntityNotFoundException("Review with ID " + id + " not found.");
            }
            reviewRepo.delete(reviewId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
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

    private void validateReviewInputs(User user, ReviewableEntity reviewableEntity, String comment, LocalDateTime reviewDate) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
        if (reviewableEntity == null) {
            throw new ValidationException("Reviewable entity cannot be null.");
        }
        if (comment == null || comment.trim().isEmpty()) {
            throw new ValidationException("Comment cannot be empty.");
        }
        if (reviewDate == null || reviewDate.isAfter(LocalDateTime.now())) {
            throw new ValidationException("Review date must be valid and cannot be in the future.");
        }
    }
}