package Domain;

import Repository.EntityParser;
import Repository.Identifiable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's wishlist, containing a list of reviewable entities.
 * Implements {@link Identifiable} for unique identification and {@link EntityParser} for CSV serialization/deserialization.
 */
public class Wishlist implements Identifiable, EntityParser {
    private int id;
    private User user;
    private List<ReviewableEntity> items;

    /**
     * Constructs a new {@code Wishlist}.
     *
     * @param id    the unique identifier for the wishlist.
     * @param user  the user who owns the wishlist.
     * @param items the initial list of items in the wishlist.
     */
    public Wishlist(int id, User user, List<ReviewableEntity> items) {
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
     */
    public void setUser(User user) {
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
     */
    public void setItems(List<ReviewableEntity> items) {
        this.items = items;
    }

    /**
     * Adds an item to the wishlist if it does not already exist.
     *
     * @param item the reviewable entity to add.
     */
    public void addItem(ReviewableEntity item) {
        if (!items.contains(item)) {
            items.add(item);
        } else {
            System.out.println("Item already exists in wishlist");
        }
    }

    /**
     * Removes an item from the wishlist.
     *
     * @param item the reviewable entity to remove.
     */
    public void removeItem(ReviewableEntity item) {
        items.remove(item);
    }

    /**
     * Converts the {@code Wishlist} object into a CSV string representation.
     *
     * @return a CSV string representing the wishlist.
     */
    @Override
    public String toCSV() {
        StringBuilder itemsCSV = new StringBuilder();
        for (ReviewableEntity item : items) {
            itemsCSV.append(item.toCSV()).append(";");
        }

        if (itemsCSV.length() > 0) {
            itemsCSV.setLength(itemsCSV.length() - 1); // Remove the last semicolon
        }

        return id + "," + user.toCSV() + "," + itemsCSV;
    }

    /**
     * Parses a {@code Wishlist} object from a CSV string.
     *
     * @param csv the CSV string to parse.
     * @return the constructed {@code Wishlist} object.
     */
    @Override
    public Wishlist parseFromCSV(String csv) {
        String[] parts = csv.split(",", 3);

        int id = Integer.parseInt(parts[0]);

        String userCSV = parts[1];
        User user = new User().parseFromCSV(userCSV);

        List<ReviewableEntity> items = new ArrayList<>();
        if (!parts[2].isEmpty()) {
            String[] entitiesCSV = parts[2].split(";");
            for (String entityCSV : entitiesCSV) {
                items.add(parseReviewableEntityFromCSV(entityCSV));
            }
        }

        Wishlist wishlist = new Wishlist(id, user, items);
        wishlist.setId(id);
        return wishlist;
    }

    /**
     * Parses a {@link ReviewableEntity} from a CSV string.
     *
     * @param csv the CSV string representing a reviewable entity.
     * @return the parsed {@link ReviewableEntity}.
     * @throws IllegalArgumentException if the entity type is unknown.
     */
    private ReviewableEntity parseReviewableEntityFromCSV(String csv) {
        String[] parts = csv.split(",", 2);
        String type = parts[0];
        String entityData = parts[1];

        switch (type) {
            case "Activity":
                return new Activity().parseFromCSV(entityData);
            case "Event":
                return new Event().parseFromCSV(entityData);
            case "FreeActivity":
                return new FreeActivity().parseFromCSV(entityData);
            default:
                throw new IllegalArgumentException("Unknown ReviewableEntity type: " + type);
        }
    }

    /**
     * Returns a string representation of the wishlist, including its ID, owner, and items.
     *
     * @return the string representation of the wishlist.
     */
    @Override
    public String toString() {
        return "Wishlist{" +
                "id=" + id +
                ", user=" + user +
                ", items=" + items +
                '}';
    }
}
