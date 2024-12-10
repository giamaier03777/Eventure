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
            itemsCSV.append(item.toCSV()).append("|");
        }

        if (!itemsCSV.isEmpty()) {
            itemsCSV.deleteCharAt(itemsCSV.length() - 1);
        }

        return wishlist.getId() + ";" +
                userParser.toCSV(wishlist.getUser()) + ";" +
                itemsCSV;
    }

    @Override
    public Wishlist parseFromCSV(String csv) {
        String[] parts = csv.split(";");

        int id = Integer.parseInt(parts[0]);
        User user = userParser.parseFromCSV(parts[1]);

        List<ReviewableEntity> items = new ArrayList<>();


        String[] entitiesCSV = parts[2].split("\\|");
        for (String entityCSV : entitiesCSV) {
            items.add(parseReviewableEntityFromCSV(entityCSV));
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
        String[] parts = csv.split(",");
        String type = parts[0];
        switch (type) {
            case "Activity":
                return new ActivityParser().parseFromCSV(csv);
            case "Event":
                return new EventParser().parseFromCSV(csv);
            case "FreeActivity":
                return new FreeActivityParser().parseFromCSV(csv);
            default:
                throw new IllegalArgumentException("Unknown ReviewableEntity type: " + type);
        }
    }
}
