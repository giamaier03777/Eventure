package Domain;


import java.time.LocalDateTime;

public class Review {
    private int id;
    private User user;
    private ReviewableEntity reviewableEntity;
    private String comment;
    private LocalDateTime reviewDate;

    public Review(int id, User user, ReviewableEntity reviewableEntity, String comment, LocalDateTime reviewDate) {
        this.id = id;
        this.user = user;
        this.reviewableEntity = reviewableEntity;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReviewableEntity getReviewableEntity() {
        return reviewableEntity;
    }

    public void setReviewableEntity(ReviewableEntity reviewableEntity) {
        this.reviewableEntity = reviewableEntity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDateTime reviewDate) {
        this.reviewDate = reviewDate;
    }
}