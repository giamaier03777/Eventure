package Repository;

import Domain.Review;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepo implements IRepository<Review> {

    private List<Review> reviewList = new ArrayList<>();


    @Override
    public void create(Review entity) {

    }

    @Override
    public Review read(int id) {
        return null;
    }

    @Override
    public void update(Review entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Review> findAll() {
        return List.of();
    }
}