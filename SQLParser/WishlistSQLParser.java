package SQLParser;

import Domain.*;
import Repository.SQLParser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLParser implementation for {@link Wishlist} entities.
 */
public class WishlistSQLParser implements SQLParser<Wishlist> {

    private final UserSQLParser userSQLParser;
    private final ActivitySQLParser activitySQLParser;
    private final EventSQLParser eventSQLParser;
    private final FreeActivitySQLParser freeActivitySQLParser;

    /**
     * Constructs a {@link WishlistSQLParser} with its dependencies.
     *
     * @param userSQLParser             the parser for {@link User} objects.
     * @param activitySQLParser         the parser for {@link Activity} objects.
     * @param eventSQLParser            the parser for {@link Event} objects.
     * @param freeActivitySQLParser     the parser for {@link FreeActivity} objects.
     */
    public WishlistSQLParser(UserSQLParser userSQLParser,
                             ActivitySQLParser activitySQLParser,
                             EventSQLParser eventSQLParser,
                             FreeActivitySQLParser freeActivitySQLParser) {
        this.userSQLParser = userSQLParser;
        this.activitySQLParser = activitySQLParser;
        this.eventSQLParser = eventSQLParser;
        this.freeActivitySQLParser = freeActivitySQLParser;
    }

    @Override
    public String getColumns() {
        return "id, user_id";
    }

    @Override
    public String getPlaceholders() {
        return "?, ?";
    }

    @Override
    public String getUpdateColumns() {
        return "user_id = ?";
    }

    @Override
    public void fillPreparedStatementForInsert(PreparedStatement stmt, Wishlist wishlist) throws SQLException {
        stmt.setInt(1, wishlist.getId());
        stmt.setInt(2, wishlist.getUser().getId());


        insertWishlistItems(wishlist, stmt.getConnection());
    }

    @Override
    public void fillPreparedStatementForUpdate(PreparedStatement stmt, Wishlist wishlist) throws SQLException {
        stmt.setInt(1, wishlist.getUser().getId());
        updateWishlistItems(wishlist, stmt.getConnection());
    }

    @Override
    public Wishlist parseFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        User user = userSQLParser.parseFromResultSet(rs);

        // Parse the associated Wishlist items
        List<ReviewableEntity> items = getWishlistItems(id, rs.getStatement().getConnection());

        return new Wishlist(id, user, items);
    }

    /**
     * Inserts the items into the wishlist_items table.
     */
    private void insertWishlistItems(Wishlist wishlist, Connection connection) throws SQLException {
        String insertItemSQL = "INSERT INTO wishlist_items (wishlist_id, entity_type, entity_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertItemSQL)) {
            for (ReviewableEntity item : wishlist.getItems()) {
                stmt.setInt(1, wishlist.getId());
                stmt.setString(2, item.getClass().getSimpleName());
                stmt.setInt(3, getEntityId(item));
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    /**
     * Updates the wishlist items.
     */
    private void updateWishlistItems(Wishlist wishlist, Connection connection) throws SQLException {
        String deleteItemsSQL = "DELETE FROM wishlist_items WHERE wishlist_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteItemsSQL)) {
            stmt.setInt(1, wishlist.getId());
            stmt.executeUpdate();
        }
        insertWishlistItems(wishlist, connection);
    }

    /**
     * Retrieves the list of items associated with a wishlist by its ID.
     */
    private List<ReviewableEntity> getWishlistItems(int wishlistId, Connection connection) throws SQLException {
        List<ReviewableEntity> items = new ArrayList<>();

        String selectItemsSQL = "SELECT entity_type, entity_id FROM wishlist_items WHERE wishlist_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(selectItemsSQL)) {
            stmt.setInt(1, wishlistId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String entityType = rs.getString("entity_type");
                    int entityId = rs.getInt("entity_id");

                    // Retrieve the entity based on type and ID
                    ReviewableEntity item = getEntityFromTypeAndId(entityType, entityId, connection);
                    items.add(item);
                }
            }
        }

        return items;
    }

    private ReviewableEntity getEntityFromTypeAndId(String entityType, int entityId, Connection connection) throws SQLException {
        String selectSQL = getSelectSQLForEntityType(entityType);
        try (PreparedStatement stmt = connection.prepareStatement(selectSQL)) {
            stmt.setInt(1, entityId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    switch (entityType) {
                        case "Activity":
                            return activitySQLParser.parseFromResultSet(rs);
                        case "Event":
                            return eventSQLParser.parseFromResultSet(rs);
                        case "FreeActivity":
                            return freeActivitySQLParser.parseFromResultSet(rs);
                        default:
                            throw new SQLException("Unknown entity type: " + entityType);
                    }
                } else {
                    throw new SQLException(entityType + " not found with ID: " + entityId);
                }
            }
        }
    }

    /**
     * Helper method to return the SQL SELECT query string based on the entity type.
     */
    private String getSelectSQLForEntityType(String entityType) {
        switch (entityType) {
            case "Activity":
                return "SELECT * FROM activities WHERE id = ?";
            case "Event":
                return "SELECT * FROM events WHERE id = ?";
            case "FreeActivity":
                return "SELECT * FROM free_activities WHERE id = ?";
            default:
                throw new IllegalArgumentException("Unknown entity type: " + entityType);
        }
    }

    @Override
    public int getUpdateParametersCount() {
        return 2;
    }

    /**
     * Helper method to get the entity ID from the specific entity.
     */
    private int getEntityId(ReviewableEntity item) {
        if (item instanceof Activity) {
            return ((Activity) item).getId();
        } else if (item instanceof Event) {
            return ((Event) item).getId();
        } else if (item instanceof FreeActivity) {
            return ((FreeActivity) item).getId();
        } else {
            throw new IllegalArgumentException("Unknown entity type: " + item.getClass().getSimpleName());
        }
    }
}
