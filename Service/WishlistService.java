package Service;

import Domain.ReviewableEntity;
import Domain.Wishlist;
import Domain.User;
import Repository.InMemoryRepo;

import java.util.List;

/**
 * Service class for managing wishlists in the system.
 */
public class WishlistService {

    private final InMemoryRepo<Wishlist> wishlistRepo;

    /**
     * Constructs a WishlistService with the specified repository.
     *
     * @param wishlistRepo the repository for storing and retrieving wishlists.
     */
    public WishlistService(InMemoryRepo<Wishlist> wishlistRepo) {
        this.wishlistRepo = wishlistRepo;
    }

    /**
     * Adds a new wishlist to the system.
     *
     * @param id    the unique identifier of the wishlist.
     * @param user  the user associated with the wishlist.
     * @param items the list of reviewable entities to include in the wishlist.
     * @throws IllegalArgumentException if the wishlist ID already exists or the user is null.
     */
    public void addWishlist(String id, User user, List<ReviewableEntity> items) {
        if (wishlistRepo.read(Integer.parseInt(id)) != null) {
            throw new IllegalArgumentException("A wishlist with this ID already exists.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        Wishlist wishlist = new Wishlist(Integer.parseInt(id), user, items);
        wishlistRepo.create(wishlist);
    }

    /**
     * Retrieves a wishlist by its unique ID.
     *
     * @param id the unique identifier of the wishlist.
     * @return the wishlist with the specified ID.
     * @throws IllegalArgumentException if the wishlist does not exist.
     */
    public Wishlist getWishlistById(String id) {
        Wishlist wishlist = wishlistRepo.read(Integer.parseInt(id));
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist with the specified ID does not exist.");
        }
        return wishlist;
    }

    /**
     * Updates an existing wishlist.
     *
     * @param id    the unique identifier of the wishlist to update.
     * @param user  the updated user associated with the wishlist.
     * @param items the updated list of reviewable entities in the wishlist.
     * @throws IllegalArgumentException if the wishlist does not exist or the user is null.
     */
    public void updateWishlist(String id, User user, List<ReviewableEntity> items) {
        Wishlist existingWishlist = wishlistRepo.read(Integer.parseInt(id));

        if (existingWishlist == null) {
            throw new IllegalArgumentException("Wishlist with the specified ID does not exist.");
        }

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }

        existingWishlist.setUser(user);
        existingWishlist.setItems(items);
        wishlistRepo.update(existingWishlist);
    }

    /**
     * Deletes a wishlist by its unique ID.
     *
     * @param id the unique identifier of the wishlist to delete.
     * @throws IllegalArgumentException if the wishlist does not exist.
     */
    public void deleteWishlist(String id) {
        Wishlist wishlist = wishlistRepo.read(Integer.parseInt(id));
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist with the specified ID does not exist.");
        }
        wishlistRepo.delete(Integer.parseInt(id));
    }

    /**
     * Adds an item to a wishlist.
     *
     * @param wishlistId the ID of the wishlist.
     * @param item       the reviewable entity to add to the wishlist.
     * @throws IllegalArgumentException if the wishlist does not exist.
     */
    public void addItemToWishlist(String wishlistId, ReviewableEntity item) {
        Wishlist wishlist = wishlistRepo.read(Integer.parseInt(wishlistId));
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist not found with ID: " + wishlistId);
        }

        wishlist.addItem(item);
        wishlistRepo.update(wishlist);
    }

    /**
     * Removes an item from a wishlist.
     *
     * @param wishlistId the ID of the wishlist.
     * @param item       the reviewable entity to remove from the wishlist.
     * @throws IllegalArgumentException if the wishlist does not exist.
     */
    public void removeItemFromWishlist(String wishlistId, ReviewableEntity item) {
        Wishlist wishlist = wishlistRepo.read(Integer.parseInt(wishlistId));
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist not found with ID: " + wishlistId);
        }

        wishlist.removeItem(item);
        wishlistRepo.update(wishlist);
    }
}
