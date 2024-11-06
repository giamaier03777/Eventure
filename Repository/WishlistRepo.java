package Repository;

import Domain.Wishlist;
import java.util.ArrayList;
import java.util.List;

public class WishlistRepo implements IRepository<Wishlist> {

    private List<Wishlist> wishlistList = new ArrayList<>();

    @Override
    public void create(Wishlist entity) {
        if (entity != null) {
            wishlistList.add(entity);
        }
    }

    @Override
    public Wishlist read(int id) {
        for (Wishlist wishlist : wishlistList) {
            if (wishlist.getUser().getId() == id) {
                return wishlist;
            }
        }
        return null;
    }

    @Override
    public void update(Wishlist entity) {
        if (entity == null) {
            return;
        }
        for (int i = 0; i < wishlistList.size(); i++) {
            if (wishlistList.get(i).getUser().getId() == entity.getUser().getId()) {
                wishlistList.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(int id) {
        wishlistList.removeIf(w -> w.getUser().getId() == id);
    }

    @Override
    public List<Wishlist> findAll() {
        return new ArrayList<>(wishlistList);
    }
}
