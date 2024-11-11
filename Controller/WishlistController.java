package Controller;

import Domain.ReviewableEntity;
import Domain.User;
import Domain.Wishlist;
import Service.WishlistService;

import java.util.List;

public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    public void addWishlist(String idString, User user, List<ReviewableEntity> items) {
        try {
            int id = Integer.parseInt(idString);

            wishlistService.addWishlist(id, user, items);
            System.out.println("Wishlist added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public Wishlist getWishlistById(String idString) {
        try {
            int id = Integer.parseInt(idString);
            return wishlistService.getWishlistById(id);
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
            return null;
        }
    }

    public void updateWishlist(String idString, User user, List<ReviewableEntity> items) {
        try {
            int id = Integer.parseInt(idString);

            wishlistService.updateWishlist(id, user, items);
            System.out.println("Wishlist updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void deleteWishlist(String idString) {
        try {
            int id = Integer.parseInt(idString);
            wishlistService.deleteWishlist(id);
            System.out.println("Wishlist deleted successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addItemToWishlist(String idString, ReviewableEntity item) {
        try {
            int id = Integer.parseInt(idString);
            Wishlist wishlist = wishlistService.getWishlistById(id);
            wishlist.addItem(item);
            wishlistService.updateWishlist(id, wishlist.getUser(), wishlist.getItems());
            System.out.println("Item added to wishlist successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void removeItemFromWishlist(String idString, ReviewableEntity item) {
        try {
            int id = Integer.parseInt(idString);
            Wishlist wishlist = wishlistService.getWishlistById(id);
            wishlist.removeItem(item);
            wishlistService.updateWishlist(id, wishlist.getUser(), wishlist.getItems());
            System.out.println("Item removed from wishlist successfully.");
        } catch (NumberFormatException e) {
            System.out.println("ID must be a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
