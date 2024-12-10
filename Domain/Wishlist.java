package Domain;

import Repository.EntityParser;
import Repository.Identifiable;
import Exception.*;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's wishlist, containing a list of reviewable entities.
 * Implements {@link Identifiable} for unique identification and {@link EntityParser} for CSV serialization/deserialization.
 */
public class Wishlist implements Identifiable {
    private int id;
    private User user;
    private List<ReviewableEntity> items;

    /**
     * Constructs a new {@code Wishlist}.
     *
     * @param id    the unique identifier for the wishlist.
     * @param user  the user who owns the wishlist.
     * @param items the initial list of items in the wishlist.
     * @throws ValidationException if the user is null or the list of items is null
     */
    public Wishlist(int id, User user, List<ReviewableEntity> items) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
        if (items == null) {
            throw new ValidationException("Items list cannot be null.");
        }

        this.id = id;
        this.user = user;
        this.items = new ArrayList<>(items);
    }

    /**
     * Default constructor for creating an empty {@code Wishlist}.
     */
    public Wishlist() {
        this.items = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the wishlist.
     *
     * @return the wishlist ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the wishlist.
     *
     * @param id the wishlist ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the user who owns the wishlist.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who owns the wishlist.
     *
     * @param user the user.
     * @throws ValidationException if the user is null
     */
    public void setUser(User user) {
        if (user == null) {
            throw new ValidationException("User cannot be null.");
        }
        this.user = user;
    }

    /**
     * Gets the list of reviewable entities in the wishlist.
     *
     * @return the list of items.
     */
    public List<ReviewableEntity> getItems() {
        return items;
    }

    /**
     * Sets the list of reviewable entities in the wishlist.
     *
     * @param items the new list of items.
     * @throws ValidationException if the items list is null
     */
    public void setItems(List<ReviewableEntity> items) {
        if (items == null) {
            throw new ValidationException("Items list cannot be null.");
        }
        this.items = items;
    }

    /**
     * Adds an item to the wishlist if it does not already exist.
     *
     * @param item the reviewable entity to add.
     * @throws ValidationException if the item is null
     */
    public void addItem(ReviewableEntity item) {
        if (item == null) {
            throw new ValidationException("Item cannot be null.");
        }
        if (!items.contains(item)) {
            items.add(item);
        } else {
            throw new ValidationException("Item already exists in the wishlist.");
        }
    }

    /**
     * Removes an item from the wishlist.
     *
     * @param item the reviewable entity to remove.
     * @throws ValidationException if the item is null or does not exist in the wishlist
     */
    public void removeItem(ReviewableEntity item) {
        if (item == null) {
            throw new ValidationException("Item cannot be null.");
        }
        if (!items.contains(item)) {
            throw new ValidationException("Item does not exist in the wishlist.");
        }
        items.remove(item);
    }

    /**
     * Returns a string representation of the wishlist, including its ID, owner, and items.
     *
     * @return the string representation of the wishlist.
     */
    @Override
    public String toString() {
        return String.format(
                "Wishlist Details:\n" +
                        "- ID: %d\n" +
                        "- User: %s\n" +
                        "- Items: %s\n",
                id,
                user != null ? user.toString() : "No user",
                items != null ? items.toString() : "No items"
        );
    }
}
