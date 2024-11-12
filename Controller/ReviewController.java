package Controller;

import Domain.Event;
import Domain.Review;
import Domain.User;
import Domain.ReviewableEntity;
import Service.ReviewService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void addReview(String idString, User user, ReviewableEntity reviewableEntity, String comment, String reviewDateString) {
        try {
            int id = Integer.parseInt(idString);
            LocalDateTime reviewDate = LocalDateTime.parse(reviewDateString);

            reviewService.addReview(id, user, reviewableEntity, comment, reviewDate);
            System.out.println("Review added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (DateTimeParseException e) {
            System.out.println("Review date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Review getReviewById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return reviewService.getReviewById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updateReview(String idString, String newComment, String newReviewDateString) {
        try {
            int id = Integer.parseInt(idString);
            LocalDateTime newReviewDate = LocalDateTime.parse(newReviewDateString);

            reviewService.updateReview(id, newComment, newReviewDate);
            System.out.println("Review updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (DateTimeParseException e) {
            System.out.println("Review date must be in a valid format (e.g., '2023-12-31T10:15:30').");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteReview(String idString) {
        try {
            int id = Integer.parseInt(idString);
            reviewService.deleteReview(id);
            System.out.println("Review deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Review> getReviewByEvent(ReviewableEntity event) {
        return this.reviewService.getReviewsByEvent(event);
    }
}
