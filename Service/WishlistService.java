package Service;

import Domain.ReviewableEntity;
import Domain.Wishlist;
import Domain.User;
import Repository.WishlistRepo;

import java.util.List;

public class WishlistService {

    private final WishlistRepo wishlistRepo;

    public WishlistService(WishlistRepo wishlistRepo) {
        this.wishlistRepo = wishlistRepo;
    }

    public void addWishlist(int id, User user, List<ReviewableEntity> items) {
        if (wishlistRepo.read(id) != null) {
            throw new IllegalArgumentException("A wishlist with this ID already exists.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Wishlist items cannot be empty.");
        }

        Wishlist wishlist = new Wishlist(id, user, items);
        wishlistRepo.create(wishlist);
    }

    public Wishlist getWishlistById(int id) {
        Wishlist wishlist = wishlistRepo.read(id);
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist with the specified ID does not exist.");
        }
        return wishlist;
    }

    public void updateWishlist(int id, User user, List<ReviewableEntity> items) {
        Wishlist existingWishlist = wishlistRepo.read(id);

        if (existingWishlist == null) {
            throw new IllegalArgumentException("Wishlist with the specified ID does not exist.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Wishlist items cannot be empty.");
        }

        existingWishlist.setUser(user);
        existingWishlist.setItems(items);
        wishlistRepo.update(existingWishlist);
    }

    public void deleteWishlist(int id) {
        Wishlist wishlist = wishlistRepo.read(id);
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist with the specified ID does not exist.");
        }
        wishlistRepo.delete(id);
    }

}

