package Parsers;

import Domain.*;
import Parsers.UserParser;
import Repository.EntityParser;

import java.util.ArrayList;
import java.util.List;

/**
 * A parser for {@link Wishlist} entities to enable CSV serialization and deserialization.
 */
public class WishlistParser implements EntityParser<Wishlist> {

    private final UserParser userParser = new UserParser();

    @Override
    public String toCSV(Wishlist wishlist) {
        StringBuilder itemsCSV = new StringBuilder();

        for (ReviewableEntity item : wishlist.getItems()) {
            String type = item.getClass().getSimpleName();
            itemsCSV.append(type).append(",").append(item.toCSV()).append(";");
        }

        if (itemsCSV.length() > 0) {
            itemsCSV.setLength(itemsCSV.length() - 1);
        }

        return wishlist.getId() + "," +
                userParser.toCSV(wishlist.getUser()) + "," +
                itemsCSV;
    }

    @Override
    public Wishlist parseFromCSV(String csv) {
        String[] parts = csv.split(",", 3);

        int id = Integer.parseInt(parts[0]);
        User user = userParser.parseFromCSV(parts[1]);

        List<ReviewableEntity> items = new ArrayList<>();
        if (parts.length > 2 && !parts[2].isEmpty()) {
            String[] entitiesCSV = parts[2].split(";");
            for (String entityCSV : entitiesCSV) {
                items.add(parseReviewableEntityFromCSV(entityCSV));
            }
        }

        return new Wishlist(id, user, items);
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
                return new ActivityParser().parseFromCSV(entityData);
            case "Event":
                return new EventParser().parseFromCSV(entityData);
            case "FreeActivity":
                return new FreeActivityParser().parseFromCSV(entityData);
            default:
                throw new IllegalArgumentException("Unknown ReviewableEntity type: " + type);
        }
    }
}
