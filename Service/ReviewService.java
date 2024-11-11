package Service;

import Domain.Review;
import Domain.User;
import Domain.ReviewableEntity;
import Repository.ReviewRepo;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewService {

    private final ReviewRepo reviewRepo;

    public ReviewService(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    public void addReview(int id, User user, ReviewableEntity reviewableEntity, String comment, LocalDateTime reviewDate) {
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

    public Review getReviewById(int id) {
        return reviewRepo.read(id);
    }

    public void updateReview(int id, String newComment, LocalDateTime newReviewDate) {
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


    public void deleteReview(int id) {
        Review review = reviewRepo.read(id);
        if (review == null) {
            throw new IllegalArgumentException("Review with the specified ID does not exist.");
        }
        reviewRepo.delete(id);
    }

}



