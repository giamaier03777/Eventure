package Service;

import Domain.ReviewableEntity;
import Domain.Wishlist;
import Domain.User;
import Repository.IRepository;
import Exception.*;

import java.util.List;

/**
 * Service class for managing wishlists in the system.
 */
public class WishlistService {

    private final IRepository<Wishlist> wishlistRepo;

    /**
     * Constructs a WishlistService with the specified repository.
     *
     * @param wishlistRepo the repository for storing and retrieving wishlists.
     */
    public WishlistService(IRepository<Wishlist> wishlistRepo) {
        this.wishlistRepo = wishlistRepo;
    }

    /**
     * Adds a new wishlist to the system.
     *
     * @param id    the unique identifier of the wishlist.
     * @param user  the user associated with the wishlist.
     * @param items the list of reviewable entities to include in the wishlist.
     * @throws ValidationException          if the user is null.
     */
    public void addWishlist(String id, User user, List<ReviewableEntity> items) {
        try {
            int wishlistId = Integer.parseInt(id);


            if (user == null) {
                throw new ValidationException("User cannot be null.");
            }

            Wishlist wishlist = new Wishlist(wishlistId, user, items);
            wishlistRepo.create(wishlist);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Retrieves a wishlist by its unique ID.
     *
     * @param id the unique identifier of the wishlist.
     * @return the wishlist with the specified ID.
     * @throws EntityNotFoundException if the wishlist does not exist.
     */
    public Wishlist getWishlistById(String id) {
        try {
            int wishlistId = Integer.parseInt(id);
            Wishlist wishlist = wishlistRepo.read(wishlistId);
            if (wishlist == null) {
                throw new EntityNotFoundException("Wishlist with ID " + id + " not found.");
            }
            return wishlist;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Updates an existing wishlist.
     *
     * @param id    the unique identifier of the wishlist to update.
     * @param user  the updated user associated with the wishlist.
     * @param items the updated list of reviewable entities in the wishlist.
     * @throws EntityNotFoundException if the wishlist does not exist.
     * @throws ValidationException     if the user is null.
     */
    public void updateWishlist(String id, User user, List<ReviewableEntity> items) {
        try {
            int wishlistId = Integer.parseInt(id);
            Wishlist existingWishlist = wishlistRepo.read(wishlistId);

            if (existingWishlist == null) {
                throw new EntityNotFoundException("Wishlist with ID " + id + " not found.");
            }

            if (user == null) {
                throw new ValidationException("User cannot be null.");
            }

            existingWishlist.setUser(user);
            existingWishlist.setItems(items);
            wishlistRepo.update(existingWishlist);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Deletes a wishlist by its unique ID.
     *
     * @param id the unique identifier of the wishlist to delete.
     * @throws EntityNotFoundException if the wishlist does not exist.
     */
    public void deleteWishlist(String id) {
        try {
            int wishlistId = Integer.parseInt(id);
            Wishlist wishlist = wishlistRepo.read(wishlistId);
            if (wishlist == null) {
                throw new EntityNotFoundException("Wishlist with ID " + id + " not found.");
            }
            wishlistRepo.delete(wishlistId);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + id, e);
        }
    }

    /**
     * Adds an item to a wishlist.
     *
     * @param wishlistId the ID of the wishlist.
     * @param item       the reviewable entity to add to the wishlist.
     * @throws EntityNotFoundException if the wishlist does not exist.
     */
    public void addItemToWishlist(String wishlistId, ReviewableEntity item) {
        try {
            int id = Integer.parseInt(wishlistId);
            Wishlist wishlist = wishlistRepo.read(id);
            if (wishlist == null) {
                throw new EntityNotFoundException("Wishlist with ID " + wishlistId + " not found.");
            }

            wishlist.addItem(item);
            wishlistRepo.update(wishlist);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + wishlistId, e);
        }
    }

    /**
     * Removes an item from a wishlist.
     *
     * @param wishlistId the ID of the wishlist.
     * @param item       the reviewable entity to remove from the wishlist.
     * @throws EntityNotFoundException if the wishlist does not exist.
     */
    public void removeItemFromWishlist(String wishlistId, ReviewableEntity item) {
        try {
            int id = Integer.parseInt(wishlistId);
            Wishlist wishlist = wishlistRepo.read(id);
            if (wishlist == null) {
                throw new EntityNotFoundException("Wishlist with ID " + wishlistId + " not found.");
            }

            wishlist.removeItem(item);
            wishlistRepo.update(wishlist);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid ID format. ID must be a number: " + wishlistId, e);
        }
    }
}