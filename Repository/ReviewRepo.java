package Repository;

import Domain.Review;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepo implements IRepository<Review> {

    private List<Review> reviewList = new ArrayList<>();


    @Override
    public void create(Review entity) {
        if (entity != null) {
            reviewList.add(entity);
        }
    }

    @Override
    public Review read(int id) {
        for (Review review : reviewList) {
            if (review.getId() == id) {
                return review;
            }
        }
        return null;
    }

    @Override
    public void update(Review entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < reviewList.size(); i++) {
            if (reviewList.get(i).getId() == entity.getId()) {
                reviewList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        reviewList.removeIf(review -> review.getId() == id);
    }

    @Override
    public List<Review> findAll() {
        return new ArrayList<>(reviewList);
    }
}